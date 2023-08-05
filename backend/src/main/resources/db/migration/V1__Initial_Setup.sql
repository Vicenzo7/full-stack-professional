CREATE  TABLE customer(
    id BIGSERIAL PRIMARY KEY ,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    age INT NOT NULL
);

-- Since id is initialized as BIG-SERIAL we do not need to create sequence