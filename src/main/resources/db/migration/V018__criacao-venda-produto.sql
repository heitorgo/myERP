create table venda_produto (
	venda_id bigint not null,
	produto_id bigint not null,
	
	constraint fk_venda_produto_venda foreign key (venda_id) references venda (id),
	constraint fk_venda_produto_produto foreign key (produto_id) references produto (id),
	
	primary key (venda_id, produto_id)
) engine=InnoDB default charset=utf8mb4;