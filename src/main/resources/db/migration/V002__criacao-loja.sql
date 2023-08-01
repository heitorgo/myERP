create table loja(
	id bigint not null auto_increment,
	nome varchar(60) not null,
	saldo double not null,
    data_cadastro datetime,
    data_atualizacao datetime,
    ativo boolean not null,
    empresa_id bigint not null,
    
    constraint fk_loja_empresa foreign key (empresa_id) references empresa (id),
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;