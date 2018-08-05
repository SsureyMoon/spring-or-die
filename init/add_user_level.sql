\connect springdb_test;

ALTER TABLE users
    ADD COLUMN level smallint NOT NULL,
    ADD COLUMN login int NOT NULL,
    ADD COLUMN recommend int NOT NULL;
