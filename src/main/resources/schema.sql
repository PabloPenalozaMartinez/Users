CREATE TABLE "User" (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    creation_date timestamp NOT NULL,
    modification_date timestamp NOT NULL,
    last_login_date timestamp NOT NULL,
    token varchar(100) NOT NULL,
    active boolean NOT NULL
);

CREATE TABLE Phone (
    id int PRIMARY KEY,
    number number NOT NULL,
    city_code number NOT NULL,
    country_code number NOT NULL    
);