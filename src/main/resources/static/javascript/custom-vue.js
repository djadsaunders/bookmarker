var vm = new Vue({
    el: '#content-div',
    data: {
      categories: '',
      bookmarkToEdit: '',
      categoryToEdit: '',
      editingBookmark: false,
      editingCategory: false,
      showHelp: false,
      showClass: 'show',
      hideClass: 'hide',
      newBookmark: {
          name: "New bookmark",
          category: "Unfiled"
      },
      jsButton: "javascript:(function(){location.href='http://" +
        location.host + "/pending-bookmark/create?url=' + location.href;})()"
    },
    created: function() {
      this.getPendingBookmark();
      this.getBookmarks();
    },
    methods: {
        // Auto-select input when clicked 
        inputClicked: function(event) {
          event.target.select();
        },
        // Retrieve all bookmarks
        getBookmarks: function() {
          $.get("/category", function(resp) {
              vm.categories = resp;
          });
        },
        // Get the pending bookmark if any and set to edit
        getPendingBookmark: function() {
            $.get("/pending-bookmark", function(resp) {
                if (resp != null) {
                    vm.bookmarkToEdit = resp;
                    vm.editingBookmark = true;
                }
            });
        },
        // Add or edit a bookmark
        // Problems with update/delete:
        // 1. Cannot call functions so have to duplicate logic to re-retrieve items
        // 2. Also, there should be a better way to update the items without re-retrieving
        addOrEditBookmark: function() {
            $.post("/bookmark/" + this.bookmarkToEdit.id, { 
                    bookmarkName: this.bookmarkToEdit.name,
                    categoryName: this.bookmarkToEdit.category
                }, 
                function(resp) {
                    vm.bookmarkToEdit = '';
                    $.get("/category", function(resp) {
                        vm.categories = resp;
                        vm.editingBookmark = false;
                    });
                }
            )
        },
        // Handle drag event
        drag: function(event) {
            event.dataTransfer.setData("draggedItemId", event.target.id);
        },
        preventDefault: function(event) {
            event.preventDefault();
        },
        // Handle drop onto trash can
        dropForDelete: function(event) {
            event.preventDefault();
            var itemType = event.dataTransfer.getData("draggedItemId").split(".")[0];
            var itemId = event.dataTransfer.getData("draggedItemId").split(".")[1];
            console.log("Deleting " + itemType + ", " + itemId);
            $.ajax({
                url: "/" + itemType + "/" + itemId,
                type: "DELETE",
                success: function(resp) {vm
                    $.get("/category", function(resp) {
                        vm.categories = resp;
                    });						
                }
            });
        },
        // Handle drop onto edit icon 
        dropForEdit: function(event) {
            event.preventDefault();
            var itemType = event.dataTransfer.getData("draggedItemId").split(".")[0];
            var itemId = event.dataTransfer.getData("draggedItemId").split(".")[1];

            if (itemType == "bookmark") {
                this.categories.forEach(function(category) {
                    category.bookmarks.forEach(function(bookmark) {
                        if (bookmark.id == itemId) {
                            vm.bookmarkToEdit = bookmark;
                            vm.editingBookmark = true;
                        }
                    });
                });
            }
            else {
                vm.categoryToEdit = vm.categories.filter(category => category.id == itemId)[0];
                vm.editingCategory = true;
            }
        },
        // Edit category
        editCategory: function() {
            $.post("/category/" + this.categoryToEdit.id, { 
                categoryName: this.categoryToEdit.name
                }, 
                function(resp) {
                    vm.categoryToEdit = '';
                    $.get("/category", function(resp) {
                        vm.categories = resp;
                        vm.editingCategory = false;
                    });
                }
            )
        },
        // Toggle to show or hide help
        toggleHelp: function() {
            vm.showHelp = !vm.showHelp;
        },
        refreshPage: function() {
            location.reload();
            return false;
        }
    }
});
