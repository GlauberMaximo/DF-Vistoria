package Vistoria.View;

import Vistoria.Controller.AgendamentoController;
import Vistoria.Controller.ClienteController;
import Vistoria.Controller.VeiculoController;
import Vistoria.Controller.VistoriaController;
import Vistoria.Model.Agendamento;
import Vistoria.Model.Cliente;
import Vistoria.Model.Funcionario;
import Vistoria.Model.ResultadoVistoria;
import Vistoria.Model.Veiculo;
import Vistoria.Model.Vistoria;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanelVistoria extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable tabelaAgendamentos;
    private DefaultTableModel tableModel;
    private JTextArea txtObservacoes;
    private JComboBox<String> comboResultado;
    private JButton btnSalvar;

    private AgendamentoController agendamentoController;
    private VistoriaController vistoriaController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;

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
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
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

        JScrollPane scrollTable = new JScrollPane(tabelaAgendamentos);
        add(scrollTable, BorderLayout.NORTH);

        // ================= FORMULÁRIO =================
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(new EmptyBorder(10, 0, 10, 0));
        panelForm.setBackground(Color.WHITE);

        JLabel lblObservacoes = new JLabel("Observações da Vistoria:");
        lblObservacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblObservacoes.setAlignmentX(LEFT_ALIGNMENT);

        txtObservacoes = new JTextArea(5, 50);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        txtObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        scrollObs.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblResultado = new JLabel("Resultado da Vistoria:");
        lblResultado.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResultado.setBorder(new EmptyBorder(10, 0, 5, 0));
        lblResultado.setAlignmentX(LEFT_ALIGNMENT);

        comboResultado = new JComboBox<>(new String[]{"Aprovado", "Reprovado", "Aprovado com ressalvas"});
        comboResultado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboResultado.setMaximumSize(new Dimension(300, 30));
        comboResultado.setAlignmentX(LEFT_ALIGNMENT);

        panelForm.add(lblObservacoes);
        panelForm.add(scrollObs);
        panelForm.add(lblResultado);
        panelForm.add(comboResultado);

        add(panelForm, BorderLayout.CENTER);

        // ================= BOTÃO SALVAR =================
        btnSalvar = new JButton("Salvar Vistoria");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
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

                String veiculoNomePlaca = veiculo != null
                        ? veiculo.getNome_veiculo() + " (" + veiculo.getPlaca() + ")"
                        : "Desconhecido";

                tableModel.addRow(new Object[]{
                        a.getIdAgendamento(),
                        a.getData_agendamento(),
                        a.getHora(),
                        a.getTipo_vistoria(),
                        cliente != null ? cliente.getNome() : "Desconhecido",
                        veiculoNomePlaca
                });
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
            JOptionPane.showMessageDialog(this, "Digite as observações da vistoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String resultadoStr = (String) comboResultado.getSelectedItem();
        ResultadoVistoria resultadoEnum = ResultadoVistoria.valueOf(resultadoStr.toUpperCase().replace(" ", "_"));

        Vistoria vistoria = new Vistoria(
                LocalDate.now(),
                resultadoEnum,
                observacoes,
                agendamentoSelecionado.getIdAgendamento(),
                funcionarioLogado.getIdFuncionario()
        );

        vistoriaController.criarVistoria(vistoria);

        agendamentoSelecionado.setStatus_agendamento("Concluido");
        agendamentoController.atualizarAgendamento(agendamentoSelecionado);

        JOptionPane.showMessageDialog(this, "Vistoria salva com sucesso!");
        txtObservacoes.setText("");
        comboResultado.setSelectedIndex(0);
        carregarAgendamentosPendentes();
    }
}
