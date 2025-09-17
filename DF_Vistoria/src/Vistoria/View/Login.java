package Vistoria.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Vistoria.Controller.ClienteController;
import Vistoria.Controller.FuncionarioController;
import Vistoria.Model.Cliente;
import Vistoria.Model.Funcionario;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtSenha;
	private ClienteController clienteController;
	private FuncionarioController funcionarioController;

	public Login() {
		setTitle("Login - Sistema Vistoria");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		clienteController = new ClienteController();
		funcionarioController = new FuncionarioController();

		contentPane = new JPanel(new BorderLayout());
		setContentPane(contentPane);

		// Painel lateral esquerdo
		JPanel panelLeft = new JPanel();
		panelLeft.setBackground(new Color(187, 208, 235));
		panelLeft.setPreferredSize(new Dimension(300, 200));
		panelLeft.setLayout(new GridBagLayout());

		// Logo
		ImageIcon originalIcon = new ImageIcon(Login.class.getResource("/imagens/logo.png"));
		Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		JLabel logo = new JLabel(new ImageIcon(scaledImage));
		GridBagConstraints gbcLogo = new GridBagConstraints();
		gbcLogo.gridx = 0;
		gbcLogo.gridy = 0;
		gbcLogo.insets = new Insets(0, 0, 5, 0);
		panelLeft.add(logo, gbcLogo);

		JLabel lblSistema = new JLabel("Sistema de Vistoria Veicular");
		lblSistema.setFont(new Font("Arial", Font.BOLD, 16));
		lblSistema.setForeground(Color.DARK_GRAY);
		GridBagConstraints gbcTitulo = new GridBagConstraints();
		gbcTitulo.gridx = 0;
		gbcTitulo.gridy = 1;
		gbcTitulo.insets = new Insets(0, 0, 5, 0);
		panelLeft.add(lblSistema, gbcTitulo);

		JLabel lblVersao = new JLabel("V1.0");
		lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblVersao.setForeground(Color.WHITE);
		GridBagConstraints gbcVersao = new GridBagConstraints();
		gbcVersao.gridx = 0;
		gbcVersao.gridy = 2;
		panelLeft.add(lblVersao, gbcVersao);

		contentPane.add(panelLeft, BorderLayout.WEST);

		// Painel de login
		JPanel panelRight = new JPanel();
		panelRight.setBackground(Color.WHITE);
		panelRight.setBorder(new EmptyBorder(30, 30, 30, 30));
		contentPane.add(panelRight, BorderLayout.CENTER);

		JLabel Titulo = new JLabel("Acesso ao Sistema");
		Titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

		JLabel lblUsuario = new JLabel("CPF/Matrícula:");
		txtUsuario = new JTextField(15);

		JLabel lblSenha = new JLabel("Senha:");
		txtSenha = new JPasswordField(15);

		JButton btnLogin = new JButton("Entrar");
		btnLogin.setBackground(new Color(140, 204, 251));
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnLogin.setFocusPainted(false);
		getRootPane().setDefaultButton(btnLogin);

		// "Cadastrar" como link clicável
		JLabel lblCadastro = new JLabel("<HTML><U>Cadastrar</U></HTML>");
		lblCadastro.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCadastro.setForeground(Color.BLUE);
		lblCadastro.setCursor(new Cursor(Cursor.HAND_CURSOR));

		lblCadastro.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				dispose();
				new CadastroCliente().setVisible(true);
			}
		});

		// Evento do botão Login
		btnLogin.addActionListener(e -> autenticar());

		// Layout com GroupLayout
		GroupLayout gl = new GroupLayout(panelRight);
		gl.setHorizontalGroup(
				gl.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl.createSequentialGroup().addGap(120)
										.addGroup(gl.createParallelGroup(Alignment.TRAILING)
												.addComponent(txtSenha, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
														200, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblSenha, Alignment.LEADING)
												.addComponent(txtUsuario, Alignment.LEADING, GroupLayout.PREFERRED_SIZE,
														200, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblUsuario, Alignment.LEADING)))
						.addGroup(gl.createSequentialGroup().addGap(156).addComponent(btnLogin,
								GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING,
								gl.createSequentialGroup().addContainerGap(396, Short.MAX_VALUE)
										.addComponent(lblCadastro).addContainerGap())
						.addGroup(gl.createSequentialGroup().addGap(138).addComponent(Titulo).addContainerGap(160,
								Short.MAX_VALUE)));
		gl.setVerticalGroup(gl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl.createSequentialGroup().addGap(137).addComponent(Titulo).addGap(18)
						.addComponent(lblUsuario).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblSenha)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE).addGap(18)
						.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 181, Short.MAX_VALUE).addComponent(lblCadastro)
						.addContainerGap()));
		panelRight.setLayout(gl);
	}

	private void autenticar() {
		String usuario = txtUsuario.getText().trim();
		String senha = new String(txtSenha.getPassword());

		if (usuario.isEmpty() || senha.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// 1. Autenticar como Cliente (via CPF)
		Cliente cliente = clienteController.buscarClientePorCpf(usuario);
		if (cliente != null && cliente.getSenha().equals(senha)) {
			JOptionPane.showMessageDialog(this, "Login de cliente realizado com sucesso!");
			dispose();
			new DashboardCliente(cliente).setVisible(true);
			return;
		}

		// 2. Autenticar como Funcionário (via Matrícula)
		Funcionario funcionario = funcionarioController.autenticar(usuario, senha);
		if (funcionario != null) {
			JOptionPane.showMessageDialog(this, "Login de funcionário realizado com sucesso!");
			dispose();

			String cargo = funcionario.getCargo();
			if (cargo != null) {
				switch (cargo) {
				case "Vistoriador":
					new DashboardVistoria(funcionario).setVisible(true);
					break;
				case "Gerente":
					new DashboardGerente(funcionario).setVisible(true);
					break;
				default:
					JOptionPane.showMessageDialog(this, "Cargo de funcionário não reconhecido.", "Erro",
							JOptionPane.ERROR_MESSAGE);
					break;
				}
			}
			return;
		}

		// 3. Nenhuma autenticação válida
		JOptionPane.showMessageDialog(this, "CPF/Matrícula ou senha inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Login frame = new Login();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
