create table usuario(
	id bigint not null auto_increment,
	nome varchar(60) not null,
	email varchar(60) not null,
	senha varchar(60) not null,
    data_cadastro datetime,
    data_atualizacao datetime,
    ativo boolean not null,
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;