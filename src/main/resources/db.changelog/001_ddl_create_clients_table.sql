create table if not exists clients(
    id serial primary key,
    site text unique not null,
    password text not null
);

comment on table clients is 'Site clients';
comment on column clients.id is 'Identifier of a client';
comment on column clients.site is 'Name of the site';
comment on column clients.password is 'Password';