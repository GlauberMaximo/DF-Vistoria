package Vistoria.View;

import Vistoria.Controller.FuncionarioController;
import Vistoria.Model.Funcionario;

import javax.swing.*;
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

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Cadastrar Funcionário (Vistoriador)", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        // Painel central com formulário
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
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

        // Linha 1 - Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(lblNome, gbc);
        gbc.gridx = 1;
        panelForm.add(txtNome, gbc);

        // Linha 2 - Matrícula
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(lblMatricula, gbc);
        gbc.gridx = 1;
        panelForm.add(txtMatricula, gbc);

        // Linha 3 - Email
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(lblEmail, gbc);
        gbc.gridx = 1;
        panelForm.add(txtEmail, gbc);

        // Linha 4 - Senha
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(lblSenha, gbc);
        gbc.gridx = 1;
        panelForm.add(txtSenha, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botão de salvar
        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSalvar.setBackground(new Color(70, 130, 180));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelButton = new JPanel();
        panelButton.setBackground(Color.WHITE);
        panelButton.add(btnSalvar);
        add(panelButton, BorderLayout.SOUTH);

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

        // Cargo fixo "Vistoriador"
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setMatricula(matricula);
        funcionario.setEmail(email);
        funcionario.setSenha(senha);
        funcionario.setCargo("Vistoriador");

        funcionarioController.adicionarFuncionario(funcionario);

        JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!",
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        // limpa os campos
        txtNome.setText("");
        txtMatricula.setText("");
        txtEmail.setText("");
        txtSenha.setText("");
    }
}
