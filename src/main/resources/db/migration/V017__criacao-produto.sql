create table produto(
	id bigint not null auto_increment,
	nome varchar(60) not null,
	descricao varchar(60) not null,
	valor double not null,
	quantidade integer not null,
    data_cadastro datetime,
    data_atualizacao datetime,
    ativo boolean not null,
    loja_id bigint not null,
    
    constraint fk_produto_loja foreign key (loja_id) references loja (id),
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;