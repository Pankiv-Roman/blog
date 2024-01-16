CREATE TABLE comment
(
    id      SERIAL PRIMARY KEY,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    text TEXT,
    post_id INTEGER
);