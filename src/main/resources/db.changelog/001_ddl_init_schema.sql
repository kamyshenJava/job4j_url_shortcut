create table if not exists clients(
    id serial primary key,
    site text unique not null,
    password text not null
);

create table if not exists links(
    id serial primary key,
    url text,
    generated_code text,
    count int default 0,
    client_id int references clients(id)
)