alter table caixa modify column saldo decimal(10,2) not null;
alter table cargo modify column remuneracao decimal(10,2) not null;
alter table loja modify column saldo decimal(10,2);
alter table venda modify column valor decimal(10,2) not null;