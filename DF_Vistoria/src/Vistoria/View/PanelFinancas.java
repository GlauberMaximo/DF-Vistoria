package Vistoria.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;

import Vistoria.DAO.PagamentoDAO;
import Vistoria.Model.Pagamento;

public class PanelFinancas extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotal, lblResumo;

    public PanelFinancas() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // título
        JLabel titulo = new JLabel("Relatório Financeiro", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        add(titulo, BorderLayout.NORTH);

        // modelo da tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Forma Pagamento", "Valor", "Data"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // tabela
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // alinhamento
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(1).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane, BorderLayout.CENTER);

        // rodapé com totais
        JPanel footer = new JPanel(new GridLayout(2, 1));
        footer.setBackground(Color.WHITE);

        lblTotal = new JLabel("Total recebido: R$ 0,00", JLabel.RIGHT);
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblResumo = new JLabel("Resumo por forma de pagamento: ", JLabel.RIGHT);
        lblResumo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        footer.add(lblTotal);
        footer.add(lblResumo);

        add(footer, BorderLayout.SOUTH);

        carregarPagamentos();
    }

    private void carregarPagamentos() {
        tableModel.setRowCount(0);
        PagamentoDAO dao = new PagamentoDAO();
        List<Pagamento> lista = dao.listarTodos();

        double total = 0.0;
        Map<String, Double> resumo = new LinkedHashMap<>(); // Usando LinkedHashMap para manter a ordem de inserção

        for (Pagamento p : lista) {
            tableModel.addRow(new Object[]{
                    p.getIdPagamento(),
                    p.getFormaPagamento(),
                    String.format("R$ %.2f", p.getValor()),
                    p.getDataPagamento()
            });

            // CORREÇÃO: Usando o nome da enumeração como chave
            String formaPagamentoStr = p.getFormaPagamento().name();
            // Acessando o valor do BigDecimal como double
            resumo.merge(formaPagamentoStr, p.getValor().doubleValue(), Double::sum);
            total += p.getValor().doubleValue();
        }

        lblTotal.setText(String.format("Total recebido: R$ %.2f", total));

        // monta resumo
        StringBuilder sb = new StringBuilder("<html>Resumo por forma: ");
        if (resumo.isEmpty()) {
            sb.append("Nenhum pagamento registrado.");
        } else {
            for (var entry : resumo.entrySet()) {
                // Formata o nome da forma de pagamento para ter a primeira letra maiúscula
                String nomeFormatado = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1).toLowerCase();
                sb.append("<br>• ").append(nomeFormatado)
                  .append(": R$ ").append(String.format("%.2f", entry.getValue()));
            }
        }
        sb.append("</html>");

        lblResumo.setText(sb.toString());
    }
}