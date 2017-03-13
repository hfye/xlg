-- Database: pagination_test

DROP DATABASE pagination_test;

CREATE DATABASE pagination_test
  WITH OWNER = pagination
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_Singapore.1252'
       LC_CTYPE = 'English_Singapore.1252'
       CONNECTION LIMIT = -1;