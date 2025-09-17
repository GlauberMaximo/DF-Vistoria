package Vistoria.View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Vistoria.Model.Funcionario;

public class DashboardVistoria extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelCenter;

    private PanelDashboard panelDashboard;
    private PanelVistoria panelVistoria;
    private PanelRelatorio panelRelatorios;

    private Funcionario funcionarioLogado; 

    public DashboardVistoria(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo!");
        }
        this.funcionarioLogado = funcionario;

        setTitle("Área de Vistoria - Sistema DF-Vistoria");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Confirmação ao sair
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcao = JOptionPane.showConfirmDialog(DashboardVistoria.this,
                        "Deseja realmente sair?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });

        // ================== CONTENT PANE ==================
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // ================= MENU LATERAL ==================
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(187, 208, 235));
        panelLeft.setPreferredSize(new Dimension(230, getHeight()));
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        contentPane.add(panelLeft, BorderLayout.WEST);

        JLabel titulo = new JLabel("DF-Vistoria");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.BLACK);
        titulo.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblFuncionario = new JLabel("Usuário: " + funcionarioLogado.getNome());
        lblFuncionario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFuncionario.setBorder(new EmptyBorder(0, 20, 10, 20));

        JButton btnDashboard = criarBotaoMenu("Dashboard", "icones/dashboard.png");
        JButton btnVistoria = criarBotaoMenu("Vistoria", "icones/clipboard.png");
        JButton btnRelatorios = criarBotaoMenu("Relatórios", "icones/file.png");
        JButton btnSair = criarBotaoMenu("Sair", "icones/saida.png");

        panelLeft.add(titulo);
        panelLeft.add(lblFuncionario);
        panelLeft.add(Box.createVerticalStrut(20));
        panelLeft.add(btnDashboard);
        panelLeft.add(btnVistoria);
        panelLeft.add(btnRelatorios);
        panelLeft.add(Box.createVerticalGlue());
        panelLeft.add(btnSair);

        // ================== PAINEL CENTRAL ==================
        panelDashboard = new PanelDashboard();
        panelVistoria = new PanelVistoria(funcionario);
        panelRelatorios = new PanelRelatorio();

        panelCenter = new JPanel(new CardLayout());
        panelCenter.add(panelDashboard, "Dashboard");
        panelCenter.add(panelVistoria, "Vistoria");
        panelCenter.add(panelRelatorios, "Relatorios");
        contentPane.add(panelCenter, BorderLayout.CENTER);

        // ================== AÇÕES DOS BOTÕES ==================
        btnDashboard.addActionListener(e -> mostrarTela("Dashboard"));
        btnVistoria.addActionListener(e -> mostrarTela("Vistoria"));
        btnRelatorios.addActionListener(e -> mostrarTela("Relatorios"));
        btnSair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                dispose();
                new Login().setVisible(true);
            }
        });

        // mostra o Dashboard inicial
        mostrarTela("Dashboard");
    }

    private JButton criarBotaoMenu(String texto, String iconPath) {
        JButton btn = new JButton(texto);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setBackground(new Color(187, 208, 235));
        btn.setForeground(Color.BLACK);
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

    private void mostrarTela(String nomeTela) {
        CardLayout cl = (CardLayout) panelCenter.getLayout();
        cl.show(panelCenter, nomeTela);
    }
}
