-- drop database if exists sistema_vistoria_df;
create database sistema_vistoria_df;
use sistema_vistoria_df;

-- =========================
-- CLIENTE
-- =========================
create table cliente (
    idCliente int primary key auto_increment,
    nome varchar(100) not null,
    cpf varchar(15) not null unique,
    telefone varchar(16) not null,
    email varchar(100) not null unique,
    senha varchar(100) not null
);

-- =========================
-- FUNCIONARIO
-- =========================
create table funcionario (
    idFuncionario int primary key auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    matricula varchar(100) not null unique,
    senha varchar(100) not null,
    cargo enum("Vistoriador","Gerente") not null
);

-- =========================
-- VEICULO
-- =========================
create table veiculo (
    idVeiculo int primary key auto_increment,
    placa varchar(8) not null unique,
    tipo_veiculo varchar(20) not null,
    nome_veiculo varchar(100) not null,
    modelo varchar(100) not null,
    ano_veiculo year not null,
    chassi varchar(17) not null unique,
    observacoes text,
    idCliente int not null,
    constraint fk_veiculo_cliente foreign key(idCliente) references cliente(idCliente)
);

-- =========================
-- AGENDAMENTO
-- =========================
-- O status será `Pendente`, `Concluido` ou `Cancelado`.
-- O idCliente é redundante se já temos o idVeiculo, mas mantemos para facilitar consultas.
create table agendamento (
    idAgendamento int primary key auto_increment,
    data_agendamento date not null,
    hora time not null,
    status_agendamento enum("Pendente","Concluido","Cancelado") not null,
    idCliente int not null,
    idVeiculo int not null,
    constraint fk_agendamento_cliente foreign key(idCliente) references cliente(idCliente),
    constraint fk_agendamento_veiculo foreign key(idVeiculo) references veiculo(idVeiculo)
);

-- =========================
-- VISTORIA
-- =========================
-- A vistoria é o resultado de um agendamento.
-- Removido o campo status_pagamento para evitar redundância.
create table vistoria (
    idVistoria int primary key auto_increment,
    data_vistoria date not null,
    resultado enum("Aprovado","Reprovado","Aprovado com ressalvas") not null,
    observacoes text,
    idAgendamento int not null unique,
    idFuncionario int not null,
    constraint fk_vistoria_agendamento foreign key(idAgendamento) references agendamento(idAgendamento),
    constraint fk_vistoria_funcionario foreign key(idFuncionario) references funcionario(idFuncionario)
);

-- =========================
-- PAGAMENTO
-- =========================
-- O pagamento está diretamente ligado à vistoria, que é o serviço.
create table pagamento (
    idPagamento int primary key auto_increment,
    forma_pagamento enum("Débito","Crédito","Pix","Boleto","Dinheiro") not null,
    valor decimal(10,2) not null,
    data_pagamento date not null,
    idVistoria int not null unique,
    constraint fk_pagamento_vistoria foreign key (idVistoria) references vistoria(idVistoria)
);
-- =========================
-- Laudo Vistoria
-- =========================
-- aqui vai ficar os dados da vistoria e valor para o pagamento que serão gerados em pdf
create table laudo(
	idLaudo int primary key auto_increment,
    caminho_arquivo varchar(255),
    data_geracao datetime not null,
    idVistoria int not null unique,
    constraint fk_laudo_vistoria foreign key(idVistoria) references vistoria(idVistoria)
);
