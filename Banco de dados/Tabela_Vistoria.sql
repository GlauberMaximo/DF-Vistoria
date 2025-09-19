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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- FUNCIONARIO
-- =========================
create table funcionario (
    idFuncionario int primary key auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    matricula varchar(100) not null unique,
    senha varchar(100) not null,
    cargo enum('Vistoriador','Gerente') not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- AGENDAMENTO
-- =========================
create table agendamento (
    idAgendamento int primary key auto_increment,
    data_agendamento date not null,
    hora time not null,
    tipo_vistoria enum('Vistoria de Transferência', 'Vistoria Cautelar', 'Vistoria Prévia') not null,
    status_agendamento enum('Pendente','Concluido','Cancelado') default 'Pendente' not null,
    idCliente int not null,
    idVeiculo int not null,
    constraint fk_agendamento_cliente foreign key(idCliente) references cliente(idCliente),
    constraint fk_agendamento_veiculo foreign key(idVeiculo) references veiculo(idVeiculo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- VISTORIA
-- =========================
create table vistoria (
    idVistoria int primary key auto_increment,
    data_vistoria date not null,
    resultado enum('Aprovado','Reprovado','Aprovado com ressalvas') not null,
    observacoes text,
    idAgendamento int not null unique,
    idFuncionario int not null,
    constraint fk_vistoria_agendamento foreign key(idAgendamento) references agendamento(idAgendamento),
    constraint fk_vistoria_funcionario foreign key(idFuncionario) references funcionario(idFuncionario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- PAGAMENTO
-- =========================
CREATE TABLE pagamento (
    idPagamento INT PRIMARY KEY AUTO_INCREMENT,
    forma_pagamento ENUM('DEBITO','CREDITO','PIX','BOLETO','DINHEIRO') NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_pagamento DATE NOT NULL,
    idVistoria INT NOT NULL UNIQUE,
    CONSTRAINT fk_pagamento_vistoria FOREIGN KEY (idVistoria) REFERENCES vistoria(idVistoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- Laudo Vistoria
-- =========================
create table laudo(
    idLaudo int primary key auto_increment,
    caminho_arquivo varchar(255),
    data_geracao datetime not null,
    idVistoria int not null unique,
    constraint fk_laudo_vistoria foreign key(idVistoria) references vistoria(idVistoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =========================
-- Desligamento (histórico)
-- =========================
CREATE TABLE desligamento_funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_funcionario INT NOT NULL,
    nome_funcionario VARCHAR(150) NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    data_desligamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_desligamento_funcionario 
        FOREIGN KEY (id_funcionario) 
        REFERENCES funcionario(idFuncionario) 
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

