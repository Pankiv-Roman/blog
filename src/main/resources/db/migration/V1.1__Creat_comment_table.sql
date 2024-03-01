CREATE TABLE comment
(
    id      SERIAL PRIMARY KEY,
    creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    text TEXT,
    post_id INTEGER
);