use sistema_vistoria_df;

-- ==============================
-- INSERÇÃO DE DADOS DE EXEMPLO
-- ==============================

-- Inserir funcionários
INSERT INTO funcionario (nome, email, matricula, senha, cargo)
VALUES 
('Testador Vistoria', 'teste.vistoria@empresa.com', 'VIS-001', 'senha123', 'Vistoriador'),
('Testador Gerente', 'teste.gerente@empresa.com', 'GER-001', 'senha456', 'Gerente');

-- Inserir cliente
INSERT INTO cliente (nome, cpf, telefone, email, senha)
VALUES ('Maria Silva', '123.456.789-01', '(11) 98765-4321', 'maria.silva@email.com', 'senha_segura123');

-- Inserir veículo
INSERT INTO veiculo (placa, tipo_veiculo, nome_veiculo, modelo, ano_veiculo, chassi, observacoes, idCliente)
VALUES ('ABC-1234', 'Carro', 'Ford Fusion', 'Titanium', 2018, 'ABC12345DEF678901', 'Pequenos arranhões na porta do motorista.', 1);

-- Inserir agendamentos
-- Agendamento pendente
INSERT INTO agendamento (data_agendamento, hora, tipo_vistoria, status_agendamento, idCliente, idVeiculo)
VALUES ('2025-09-15', '10:30:00', 'Vistoria Prévia', 'Pendente', 1, 1);

-- Agendamento concluído (com vistoria e pagamento)
INSERT INTO agendamento (data_agendamento, hora, tipo_vistoria, status_agendamento, idCliente, idVeiculo)
VALUES ('2025-09-01', '14:00:00', 'Vistoria de Transferência', 'Concluido', 1, 1);

-- Inserir vistoria para o agendamento concluído
INSERT INTO vistoria (data_vistoria, resultado, observacoes, idAgendamento, idFuncionario)
VALUES ('2025-09-01', 'Aprovado', 'Vistoria completa e sem falhas graves.', 2, 1);

-- Inserir pagamento para a vistoria concluída
INSERT INTO pagamento (forma_pagamento, valor, data_pagamento, idVistoria)
VALUES ('Pix', 150.00, '2025-09-01', 1);

-- Inserir agendamento cancelado
INSERT INTO agendamento (data_agendamento, hora, tipo_vistoria, status_agendamento, idCliente, idVeiculo)
VALUES ('2025-08-28', '09:00:00', 'Vistoria Cautelar', 'Cancelado', 1, 1);

-- ==============================
-- FUNCIONÁRIOS VISTORIADORES (10)
-- ==============================
INSERT INTO funcionario (nome, email, matricula, senha, cargo) VALUES
('Carlos Andrade', 'carlos.andrade@empresa.com', 'VIS-002', 'senha123', 'Vistoriador'),
('Fernanda Lima', 'fernanda.lima@empresa.com', 'VIS-003', 'senha123', 'Vistoriador'),
('João Pereira', 'joao.pereira@empresa.com', 'VIS-004', 'senha123', 'Vistoriador'),
('Mariana Souza', 'mariana.souza@empresa.com', 'VIS-005', 'senha123', 'Vistoriador'),
('Rodrigo Alves', 'rodrigo.alves@empresa.com', 'VIS-006', 'senha123', 'Vistoriador'),
('Patrícia Gomes', 'patricia.gomes@empresa.com', 'VIS-007', 'senha123', 'Vistoriador'),
('Thiago Martins', 'thiago.martins@empresa.com', 'VIS-008', 'senha123', 'Vistoriador'),
('Juliana Costa', 'juliana.costa@empresa.com', 'VIS-009', 'senha123', 'Vistoriador'),
('Rafael Oliveira', 'rafael.oliveira@empresa.com', 'VIS-010', 'senha123', 'Vistoriador'),
('Camila Ferreira', 'camila.ferreira@empresa.com', 'VIS-011', 'senha123', 'Vistoriador');

-- ==============================
-- CONSULTAS DE VERIFICAÇÃO
-- ==============================
select * from cliente;
select * from veiculo;
select * from agendamento;
select * from vistoria;
select * from pagamento;
select * from funcionario;
SELECT * FROM desligamento_funcionario;
