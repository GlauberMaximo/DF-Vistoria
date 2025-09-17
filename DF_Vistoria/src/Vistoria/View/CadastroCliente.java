package Vistoria.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import Vistoria.Controller.ClienteController;
import Vistoria.Model.Cliente;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.text.ParseException;

public class CadastroCliente extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNome, txtEmail;
    private JFormattedTextField txtCpf, txtTelefone;
    private JPasswordField txtSenha, txtConfirmaSenha;
    private ClienteController controller;

    public CadastroCliente() {
        setType(Type.UTILITY);
        setTitle("Cadastro - Sistema Vistoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        controller = new ClienteController();

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        // Painel lateral esquerdo
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(187, 208, 235));
        panelLeft.setPreferredSize(new Dimension(300, 200));

        ImageIcon originalIcon = new ImageIcon(Login.class.getResource("/imagens/logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(scaledImage));

        JLabel lblSistema = new JLabel("Sistema de Vistoria Veicular");
        lblSistema.setFont(new Font("Arial", Font.BOLD, 16));
        lblSistema.setForeground(Color.DARK_GRAY);

        JLabel lblVersao = new JLabel("V1.0");
        lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblVersao.setForeground(Color.WHITE);

        // Painel direito
        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.WHITE);
        panelRight.setBorder(new EmptyBorder(30, 50, 30, 50));

        JLabel Titulo = new JLabel("Informe seus dados");
        Titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField(20);

        JLabel lblCpf = new JLabel("CPF:");
        try {
            MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
            maskCpf.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(maskCpf);
            txtCpf.setColumns(20);
        } catch (ParseException e) {
            txtCpf = new JFormattedTextField();
        }

        JLabel lblTelefone = new JLabel("Telefone:");
        try {
            MaskFormatter maskTel = new MaskFormatter("(##) #####-####");
            maskTel.setPlaceholderCharacter('_');
            txtTelefone = new JFormattedTextField(maskTel);
            txtTelefone.setColumns(20);
        } catch (ParseException e) {
            txtTelefone = new JFormattedTextField();
        }

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(20);

        JLabel lblConfirmaSenha = new JLabel("Confirme a Senha:");
        txtConfirmaSenha = new JPasswordField(20);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(140, 204, 251));
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setForeground(new Color(255, 255, 255));
        btnCancelar.setBackground(new Color(234, 27, 21));
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Layout painel esquerdo
        contentPane.add(panelLeft, BorderLayout.WEST);
        GroupLayout gl_panelLeft = new GroupLayout(panelLeft);
        gl_panelLeft.setHorizontalGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelLeft.createSequentialGroup()
                        .addGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panelLeft.createSequentialGroup().addGap(75).addComponent(logo))
                                .addGroup(gl_panelLeft.createSequentialGroup().addGap(44).addComponent(lblSistema))
                                .addGroup(gl_panelLeft.createSequentialGroup().addGap(133).addComponent(lblVersao)))
                        .addContainerGap(44, Short.MAX_VALUE)));
        gl_panelLeft.setVerticalGroup(gl_panelLeft.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelLeft.createSequentialGroup().addGap(137).addComponent(logo)
                        .addPreferredGap(ComponentPlacement.UNRELATED).addComponent(lblSistema)
                        .addPreferredGap(ComponentPlacement.RELATED).addComponent(lblVersao).addGap(224)));
        panelLeft.setLayout(gl_panelLeft);
        contentPane.add(panelRight, BorderLayout.CENTER);

        // Layout painel direito
        GroupLayout gl_panelRight = new GroupLayout(panelRight);
        gl_panelRight.setHorizontalGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelRight.createSequentialGroup()
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panelRight.createSequentialGroup().addGap(97).addComponent(Titulo))
                                .addGroup(gl_panelRight.createSequentialGroup()
                                        .addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
                                                .addComponent(lblNome).addComponent(lblCpf).addComponent(lblTelefone)
                                                .addComponent(lblEmail).addComponent(lblSenha)
                                                .addComponent(lblConfirmaSenha))
                                        .addGap(18)
                                        .addGroup(gl_panelRight.createParallelGroup(Alignment.LEADING, false)
                                                .addGroup(gl_panelRight.createSequentialGroup()
                                                        .addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 100,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addGap(30).addComponent(btnCancelar,
                                                                GroupLayout.PREFERRED_SIZE, 100,
                                                                GroupLayout.PREFERRED_SIZE))
                                                .addComponent(txtNome).addComponent(txtCpf).addComponent(txtTelefone)
                                                .addComponent(txtEmail).addComponent(txtSenha)
                                                .addComponent(txtConfirmaSenha))))
                        .addContainerGap(85, Short.MAX_VALUE)));
        gl_panelRight.setVerticalGroup(gl_panelRight.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelRight.createSequentialGroup().addGap(21).addComponent(Titulo).addGap(30)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE).addComponent(lblNome)
                                .addComponent(txtNome, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE).addComponent(lblCpf)
                                .addComponent(txtCpf, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE).addComponent(lblTelefone)
                                .addComponent(txtTelefone, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE).addComponent(lblEmail)
                                .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE).addComponent(lblSenha)
                                .addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(15)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE).addComponent(lblConfirmaSenha)
                                .addComponent(txtConfirmaSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                        .addGap(30)
                        .addGroup(gl_panelRight.createParallelGroup(Alignment.BASELINE)
                                .addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(120, Short.MAX_VALUE)));
        panelRight.setLayout(gl_panelRight);

        // Eventos
        btnSalvar.addActionListener(e -> salvarCliente());
        btnCancelar.addActionListener(e -> voltarLogin());

        // Pressionar Enter para salvar
        getRootPane().setDefaultButton(btnSalvar);
    }

    private void salvarCliente() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword());
        String confirmaSenha = new String(txtConfirmaSenha.getPassword());

        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmaSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confirmaSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = new Cliente(nome, cpf, telefone, email, senha);
        controller.adicionarCliente(cliente);

        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        voltarLogin();
    }

    private void voltarLogin() {
        dispose();
        new Login().setVisible(true);
    }
}
