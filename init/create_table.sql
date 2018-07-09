CREATE TABLE users (
    id varchar(10) PRIMARY KEY,
    name varchar(20) NOT NULL,
    password varchar(10) NOT NULL
);

CREATE DATABASE springdb_test;

\connect springdb_test;

CREATE TABLE users (
    id varchar(10) PRIMARY KEY,
    name varchar(20) NOT NULL,
    password varchar(10) NOT NULL
);
