create table public.users (
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20),
    age int not null,
    is_deleted boolean default false
);

