package Vistoria.View;

import Vistoria.Controller.AgendamentoController;
import Vistoria.Model.Agendamento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelDashboard extends JPanel {

    private static final long serialVersionUID = 1L;
    private AgendamentoController agendamentoController;

    public PanelDashboard() {
        agendamentoController = new AgendamentoController();

        setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel titulo = new JLabel("DASHBOARD");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Painel de cards
        JPanel panelCards = new JPanel();
        panelCards.setBackground(new Color(245, 245, 245));
        panelCards.setLayout(new GridLayout(1, 2, 20, 0));

        // Cards
        JPanel cardPendentes = criarCard("Agendamentos Pendentes", contarPendentes(), new Color(255, 140, 0));
        JPanel cardConcluidos = criarCard("Agendamentos Concluídos", contarConcluidos(), new Color(60, 179, 113));

        panelCards.add(cardPendentes);
        panelCards.add(cardConcluidos);

        add(panelCards, BorderLayout.CENTER);
    }

    private JPanel criarCard(String titulo, int quantidade, Color corFundo) {
        JPanel card = new JPanel() {
            // Sombra suave
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(corFundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        // Quantidade
        JLabel lblQuantidade = new JLabel(String.valueOf(quantidade));
        lblQuantidade.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblQuantidade.setForeground(Color.WHITE);
        lblQuantidade.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblQuantidade, BorderLayout.CENTER);

        // Efeito hover
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
                card.setBackground(corFundo.darker());
                card.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                card.setBackground(corFundo);
                card.repaint();
            }
        });

        return card;
    }

    private int contarPendentes() {
        List<Agendamento> agendamentos = agendamentoController.listarTodos();
        return (int) agendamentos.stream()
                .filter(a -> "Pendente".equalsIgnoreCase(a.getStatus_agendamento()))
                .count();
    }

    private int contarConcluidos() {
        List<Agendamento> agendamentos = agendamentoController.listarTodos();
        return (int) agendamentos.stream()
                .filter(a -> "Concluido".equalsIgnoreCase(a.getStatus_agendamento()))
                .count();
    }
}
