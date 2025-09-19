package Vistoria.View;

import Vistoria.Controller.*;
import Vistoria.Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class PanelVistoria extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tabelaAgendamentos;
    private DefaultTableModel tableModel;
    private JTextArea txtObservacoes;
    private JComboBox<String> comboResultado;
    private JComboBox<String> comboFormaPagamento; // NOVO: Adicionando o JComboBox
    private JTextField txtValor;
    private JButton btnSalvar;

    private AgendamentoController agendamentoController;
    private VistoriaController vistoriaController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;
    private PagamentoController pagamentoController;
    private LaudoController laudoController; // CORREÇÃO: Adicionando o LaudoController

    private Funcionario funcionarioLogado;
    private Agendamento agendamentoSelecionado;

    public PanelVistoria(Funcionario funcionario) {
        this.funcionarioLogado = funcionario;

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        agendamentoController = new AgendamentoController();
        vistoriaController = new VistoriaController();
        clienteController = new ClienteController();
        veiculoController = new VeiculoController();
        pagamentoController = new PagamentoController();
        laudoController = new LaudoController(); // CORREÇÃO: Inicializando o LaudoController

        // ================= TABELA =================
        String[] colunas = {"ID", "Data", "Hora", "Tipo Vistoria", "Cliente", "Veículo"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAgendamentos = new JTable(tableModel);
        tabelaAgendamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaAgendamentos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaAgendamentos.setRowHeight(30);
        tabelaAgendamentos.setFillsViewportHeight(true);
        tabelaAgendamentos.setIntercellSpacing(new Dimension(0, 0));
        tabelaAgendamentos.setShowGrid(false);
        tabelaAgendamentos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(140, 204, 251));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        JTableHeader header = tabelaAgendamentos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(140, 204, 251));
        header.setForeground(Color.BLACK);
        header.setReorderingAllowed(false);

        // Ajusta a largura das colunas
        tabelaAgendamentos.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaAgendamentos.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabelaAgendamentos.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabelaAgendamentos.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabelaAgendamentos.getColumnModel().getColumn(4).setPreferredWidth(180);
        tabelaAgendamentos.getColumnModel().getColumn(5).setPreferredWidth(200);

        JScrollPane scrollTable = new JScrollPane(tabelaAgendamentos);
        add(scrollTable, BorderLayout.NORTH);

        // ================= FORMULÁRIO =================
        JPanel panelForm = new JPanel(new BorderLayout(10, 10));
        panelForm.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelForm.setBackground(Color.WHITE);

        // Painel para a área de Observações (título e JTextArea)
        JPanel panelObs = new JPanel(new BorderLayout(0, 5));
        panelObs.setBackground(Color.WHITE);

        JLabel lblObservacoes = new JLabel("Observações da Vistoria:");
        lblObservacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelObs.add(lblObservacoes, BorderLayout.PAGE_START);

        txtObservacoes = new JTextArea(8, 50);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        txtObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        panelObs.add(scrollObs, BorderLayout.CENTER);

        // Painel para os controles de Resultado e Valor
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelControles.setBackground(Color.WHITE);

        JLabel lblResultado = new JLabel("Resultado da Vistoria:");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelControles.add(lblResultado);

        comboResultado = new JComboBox<>(new String[]{"Aprovado", "Reprovado", "Aprovado com ressalvas"});
        comboResultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboResultado.setPreferredSize(new Dimension(180, 30));
        panelControles.add(comboResultado);

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelControles.add(lblValor);

        txtValor = new JTextField();
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtValor.setPreferredSize(new Dimension(120, 30));
        panelControles.add(txtValor);
        
        // NOVO: Adicionar JComboBox para a forma de pagamento
        JLabel lblFormaPagamento = new JLabel("Forma de Pagamento:");
        lblFormaPagamento.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelControles.add(lblFormaPagamento);

        comboFormaPagamento = new JComboBox<>(new String[]{"DEBITO", "CREDITO", "PIX", "BOLETO", "DINHEIRO"});
        comboFormaPagamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFormaPagamento.setPreferredSize(new Dimension(180, 30));
        panelControles.add(comboFormaPagamento);

        // Adiciona os painéis ao painel de formulário principal
        panelForm.add(panelObs, BorderLayout.CENTER);
        panelForm.add(panelControles, BorderLayout.PAGE_END);

        add(panelForm, BorderLayout.CENTER);

        // ================= BOTÃO SALVAR =================
        btnSalvar = new JButton("Salvar Vistoria");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnSalvar.addActionListener(e -> salvarVistoria());

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButton.setBackground(Color.WHITE);
        panelButton.add(btnSalvar);
        add(panelButton, BorderLayout.SOUTH);

        // ================= EVENTO SELEÇÃO TABELA =================
        tabelaAgendamentos.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                carregarAgendamentoSelecionado();
            }
        });

        carregarAgendamentosPendentes();
    }

    private void carregarAgendamentosPendentes() {
        tableModel.setRowCount(0);
        List<Agendamento> agendamentos = agendamentoController.listarTodos();
        for (Agendamento a : agendamentos) {
            if ("Pendente".equalsIgnoreCase(a.getStatus_agendamento())) {
                Cliente cliente = clienteController.buscarClientePorId(a.getIdCliente());
                Veiculo veiculo = veiculoController.buscarPorId(a.getIdVeiculo());
                String veiculoNomePlaca = veiculo != null ? veiculo.getNome_veiculo() + " (" + veiculo.getPlaca() + ")"
                        : "Desconhecido";

                tableModel.addRow(new Object[]{a.getIdAgendamento(), a.getData_agendamento(), a.getHora(),
                        a.getTipo_vistoria(), cliente != null ? cliente.getNome() : "Desconhecido", veiculoNomePlaca});
            }
        }
    }

    private void carregarAgendamentoSelecionado() {
        int row = tabelaAgendamentos.getSelectedRow();
        if (row >= 0) {
            int idAgendamento = (int) tableModel.getValueAt(row, 0);
            agendamentoSelecionado = agendamentoController.buscarPorId(idAgendamento);

            if (agendamentoSelecionado != null) {
                txtObservacoes.setText("");
                comboResultado.setSelectedIndex(0);
                txtValor.setText("");
                comboFormaPagamento.setSelectedIndex(0);
            }
        }
    }

    private void salvarVistoria() {
        if (agendamentoSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String observacoes = txtObservacoes.getText().trim();
        if (observacoes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite as observações da vistoria.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal valor;
        try {
            valor = new BigDecimal(txtValor.getText().replace(",", "."));
            if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                JOptionPane.showMessageDialog(this, "Digite um valor válido para a vistoria.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "O valor deve ser um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultadoStr = (String) comboResultado.getSelectedItem();
        ResultadoVistoria resultadoEnum = ResultadoVistoria.valueOf(resultadoStr.toUpperCase().replace(" ", "_"));

        // Cria e salva a Vistoria
        Vistoria vistoria = new Vistoria(LocalDate.now(), resultadoEnum, observacoes,
                agendamentoSelecionado.getIdAgendamento(), funcionarioLogado.getIdFuncionario());
        vistoriaController.criarVistoria(vistoria);

        // NOVO: Pega a forma de pagamento selecionada
        String formaPagamentoStr = (String) comboFormaPagamento.getSelectedItem();
        FormaPagamento formaPagamentoEnum = FormaPagamento.valueOf(formaPagamentoStr.toUpperCase());

        // Cria e salva o Pagamento com a forma de pagamento selecionada
        Pagamento pagamento = new Pagamento(formaPagamentoEnum, valor, LocalDate.now(), vistoria.getIdVistoria());
        pagamentoController.criarPagamento(pagamento);

        // CORREÇÃO: Chamada para o método de gerar o laudo
        laudoController.gerarLaudo("laudos/laudo_" + vistoria.getIdVistoria() + ".pdf", vistoria.getIdVistoria());

        agendamentoSelecionado.setStatus_agendamento("Concluido");
        agendamentoController.atualizarAgendamento(agendamentoSelecionado);

        JOptionPane.showMessageDialog(this, "Vistoria, pagamento e laudo salvos com sucesso!");
        txtObservacoes.setText("");
        comboResultado.setSelectedIndex(0);
        txtValor.setText("");
        comboFormaPagamento.setSelectedIndex(0);
        carregarAgendamentosPendentes();
    }
}