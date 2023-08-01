create table grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null,
	
	constraint fk_grupo_permissao_grupo foreign key (grupo_id) references grupo (id),
	constraint fk_grupo_permissao_permissao foreign key (permissao_id) references permissao (id),
	
	primary key (grupo_id, permissao_id)
) engine=InnoDB default charset=utf8mb4;