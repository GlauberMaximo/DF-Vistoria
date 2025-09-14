package Vistoria.View;

import Vistoria.Controller.VeiculoController;
import Vistoria.Model.Cliente;
import Vistoria.Model.Veiculo;

import javax.swing.*;
import java.awt.*;
import java.time.Year;

public class PanelCadastrarVeiculo extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtPlaca, txtTipo, txtNome, txtModelo, txtAno, txtChassi;
    private JTextArea txtObservacoes;
    private JButton btnSalvar;
    private VeiculoController veiculoController;
    private Cliente clienteLogado;

    public PanelCadastrarVeiculo(Cliente cliente) {
        this.clienteLogado = cliente;
        this.veiculoController = new VeiculoController();

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        setBackground(new Color(245, 248, 255));

        // Cabeçalho
        JLabel lblTitulo = new JLabel("Cadastrar Novo Veículo");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(40, 55, 95));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel do formulário
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Placa
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Placa:"), gbc);
        gbc.gridx = 1;
        txtPlaca = new JTextField(20);
        panelForm.add(txtPlaca, gbc);

        // Tipo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Tipo de Veículo:"), gbc);
        gbc.gridx = 1;
        txtTipo = new JTextField(20);
        panelForm.add(txtTipo, gbc);

        // Nome
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Nome do Veículo:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        panelForm.add(txtNome, gbc);

        // Modelo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1;
        txtModelo = new JTextField(20);
        panelForm.add(txtModelo, gbc);

        // Ano
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        txtAno = new JTextField(20);
        panelForm.add(txtAno, gbc);

        // Chassi
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Chassi:"), gbc);
        gbc.gridx = 1;
        txtChassi = new JTextField(20);
        panelForm.add(txtChassi, gbc);

        // Observações
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1;
        txtObservacoes = new JTextArea(5, 20);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        scrollObs.setPreferredSize(new Dimension(250, 100));
        panelForm.add(scrollObs, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botão salvar
        btnSalvar = new JButton("Salvar Veículo");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSalvar.setBackground(new Color(52, 152, 219));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel panelButton = new JPanel();
        panelButton.setOpaque(false);
        panelButton.add(btnSalvar);
        add(panelButton, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvarVeiculo());
    }

    private void salvarVeiculo() {
        try {
            String placa = txtPlaca.getText().trim().toUpperCase();
            String tipo = txtTipo.getText().trim();
            String nome = txtNome.getText().trim();
            String modelo = txtModelo.getText().trim();
            String anoStr = txtAno.getText().trim();
            String chassi = txtChassi.getText().trim();
            String obs = txtObservacoes.getText().trim();

            if (placa.isEmpty() || tipo.isEmpty() || nome.isEmpty() || modelo.isEmpty() || anoStr.isEmpty() || chassi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!placa.matches("[A-Z]{3}-?\\d[A-Z]\\d{2}|[A-Z]{3}-?\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Placa inválida. Use 'ABC-1234' ou 'ABC-1D23'.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (chassi.length() != 17) {
                JOptionPane.showMessageDialog(this, "O chassi deve ter 17 caracteres.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (veiculoController.buscarPorPlaca(placa) != null) {
                JOptionPane.showMessageDialog(this, "Já existe um veículo com esta placa.", "Erro de Duplicidade", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (veiculoController.buscarPorChassi(chassi) != null) {
                JOptionPane.showMessageDialog(this, "Já existe um veículo com este chassi.", "Erro de Duplicidade", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ano = Integer.parseInt(anoStr);

            Veiculo novoVeiculo = new Veiculo(
                    placa, tipo, nome, modelo,
                    Year.of(ano), chassi, obs, clienteLogado.getIdCliente()
            );

            veiculoController.adicionarVeiculo(novoVeiculo);

            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
            limparCampos();

            Container parent = this.getParent();
            while (parent != null && !(parent instanceof DashboardCliente)) {
                parent = parent.getParent();
            }
            if (parent instanceof DashboardCliente) {
                DashboardCliente dashboard = (DashboardCliente) parent;
                dashboard.getPanelAgendar().refreshVeiculosList();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O ano deve ser um número válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar veículo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtPlaca.setText("");
        txtTipo.setText("");
        txtNome.setText("");
        txtModelo.setText("");
        txtAno.setText("");
        txtChassi.setText("");
        txtObservacoes.setText("");
    }
}
