create table if not exists links(
    id serial primary key,
    url text,
    generated_code text,
    count int default 0,
    client_id int references clients (id)
);

comment on table links is 'Links that the server works with';
comment on column links.id is 'Identifier of a link';
comment on column links.url is 'Original web link sent to the server by a client';
comment on column links.generated_code is 'Code that the server generates for the link';
comment on column links.count is 'How many times this link has been requested';
comment on column links.client_id is 'refers to the client who created the link';