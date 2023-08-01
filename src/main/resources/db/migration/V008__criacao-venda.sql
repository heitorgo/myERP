create table venda(
	id bigint not null auto_increment,
	valor double not null,
    descricao varchar(60) not null,
    data_cadastro datetime,
    data_atualizacao datetime,
    funcionario_id bigint not null,
    caixa_id bigint not null,
    
    constraint fk_venda_funcionario foreign key (funcionario_id) references funcionario (id),
	constraint fk_venda_caixa foreign key (caixa_id) references caixa (id),
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;