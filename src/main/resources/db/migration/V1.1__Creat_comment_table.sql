CREATE TABLE comment
(
    id      SERIAL PRIMARY KEY,
    text TEXT,
    post_id INTEGER
);