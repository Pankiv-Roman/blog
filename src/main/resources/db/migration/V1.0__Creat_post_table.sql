CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    content TEXT,
    star BOOLEAN
);