insert into empresa (id, nome, data_cadastro, data_atualizacao, ativo) values(1, "Auto Peças Itu", "Auto peças itu Ltda", utc_timestamp, utc_timestamp, true)
insert into empresa (id, nome, data_cadastro, data_atualizacao, ativo) values(2, "Atelie mãos de costura", "Atelie mãos de costura Ltda", utc_timestamp, utc_timestamp, true)
insert into empresa (id, nome, data_cadastro, data_atualizacao, ativo) values(3, "Mercado utils", "Mercado utils Ltda", utc_timestamp, utc_timestamp, true)

insert into loja (id, nome, empresa_id, data_cadastro, data_atualizacao, ativo) values(1, "Loja 1", 1, utc_timestamp, utc_timestamp, true)
insert into loja (id, nome, empresa_id, data_cadastro, data_atualizacao, ativo) values(2, "Loja 2", 1, utc_timestamp, utc_timestamp, true)
insert into loja (id, nome, empresa_id, data_cadastro, data_atualizacao, ativo) values(3, "Loja 1", 2, utc_timestamp, utc_timestamp, true)
insert into loja (id, nome, empresa_id, data_cadastro, data_atualizacao, ativo) values(4, "Loja 1", 3, utc_timestamp, utc_timestamp, true)

insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(1, "CEO", 20000, 1, utc_timestamp, utc_timestamp, true )
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(2, "Gerente", 5000, 1, utc_timestamp, utc_timestamp, true )
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(3, "Vendedor", 1800, 1, utc_timestamp, utc_timestamp, true )
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(4, "Caixa", 1200, 1, utc_timestamp, utc_timestamp, true )

insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(1, "Josivalda", utc_timestamp, utc_timestamp, true)
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(2, "Cleber", utc_timestamp, utc_timestamp, true)
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(3, "Maria", utc_timestamp, utc_timestamp, true)
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(4, "Mateus", utc_timestamp, utc_timestamp, true)
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(5, "Pedro", utc_timestamp, utc_timestamp, true)
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(6, "José", utc_timestamp, utc_timestamp, true)

insert into funcionario_cargo (funcionario_id, cargo_id) values(2,1)
insert into funcionario_cargo (funcionario_id, cargo_id) values(3,2)
insert into funcionario_cargo (funcionario_id, cargo_id) values(4,3)
insert into funcionario_cargo (funcionario_id, cargo_id) values(5,4)
insert into funcionario_cargo (funcionario_id, cargo_id) values(4,4)

insert into caixa (id, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(1, 1, 0, utc_timestamp, utc_timestamp, true)
insert into caixa (id, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(2, 1, 0, utc_timestamp, utc_timestamp, true)

insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(1, "Dinheiro", utc_timestamp, utc_timestamp, true)
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(2, "Débito", utc_timestamp, utc_timestamp, true)
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(3, "Crédito", utc_timestamp, utc_timestamp, true)
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(4, "Pix", utc_timestamp, utc_timestamp, true)

insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(1, 20, "Venda de gás", 1, 4, utc_timestamp, utc_timestamp)
insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(2, 20, "Venda de bolacha", 1, 4, utc_timestamp, utc_timestamp)
insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(3, 20, "Venda de escova de dente", 1, 4, utc_timestamp, utc_timestamp)
