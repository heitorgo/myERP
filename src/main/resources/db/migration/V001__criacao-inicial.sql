create table empresa(
	id bigint not null auto_increment,
	nome varchar(60) not null,
	razao_social varchar(60),
    data_cadastro datetime,
    data_atualizacao datetime,
    ativo boolean not null,
    
    primary key (id)
)engine=InnoDB default charset=utf8mb4;