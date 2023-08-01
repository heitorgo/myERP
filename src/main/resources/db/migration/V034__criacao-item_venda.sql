create table item_venda(
	id bigint not null auto_increment,
	valorTotal decimal(10,2),
	valorUnitario decimal(10,2),
	quantidade integer,
    venda_id bigint not null,
    produto_id bigint not null,
    
    constraint fk_item_venda_venda foreign key (venda_id) references venda (id),
    constraint fk_item_venda_produto foreign key (produto_id) references produto (id),
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;