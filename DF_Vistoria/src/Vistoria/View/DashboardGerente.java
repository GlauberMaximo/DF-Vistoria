package Vistoria.View;

import Vistoria.Model.Funcionario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DashboardGerente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelCenter;

    private Funcionario funcionarioLogado;

    // Painéis centrais
    private JTabbedPane panelRelatorios;
    private JPanel panelFinancas; // Declarado
    private JPanel panelCadastrarFuncionario;
    private JPanel panelListarFuncionario;

    public DashboardGerente(Funcionario funcionario) {
        this.funcionarioLogado = funcionario;

        setTitle("Área do Gerente - Sistema DF-Vistoria");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Confirmação ao sair
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcao = JOptionPane.showConfirmDialog(DashboardGerente.this,
                        "Deseja realmente sair?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });

        // ================== CONTENT PANE ==================
        contentPane = new JPanel(new BorderLayout(0, 0));
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
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

        JLabel lblGerente = new JLabel("Usuário: " + funcionarioLogado.getNome());
        lblGerente.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblGerente.setBorder(new EmptyBorder(0, 20, 10, 20));

        // Botões do menu
        JButton btnRelatorio = criarBotaoMenu("Relatórios", "icones/file.png");
        JButton btnFinancas = criarBotaoMenu("Finanças", "icones/finance.png");
        JButton btnCadastrar = criarBotaoMenu("Cadastrar Funcionário", "icones/adicionar-usuario.png");
        JButton btnListar = criarBotaoMenu("Listar Funcionários", "icones/task.png");
        JButton btnSair = criarBotaoMenu("Sair", "icones/saida.png");

        panelLeft.add(titulo);
        panelLeft.add(lblGerente);
        panelLeft.add(Box.createVerticalStrut(20));
        panelLeft.add(btnRelatorio);
        panelLeft.add(btnFinancas);
        panelLeft.add(btnCadastrar);
        panelLeft.add(btnListar);
        panelLeft.add(Box.createVerticalGlue());
        panelLeft.add(btnSair);

        // ================== PAINEL CENTRAL ==================
        panelRelatorios = new JTabbedPane();
        panelRelatorios.addTab("Agendamentos", new PanelRelatorio());
        panelRelatorios.addTab("Desligamentos", new PanelRelatorioDesligamentos());

        // CORREÇÃO: Inicializando o painel de finanças com a classe correta
        panelFinancas = new PanelFinancas();

        panelCadastrarFuncionario = new PanelCadastrarFuncionario();
        panelListarFuncionario = new PanelListarFuncionario();

        panelCenter = new JPanel(new CardLayout());
        panelCenter.add(panelRelatorios, "Relatorios");
        panelCenter.add(panelFinancas, "Financas");
        panelCenter.add(panelCadastrarFuncionario, "Cadastrar");
        panelCenter.add(panelListarFuncionario, "Listar");
        contentPane.add(panelCenter, BorderLayout.CENTER);

        // ================== AÇÕES DOS BOTÕES ==================
        btnRelatorio.addActionListener(e -> mostrarTela("Relatorios"));
        btnFinancas.addActionListener(e -> mostrarTela("Financas"));
        btnCadastrar.addActionListener(e -> mostrarTela("Cadastrar"));
        btnListar.addActionListener(e -> mostrarTela("Listar"));
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

    // Getters para painéis
    public JTabbedPane getPanelRelatorios() { return panelRelatorios; }
    public JPanel getPanelFinancas() { return panelFinancas; }
    public JPanel getPanelCadastrarFuncionario() { return panelCadastrarFuncionario; }
    public JPanel getPanelListarFuncionario() { return panelListarFuncionario; }

}