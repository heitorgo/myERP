create table usuario_grupo (
	usuario_id bigint not null,
	grupo_id bigint not null,
	
	constraint fk_usuario_grupo_usuario foreign key (usuario_id) references usuario (id),
	constraint fk_usuario_grupo_grupo foreign key (grupo_id) references grupo (id),
	
	primary key (usuario_id, grupo_id)
) engine=InnoDB default charset=utf8mb4;