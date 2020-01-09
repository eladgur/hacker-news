CREATE TABLE posts(
   id serial PRIMARY KEY,
   author VARCHAR(30),
   votes INTEGER,
   text VARCHAR(1000),
   score INTEGER,
   created_on TIMESTAMP
);