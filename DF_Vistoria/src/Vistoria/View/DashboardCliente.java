package Vistoria.View;

import Vistoria.Model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.border.EmptyBorder;

public class DashboardCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panelCenter;
    private Cliente clienteLogado;
    
    // Novas referências para os painéis
    private PanelAgendarVistoria panelAgendar;
    private PanelCadastrarVeiculo panelCadastrar;
    private JPanel panelDashboard, panelLaudo;

    // Construtor atualizado para receber o objeto Cliente
    public DashboardCliente(Cliente cliente) {
        this.clienteLogado = cliente;
        setTitle("Área do Cliente - Sistema de Vistoria");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        // Adiciona um WindowListener para tratar o fechamento da janela
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int opcao = JOptionPane.showConfirmDialog(DashboardCliente.this,
                    "Deseja realmente sair?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);
                if (opcao == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login().setVisible(true);
                }
            }
        });

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
        
        // CORREÇÃO: Cria um JPanel para cada JLabel
        panelDashboard = new JPanel();
        panelDashboard.add(new JLabel("Tela Dashboard do Cliente", SwingConstants.CENTER));
        
        panelLaudo = new JPanel();
        panelLaudo.add(new JLabel("Tela Emitir Laudo", SwingConstants.CENTER));
        
        // Instancia os outros painéis uma única vez
        panelAgendar = new PanelAgendarVistoria(clienteLogado);
        panelCadastrar = new PanelCadastrarVeiculo(clienteLogado);

        // Adiciona os painéis ao CardLayout
        panelCenter = new JPanel(new CardLayout());
        panelCenter.add(panelDashboard, "Dashboard");
        panelCenter.add(panelAgendar, "Agendar");
        panelCenter.add(panelCadastrar, "Cadastrar");
        panelCenter.add(panelLaudo, "Laudo");
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

    // Método getter público para o painel de agendamento
    public PanelAgendarVistoria getPanelAgendar() {
        return panelAgendar;
    }
    
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

    private void mostrarTela(String nomeTela) {
        CardLayout cl = (CardLayout) panelCenter.getLayout();
        cl.show(panelCenter, nomeTela);
    }
}