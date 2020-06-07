var vm = new Vue({
    el: '#content-div',
    data: {
      categories: '',
      pendingBookmark: ''
    },
    created: function() {
      this.getPendingBookmark();
      this.getBookmarks();
    },
    methods: {
      bookmarkInputClicked: function(event) {
          event.target.select();
      },
        getBookmarks: function() {
          $.get("/category", function(resp) {
              vm.categories = resp;
          });
        },
        getPendingBookmark: function() {
          $.get("http://localhost:8080/pending-bookmark", function(resp) {
              if (resp != null) {
                  vm.pendingBookmark = resp;
              }
          });
      },
      // Problems with update/delete:
      // Cannot call functions so have to duplicate logic to re-retrieve items
      // Also, there should be a better way to update the items without re-retrieving
      updateBookmark: function() {
          $.post("/bookmark/" + this.pendingBookmark.id,
              { bookmarkName: this.pendingBookmark.name }, 
              function(resp) {
                  vm.pendingBookmark = null;
                  $.get("/category", function(resp) {
                      vm.categories = resp;
                  });
              }
          )
      },
      deleteBookmark: function(id) {
          $.ajax({
              url: "/bookmark/" + id,
              type: "DELETE",
              success: function(resp) {
                  $.get("/category", function(resp) {
                      vm.categories = resp;
                  });						
              }
          });
      },
      drag: function(event) {
          event.dataTransfer.setData("text", event.target.id);
      },
      allowDrop: function(event) {
          event.preventDefault();
      },
      drop: function(event) {
          event.preventDefault();
          var data = event.dataTransfer.getData("text");
          this.deleteBookmark(data);
      }
    }
  });
