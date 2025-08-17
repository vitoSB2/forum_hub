alter table usuarios drop column nomeUsuario;

alter table topicos
    drop column email,
    drop column senha,
    drop column nomeUsuario;

alter table topicos add autor varchar(100) not null;