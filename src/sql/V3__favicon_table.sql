CREATE TABLE public.favicon (
    id bigint NOT NULL,
    name character varying(50),
    image oid
);

ALTER TABLE ONLY public.favicon
    ADD CONSTRAINT pk_favicon PRIMARY KEY (id);
