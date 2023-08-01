create table caixa(
	id bigint not null auto_increment,
	nome varchar(30) not null,
	saldo double not null,
    data_cadastro datetime,
    data_atualizacao datetime,
    ativo boolean not null,
    loja_id bigint not null,
    
    constraint fk_caixa_loja foreign key (loja_id) references loja (id),
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;