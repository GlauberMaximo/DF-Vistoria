package Vistoria.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelCenter; // painel central com CardLayout

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DashboardCliente frame = new DashboardCliente();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DashboardCliente() {
        setTitle("Área do Cliente - Sistema de Vistoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // abre maximizado
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new BorderLayout(0, 0)); // compatível com WindowBuilder
        setContentPane(contentPane);

        // ================= MENU LATERAL ==================
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(187, 208, 235)); // azul da sua imagem
        panelLeft.setPreferredSize(new Dimension(230, getHeight()));
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        contentPane.add(panelLeft, BorderLayout.WEST);

        JLabel titulo = new JLabel("DF-Vistoria");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 0, 0));
        titulo.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Botões de menu
        JButton btnDashboard = criarBotaoMenu("Dashboard", "icones/dashboard.png");
        JButton btnAgendar = criarBotaoMenu("Agendar Vistoria", "icones/calendario.png");
        JButton btnCadastrar = criarBotaoMenu("Cadastrar Veículo", "icones/carro.png");
        JButton btnLaudo = criarBotaoMenu("Emitir Laudo", "icones/emitir.png");
        JButton btnSair = criarBotaoMenu("Sair", "icones/saida.png");

        // adiciona ao painel lateral
        panelLeft.add(titulo);
        panelLeft.add(Box.createVerticalStrut(30));
        panelLeft.add(btnDashboard);
        panelLeft.add(btnAgendar);
        panelLeft.add(btnCadastrar);
        panelLeft.add(btnLaudo);
        panelLeft.add(Box.createVerticalGlue());
        panelLeft.add(btnSair);

        // ================== PAINEL CENTRAL ==================
        panelCenter = new JPanel(new CardLayout());
        panelCenter.add(new JLabel("Tela Dashboard", SwingConstants.CENTER), "Dashboard");
        panelCenter.add(new JLabel("Tela Agendar Vistoria", SwingConstants.CENTER), "Agendar");
        panelCenter.add(new JLabel("Tela Cadastrar Veículo", SwingConstants.CENTER), "Cadastrar");
        panelCenter.add(new JLabel("Tela Emitir Laudo", SwingConstants.CENTER), "Laudo");
        contentPane.add(panelCenter, BorderLayout.CENTER);

        // ================== AÇÕES DOS BOTÕES ==================
        btnDashboard.addActionListener(e -> mostrarTela("Dashboard"));
        btnAgendar.addActionListener(e -> mostrarTela("Agendar"));
        btnCadastrar.addActionListener(e -> mostrarTela("Cadastrar"));
        btnLaudo.addActionListener(e -> mostrarTela("Laudo"));
        btnSair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                dispose();
                new Login().setVisible(true);
            }
        });
    }

    // método auxiliar para criar botões de menu
    private JButton criarBotaoMenu(String texto, String iconPath) {
        JButton btn = new JButton(texto);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(new Color(187, 208, 235));
        btn.setForeground(new Color(0, 0, 0));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setIconTextGap(10);

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/" + iconPath));
            Image scaled = icon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.out.println("Ícone não encontrado: " + iconPath);
        }
        return btn;
    }

    // método para alternar entre as telas no painel central
    private void mostrarTela(String nomeTela) {
        CardLayout cl = (CardLayout) panelCenter.getLayout();
        cl.show(panelCenter, nomeTela);
    }
}
