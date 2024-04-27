CREATE TABLE "_user"
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);
