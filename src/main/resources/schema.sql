create sequence if not exists public.cidade_sequence start with 1 increment by 1;
create table if not exists public.cidade
(
    cidade_id integer not null,
    nome      varchar(255),
    estado    varchar(20),
    primary key (cidade_id)
);

create sequence if not exists public.cliente_sequence start with 1 increment by 1;
create table if not exists public.cliente
(
    cliente_id      integer not null,
    nome_completo   varchar(255),
    sexo            varchar(10),
    idade           integer,
    data_nascimento date,
    cidade_id       integer,
    primary key (cliente_id),
    foreign key (cidade_id) REFERENCES cidade (cidade_id)
);


