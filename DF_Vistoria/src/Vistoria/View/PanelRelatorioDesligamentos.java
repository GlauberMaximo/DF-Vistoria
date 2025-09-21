package Vistoria.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer; // <-- IMPORT NECESSÁRIO
import java.awt.*;
import java.util.List;

import Vistoria.DAO.DesligamentoDAO;
import Vistoria.Model.Desligamento;

public class PanelRelatorioDesligamentos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel tableModel;

    public PanelRelatorioDesligamentos() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Relatório de Desligamentos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Modelo da tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Funcionário", "Motivo", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // bloqueia edição direta
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Alternar cores nas linhas (zebrado)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color evenColor = new Color(240, 248, 255);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? evenColor : Color.WHITE);
                }
                return comp;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(titulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        carregarDesligamentos();
    }

    private void carregarDesligamentos() {
        tableModel.setRowCount(0);
        DesligamentoDAO dao = new DesligamentoDAO();
        List<Desligamento> lista = dao.listarTodos();

        for (Desligamento d : lista) {
            tableModel.addRow(new Object[]{
                    d.getId(),
                    d.getNomeFuncionario(),
                    d.getMotivo(),
                    d.getDataDesligamento()
            });
        }
    }
}
