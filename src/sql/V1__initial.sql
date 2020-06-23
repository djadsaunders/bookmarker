CREATE TABLE public.bookmark (
    id bigint NOT NULL,
    favicon_file character varying(255),
    name character varying(255),
    pending boolean,
    url character varying(255),
    userid character varying(255),
    category_id bigint
);

CREATE TABLE public.category (
    id bigint NOT NULL,
    name character varying(255),
    userid character varying(255)
);

ALTER TABLE ONLY public.bookmark
    ADD CONSTRAINT pk_bookmark PRIMARY KEY (id);

ALTER TABLE ONLY public.category
    ADD CONSTRAINT pk_category PRIMARY KEY (id);

ALTER TABLE ONLY public.bookmark
    ADD CONSTRAINT fk_bookmark_category FOREIGN KEY (category_id) REFERENCES public.category(id);
