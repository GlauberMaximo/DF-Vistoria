package Vistoria.View;

import Vistoria.Controller.VeiculoController;
import Vistoria.Model.Cliente;
import Vistoria.Model.Veiculo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.Year;

public class PanelCadastrarVeiculo extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtPlaca, txtTipo, txtNome, txtModelo, txtChassi;
    private JFormattedTextField txtAno;
    private JTextArea txtObservacoes;
    private JButton btnSalvar;
    private final VeiculoController veiculoController;
    private final Cliente clienteLogado;
    
    // Novas referências para os painéis que precisam ser atualizados
    private final Dashboard dashboardPanel;
    private final PanelAgendarVistoria agendamentoPanel;

    // Construtor agora recebe os painéis como parâmetros
    public PanelCadastrarVeiculo(Cliente cliente, Dashboard dashboard, PanelAgendarVistoria agendamento) {
        this.clienteLogado = cliente;
        this.veiculoController = new VeiculoController();
        this.dashboardPanel = dashboard;
        this.agendamentoPanel = agendamento;

        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(30, 60, 30, 60));
        setLayout(new BorderLayout(0, 20));

        // Título principal
        JLabel lblTitulo = new JLabel("Cadastrar Novo Veículo", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(33, 37, 41));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel com borda e título para dados do veículo
        JPanel panelDadosVeiculo = new JPanel();
        panelDadosVeiculo.setBackground(Color.WHITE);
        panelDadosVeiculo.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                "Dados do Veículo",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 16),
                new Color(70, 70, 70)
        ));

        GroupLayout layout = new GroupLayout(panelDadosVeiculo);
        panelDadosVeiculo.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Labels
        JLabel lblPlaca = new JLabel("Placa:");
        JLabel lblTipo = new JLabel("Tipo de Veículo:");
        JLabel lblNome = new JLabel("Nome do Veículo:");
        JLabel lblModelo = new JLabel("Modelo:");
        JLabel lblAno = new JLabel("Ano:");
        JLabel lblChassi = new JLabel("Chassi:");
        JLabel lblObservacoes = new JLabel("Observações:");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color labelColor = new Color(80, 80, 80);
        for (JLabel lbl : new JLabel[]{lblPlaca, lblTipo, lblNome, lblModelo, lblAno, lblChassi, lblObservacoes}) {
            lbl.setFont(labelFont);
            lbl.setForeground(labelColor);
        }

        // Campos
        txtPlaca = new JTextField();
        txtPlaca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPlaca.setColumns(10);
        txtPlaca.setToolTipText("Ex: ABC-1234 (padrão antigo) ou ABC1D23 (padrão Mercosul)");

        txtTipo = new JTextField();
        txtTipo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTipo.setColumns(15);

        txtNome = new JTextField();
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtNome.setColumns(15);

        txtModelo = new JTextField();
        txtModelo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtModelo.setColumns(15);

        try {
            MaskFormatter anoMask = new MaskFormatter("####");
            anoMask.setPlaceholderCharacter('_');
            txtAno = new JFormattedTextField(anoMask);
        } catch (ParseException e) {
            txtAno = new JFormattedTextField();
        }
        txtAno.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtAno.setColumns(6);

        txtChassi = new JTextField();
        txtChassi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChassi.setColumns(17);

        txtObservacoes = new JTextArea(4, 30);
        txtObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        scrollObs.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        // Layout horizontal e vertical
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblPlaca)
                    .addComponent(lblTipo)
                    .addComponent(lblNome)
                    .addComponent(lblModelo)
                    .addComponent(lblAno)
                    .addComponent(lblChassi)
                    .addComponent(lblObservacoes))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtPlaca)
                    .addComponent(txtTipo)
                    .addComponent(txtNome)
                    .addComponent(txtModelo)
                    .addComponent(txtAno)
                    .addComponent(txtChassi)
                    .addComponent(scrollObs))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPlaca)
                    .addComponent(txtPlaca))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipo)
                    .addComponent(txtTipo))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblModelo)
                    .addComponent(txtModelo))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAno)
                    .addComponent(txtAno))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChassi)
                    .addComponent(txtChassi))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblObservacoes)
                    .addComponent(scrollObs))
        );

        add(panelDadosVeiculo, BorderLayout.CENTER);

        // Botão salvar com estilo moderno
        btnSalvar = new JButton("Salvar Veículo");
        btnSalvar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setBackground(new Color(0, 123, 255));
        btnSalvar.setFocusPainted(false);
        btnSalvar.setPreferredSize(new Dimension(200, 45));
        btnSalvar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel panelButton = new JPanel();
        panelButton.setBackground(Color.WHITE);
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

            if (placa.isEmpty() || tipo.isEmpty() || nome.isEmpty() || modelo.isEmpty() || anoStr.contains("_") || anoStr.isEmpty() || chassi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios e devem estar completos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            placa = placa.replace("-", "");
            if (!placa.matches("[A-Z]{3}\\d[A-Z]\\d{2}|[A-Z]{3}\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Placa inválida. Use o padrão 'ABC1D23' (Mercosul) ou 'ABC1234'.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (placa.matches("[A-Z]{3}\\d{4}")) {
                placa = placa.substring(0, 3) + "-" + placa.substring(3);
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
            if (ano < 1900 || ano > Year.now().getValue() + 1) {
                JOptionPane.showMessageDialog(this, "Ano inválido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Veiculo novoVeiculo = new Veiculo(
                placa, tipo, nome, modelo,
                Year.of(ano), chassi, obs, clienteLogado.getIdCliente()
            );

            veiculoController.adicionarVeiculo(novoVeiculo);

            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso!");
            limparCampos();

            if (agendamentoPanel != null) {
                agendamentoPanel.refreshVeiculosList();
            }
            if (dashboardPanel != null) {
                dashboardPanel.refreshDados();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O ano deve ser um número válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar veículo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtPlaca.setText("");
        txtTipo.setText("");
        txtNome.setText("");
        txtModelo.setText("");
        txtAno.setValue(null);
        txtChassi.setText("");
        txtObservacoes.setText("");
    }
}