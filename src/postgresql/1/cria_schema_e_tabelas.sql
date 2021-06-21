create schema financas;

create table financas.usuario
(
    id bigserial not null primary key,
    nome character varying(150),
    email character varying(100),
    senha character varying(20),
    data_cadastro timestamp default now()
);

create table financas.lancamento
(
    id bigserial not null primary key,
    descricao character varying(100) not null,
    mes integer not null,
    ano integer not null,
    valor numeric(16,2) not null,
    tipo character varying(20) not null,
    status character varying(20) not null,
    id_usuario bigint references financas.usuario (id),
    data_cadastro timestamp default now(),
    constraint lancamento_tipo_check check (tipo::text = any (array['RECEITA'::character varying, 'DESPESA'::character varying]::text[])),
    constraint lancamento_status_check check (status::text = any (array['PENDENTE'::character varying, 'CANCELADO'::character varying, 'EFETIVADO'::character varying]::text[]))
);