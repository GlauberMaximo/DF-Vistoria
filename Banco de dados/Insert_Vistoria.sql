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
-- CONSULTAS DE VERIFICAÇÃO
-- ==============================
select * from cliente;
select * from veiculo;
select * from agendamento;
select * from vistoria;
select * from pagamento;
select * from funcionario;