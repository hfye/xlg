-- Table: public.test_table

DROP TABLE public.test_table;

CREATE TABLE public.test_table
(
  test_date date NOT NULL,
  test_id integer NOT NULL,
  CONSTRAINT test_table_pk PRIMARY KEY (test_date, test_id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.test_table
  OWNER TO pagination;