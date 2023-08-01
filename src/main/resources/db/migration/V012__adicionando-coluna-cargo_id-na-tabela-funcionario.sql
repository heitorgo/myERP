alter table funcionario add column cargo_id bigint not null;
alter table funcionario add constraint fk_funcionario_cargo 
foreign key (cargo_id) references cargo (id);