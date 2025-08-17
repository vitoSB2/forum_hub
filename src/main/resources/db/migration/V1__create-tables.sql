create table usuarios(

    id bigint not null auto_increment,
    nomeUsuario varchar(100) not null,
    email varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)

);

create table topicos(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensagem varchar(500) not null,
    nomeCurso varchar(100) not null unique,
    dataCriacao varchar(100) not null,
    estado varchar(100) not null,

    nomeUsuario varchar(100) not null,
    email varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)

);