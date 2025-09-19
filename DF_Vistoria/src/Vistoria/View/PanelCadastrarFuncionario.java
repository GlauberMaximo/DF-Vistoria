package Vistoria.View;

import Vistoria.Controller.FuncionarioController;
import Vistoria.Model.Funcionario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelCadastrarFuncionario extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtNome;
    private JTextField txtMatricula;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnSalvar;

    private FuncionarioController funcionarioController;

    public PanelCadastrarFuncionario() {
        this.funcionarioController = new FuncionarioController();

        setLayout(new GridBagLayout()); // Centraliza tudo
        setBackground(new Color(245, 247, 250)); // cinza claro elegante

        // ===== CARD PRINCIPAL =====
        JPanel card = new JPanel(new BorderLayout(20, 20));
        card.setBackground(Color.WHITE);
        card.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        card.setPreferredSize(new Dimension(450, 400));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        // ===== TÍTULO =====
        JLabel titulo = new JLabel("Cadastrar Funcionário", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(new Color(70, 130, 180));
        card.add(titulo, BorderLayout.NORTH);

        // ===== FORMULÁRIO =====
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNome = new JTextField(20);

        JLabel lblMatricula = new JLabel("Matrícula:");
        lblMatricula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMatricula = new JTextField(20);

        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSenha = new JPasswordField(20);

        // Linha 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblNome, gbc);
        gbc.gridx = 1;
        panelForm.add(txtNome, gbc);

        // Linha 2
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(lblMatricula, gbc);
        gbc.gridx = 1;
        panelForm.add(txtMatricula, gbc);

        // Linha 3
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(lblEmail, gbc);
        gbc.gridx = 1;
        panelForm.add(txtEmail, gbc);

        // Linha 4
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(lblSenha, gbc);
        gbc.gridx = 1;
        panelForm.add(txtSenha, gbc);

        card.add(panelForm, BorderLayout.CENTER);

        // ===== BOTÃO =====
        btnSalvar = new JButton("Salvar Funcionário");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setPreferredSize(new Dimension(200, 40));
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        JPanel panelButton = new JPanel();
        panelButton.setBackground(Color.WHITE);
        panelButton.add(btnSalvar);
        card.add(panelButton, BorderLayout.SOUTH);

        // Adiciona o card no centro
        add(card, new GridBagConstraints());

        // Evento do botão
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarFuncionario();
            }
        });
    }

    private void cadastrarFuncionario() {
        String nome = txtNome.getText().trim();
        String matricula = txtMatricula.getText().trim();
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (nome.isEmpty() || matricula.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setMatricula(matricula);
        funcionario.setEmail(email);
        funcionario.setSenha(senha);
        funcionario.setCargo("Vistoriador");

        funcionarioController.adicionarFuncionario(funcionario);

        JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        txtNome.setText("");
        txtMatricula.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
