package Vistoria.View;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtUsuario;
    private JPasswordField txtSenha;

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

    public Login() {
        setType(Type.UTILITY);
        setTitle("Login - Sistema Vistoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // >>> tamanho fixo
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Painel lateral colorido
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(187, 208, 235));
        panelLeft.setPreferredSize(new Dimension(300, 200));
        panelLeft.setLayout(new GridBagLayout());
        
        // Cria o ImageIcon original a partir do recurso
        ImageIcon originalIcon = new ImageIcon(Login.class.getResource("/imagens/logo.png"));

        // Obtém a imagem do ícone original
        Image originalImage = originalIcon.getImage();

        // Redimensiona a imagem para o novo tamanho (ex: 200x150)
        // O Image.SCALE_SMOOTH melhora a qualidade da imagem redimensionada
        Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);

        // Cria um novo ImageIcon a partir da imagem redimensionada
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Cria o JLabel e define o novo ícone redimensionado
        JLabel logo = new JLabel("");
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setIcon(scaledIcon);

        // O restante do código para adicionar o JLabel ao painel continua o mesmo
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 0;
        panelLeft.add(logo, gbc_lblNewLabel_1);
        
                JLabel lblLogo = new JLabel("Sistema de Vistoria Veicular");
                lblLogo.setFont(new Font("Arial", Font.BOLD, 16));
                lblLogo.setForeground(Color.DARK_GRAY);
                GridBagConstraints gbc_lblLogo = new GridBagConstraints();
                gbc_lblLogo.insets = new Insets(0, 0, 5, 0);
                gbc_lblLogo.gridx = 0;
                gbc_lblLogo.gridy = 1;
                panelLeft.add(lblLogo, gbc_lblLogo);

        contentPane.add(panelLeft, BorderLayout.WEST);
        
                JLabel lblNewLabel = new JLabel("V1.0");
                lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
                lblNewLabel.setForeground(Color.WHITE);
                GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
                gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
                gbc_lblNewLabel.gridx = 0;
                gbc_lblNewLabel.gridy = 2;
                panelLeft.add(lblNewLabel, gbc_lblNewLabel);

        // Painel de login
        JPanel panelRight = new JPanel();
        panelRight.setBackground(Color.WHITE);
        panelRight.setBorder(new EmptyBorder(30, 30, 30, 30));
        contentPane.add(panelRight, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel("Acesso ao Sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JLabel lblUsuario = new JLabel("CPF/Matrícula:");
        txtUsuario = new JTextField(15);

        JLabel lblSenha = new JLabel("Senha:");
        txtSenha = new JPasswordField(15);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(new Color(140, 204, 251));
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);

        // >>> Enter ativa o botão
        getRootPane().setDefaultButton(btnLogin);

        // Layout centralizado no painel direito
        GroupLayout gl = new GroupLayout(panelRight);
        gl.setHorizontalGroup(
        	gl.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl.createSequentialGroup()
        			.addGroup(gl.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl.createSequentialGroup()
        					.addGap(120)
        					.addGroup(gl.createParallelGroup(Alignment.TRAILING)
        						.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblUsuario, Alignment.LEADING)
        						.addComponent(txtSenha, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblSenha, Alignment.LEADING)))
        				.addGroup(gl.createSequentialGroup()
        					.addGap(160)
        					.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl.createSequentialGroup()
        					.addGap(145)
        					.addComponent(lblTitulo)))
        			.addContainerGap(134, Short.MAX_VALUE))
        );
        gl.setVerticalGroup(
        	gl.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl.createSequentialGroup()
        			.addGap(149)
        			.addComponent(lblTitulo)
        			.addGap(18)
        			.addComponent(lblUsuario)
        			.addGap(1)
        			.addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(lblSenha)
        			.addGap(1)
        			.addComponent(txtSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
        			.addGap(34)
        			.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(175, Short.MAX_VALUE))
        );
        panelRight.setLayout(gl);
    }
}
