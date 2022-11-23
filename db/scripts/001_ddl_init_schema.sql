create table if not exists clients(
    id serial primary key,
    site text unique not null,
    password text not null
);