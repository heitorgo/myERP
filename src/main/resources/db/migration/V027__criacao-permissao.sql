create table permissao(
	id bigint not null auto_increment,
    nome varchar(255) not null,
    descricao varchar(255) not null,
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;