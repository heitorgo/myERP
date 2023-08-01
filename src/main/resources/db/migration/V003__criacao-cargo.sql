create table cargo(
	id bigint not null auto_increment,
	titulo varchar(60) not null,
	remuneracao double not null,
    data_cadastro datetime,
    data_atualizacao datetime,
    ativo boolean not null,
    loja_id bigint not null,
    
    constraint fk_cargo_loja foreign key (loja_id) references loja (id),
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;