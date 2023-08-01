create table empresa_usuario_responsavel (
	empresa_id bigint not null,
	usuario_id bigint not null,
	
	constraint fk_empresa_usuario_empresa foreign key (empresa_id) references empresa (id),
	constraint fk_empresa_usuario_usuario foreign key (usuario_id) references usuario (id),
	
	primary key (empresa_id, usuario_id)
) engine=InnoDB default charset=utf8mb4;