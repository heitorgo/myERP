set foreign_key_checks = 0;

delete from empresa;
delete from loja;
delete from caixa;
delete from cargo;
delete from funcionario;
delete from venda;
delete from forma_pagamento;
delete from usuario;
delete from produto;
delete from grupo;
delete from permissao;
delete from grupo_permissao;
delete from usuario_grupo;
delete from empresa_usuario_responsavel;
delete from item_venda;

set foreign_key_checks = 1;

alter table empresa auto_increment = 1;
alter table loja auto_increment = 1;
alter table caixa auto_increment = 1;
alter table cargo auto_increment = 1;
alter table funcionario auto_increment = 1;
alter table venda auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table usuario auto_increment = 1;
alter table produto auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1; 

insert into usuario (id, nome, email, senha, data_cadastro, data_atualizacao, ativo) values(1, "Cleber", "cleber.2022@gmail.com", "Cleber123@2022", utc_timestamp, utc_timestamp, true);
insert into usuario (id, nome, email, senha, data_cadastro, data_atualizacao, ativo) values(2, "Josivalda", "jo.2020@gmail.com", "Jo5ivalda2020", utc_timestamp, utc_timestamp, true);
insert into usuario (id, nome, email, senha, data_cadastro, data_atualizacao, ativo) values(3, "José Thiago", "zeth.1990@hotmail.com", "Zeth@1990", utc_timestamp, utc_timestamp, true);
insert into usuario (id, nome, email, senha, data_cadastro, data_atualizacao, ativo) values(4, "Pedro", "pedro.1992@hotmail.com", "Pedr@1992", utc_timestamp, utc_timestamp, false);

insert into empresa (id, nome, razao_social, data_cadastro, data_atualizacao, ativo) values(1, "Auto Peças Itu", "Auto Peças Itu LTDA", utc_timestamp, utc_timestamp, true);
insert into empresa (id, nome, razao_social, data_cadastro, data_atualizacao, ativo) values(2, "Atelie Mãos de Costura", "Atelie Mãos de Costura LTDA", utc_timestamp, utc_timestamp, true);
insert into empresa (id, nome, razao_social, data_cadastro, data_atualizacao, ativo) values(3, "Mercearia Util", "Mercearia Util LTDA", utc_timestamp, utc_timestamp, true);

insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, aberto, ativo) values(1, "Loja 1", 0, 1, utc_timestamp, utc_timestamp, true, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, aberto, ativo) values(2, "Loja 1", 0, 2, utc_timestamp, utc_timestamp, true, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, aberto, ativo) values(3, "Loja 1", 0, 3, utc_timestamp, utc_timestamp, true, true);

insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(1, "Gerente", 5000, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(2, "Vendedor", 1800, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(3, "Caixa", 1300, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(4, "Caixa", 1800, 3, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, loja_id, data_cadastro, data_atualizacao, ativo) values(5, "Administrador", 2, utc_timestamp, utc_timestamp, true );

insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(1, "Cleber", 1, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(2, "Maria", 3, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(3, "Mateus", 2, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(4, "Pedro", 4, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(5, "Izonilda", 5, utc_timestamp, utc_timestamp, true);

insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(1, "Caixa Principal", 1, 0, utc_timestamp, utc_timestamp, true);
insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(2, "Caixa Principal", 2, 0, utc_timestamp, utc_timestamp, true);
insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(3, "Caixa Principal", 3, 0, utc_timestamp, utc_timestamp, true);

insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(1, "Dinheiro", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(2, "Débito", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(3, "Crédito", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(4, "Pix", utc_timestamp, utc_timestamp, true);

insert into produto (id, nome, descricao, valor, quantidade, loja_id, data_cadastro, data_atualizacao, ativo) values(1, "Porca", "Peça para comercio", 1.50, 1000, 1, utc_timestamp, utc_timestamp, true);
insert into produto (id, nome, descricao, valor, quantidade, loja_id, data_cadastro, data_atualizacao, ativo) values(2, "Camisa P", "Camisa de tecido poliester", 25.50, 10, 2, utc_timestamp, utc_timestamp, true);
insert into produto (id, nome, descricao, valor, quantidade, loja_id, data_cadastro, data_atualizacao, ativo) values(3, "Bolacha Mabel", "Bolacha mabel de chocolate", 3.50, 1000, 3, utc_timestamp, utc_timestamp, true);

insert into venda (id, valor, descricao, caixa_id, funcionario_id, forma_pagamento_id, data_cadastro, data_atualizacao) values(1, 7.00, "Venda de bolacha", 3, 4, 1, utc_timestamp, utc_timestamp);
insert into item_venda(id, valor_total, valor_unitario, quantidade, produto_id, venda_id) values(1, 7.00, 3.50, 2, 3, 1);
insert into venda (id, valor, descricao, caixa_id, funcionario_id, forma_pagamento_id, data_cadastro, data_atualizacao) values(2, 15.0, "Venda de porcas", 1, 2, 4, utc_timestamp, utc_timestamp);
insert into item_venda(id, valor_total, valor_unitario, quantidade, produto_id, venda_id) values(2, 15.00, 1.50, 10, 1, 2);
insert into venda (id, valor, descricao, caixa_id, funcionario_id, forma_pagamento_id, data_cadastro, data_atualizacao) values(3, 25.50, "Venda de camisa", 2, 5, 4, utc_timestamp, utc_timestamp);
insert into item_venda(id, valor_total, valor_unitario, quantidade, produto_id, venda_id) values(3, 25.50, 25.50, 1, 2, 3);

insert into grupo (id, nome) values(1, "Gerente");
insert into grupo (id, nome) values(2, "Vendedor");
insert into grupo (id, nome) values(3, "Caixa");

insert into permissao (id, nome, descricao) values(1, "Criar usuarios", "permite criar usuarios");
insert into permissao (id, nome, descricao) values(2, "Criar vendas", "permite criar vendas");

insert into grupo_permissao (grupo_id, permissao_id) values(1, 1);
insert into grupo_permissao (grupo_id, permissao_id) values(2, 2);

insert into usuario_grupo(usuario_id, grupo_id) values(1, 1);
insert into usuario_grupo(usuario_id, grupo_id) values(3, 2);

insert into empresa_usuario_responsavel(empresa_id, usuario_id) values(1, 1);
insert into empresa_usuario_responsavel(empresa_id, usuario_id) values(3, 4);