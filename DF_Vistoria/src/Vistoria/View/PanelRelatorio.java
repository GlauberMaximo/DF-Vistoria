package Vistoria.View;

import Vistoria.Controller.AgendamentoController;
import Vistoria.Controller.ClienteController;
import Vistoria.Controller.VeiculoController;
import Vistoria.Model.Agendamento;
import Vistoria.Model.Cliente;
import Vistoria.Model.Veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelRelatorio extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable tabelaRelatorios;
    private DefaultTableModel tableModel;

    private AgendamentoController agendamentoController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;

    public PanelRelatorio() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(245, 245, 245));

        agendamentoController = new AgendamentoController();
        clienteController = new ClienteController();
        veiculoController = new VeiculoController();

        // ====== TABELA ======
        String[] colunas = {"ID Agendamento", "Cliente", "Veículo", "Placa", "Tipo Vistoria", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela não editável
            }
        };
        tabelaRelatorios = new JTable(tableModel);
        tabelaRelatorios.setFillsViewportHeight(true);
        tabelaRelatorios.setRowHeight(30);
        tabelaRelatorios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaRelatorios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabelaRelatorios.getTableHeader().setBackground(new Color(187, 208, 235));
        tabelaRelatorios.getTableHeader().setForeground(Color.BLACK);
        tabelaRelatorios.setSelectionBackground(new Color(180, 220, 255));
        tabelaRelatorios.setSelectionForeground(Color.BLACK);

        // Renderizador para linhas alternadas
        tabelaRelatorios.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 230, 230));
                }
                return c;
            }
        });

        JScrollPane scrollTable = new JScrollPane(tabelaRelatorios);
        add(scrollTable, BorderLayout.CENTER);

        carregarAgendamentosConcluidos();
    }

    private void carregarAgendamentosConcluidos() {
        tableModel.setRowCount(0); // limpa tabela
        List<Agendamento> agendamentos = agendamentoController.listarTodos();

        for (Agendamento a : agendamentos) {
            if ("Concluido".equalsIgnoreCase(a.getStatus_agendamento())) {
                Cliente cliente = clienteController.buscarClientePorId(a.getIdCliente());
                Veiculo veiculo = veiculoController.buscarPorId(a.getIdVeiculo());

                tableModel.addRow(new Object[]{
                        a.getIdAgendamento(),
                        cliente != null ? cliente.getNome() : "Desconhecido",
                        veiculo != null ? veiculo.getNome_veiculo() : "Desconhecido",
                        veiculo != null ? veiculo.getPlaca() : "Desconhecido",
                        a.getTipo_vistoria(),
                        a.getStatus_agendamento()
                });
            }
        }
    }
}
