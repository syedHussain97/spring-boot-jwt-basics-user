drop table if exists "user";


create table "user"
(

    id       INT auto_increment PRIMARY KEY,
    username VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL,
    phone_number VARCHAR(32) NOT NULL,

);
