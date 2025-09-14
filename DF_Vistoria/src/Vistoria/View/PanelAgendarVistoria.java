package Vistoria.View;

import Vistoria.Controller.AgendamentoController;
import Vistoria.Controller.VeiculoController;
import Vistoria.Model.Agendamento;
import Vistoria.Model.Cliente;
import Vistoria.Model.Veiculo;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PanelAgendarVistoria extends JPanel {
    private static final long serialVersionUID = 1L;

    private JDateChooser dateChooser;
    private JComboBox<String> cmbHora;
    private JComboBox<String> cmbTipoVistoria;
    private JComboBox<String> cmbVeiculo;
    private JButton btnAgendar;

    private AgendamentoController agendamentoController;
    private VeiculoController veiculoController;
    private Cliente clienteLogado;

    public PanelAgendarVistoria(Cliente cliente) {
        this.clienteLogado = cliente;
        this.agendamentoController = new AgendamentoController();
        this.veiculoController = new VeiculoController();

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        setBackground(new Color(245, 248, 255));

        // Cabeçalho
        JLabel lblTitulo = new JLabel("Agendar Nova Vistoria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(40, 55, 95));
        add(lblTitulo, BorderLayout.NORTH);

        // Painel central com GridBagLayout
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Campo Data
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Data da Vistoria:"), gbc);
        gbc.gridx = 1;
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(220, 35));
        panelForm.add(dateChooser, gbc);

        // Campo Hora
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Horário:"), gbc);
        gbc.gridx = 1;
        cmbHora = new JComboBox<>(new String[]{"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00"});
        cmbHora.setPreferredSize(new Dimension(220, 35));
        panelForm.add(cmbHora, gbc);

        // Campo Tipo de Vistoria
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Tipo de Vistoria:"), gbc);
        gbc.gridx = 1;
        cmbTipoVistoria = new JComboBox<>(new String[]{
                "Vistoria de Transferência", 
                "Vistoria Cautelar", 
                "Vistoria Prévia"
        });
        cmbTipoVistoria.setPreferredSize(new Dimension(220, 35));
        panelForm.add(cmbTipoVistoria, gbc);

        // Campo Veículo
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(new JLabel("Selecione o Veículo:"), gbc);
        gbc.gridx = 1;
        cmbVeiculo = new JComboBox<>();
        cmbVeiculo.setPreferredSize(new Dimension(220, 35));
        panelForm.add(cmbVeiculo, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Botão de confirmar
        btnAgendar = new JButton("Confirmar Agendamento");
        btnAgendar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAgendar.setBackground(new Color(52, 152, 219));
        btnAgendar.setForeground(Color.WHITE);
        btnAgendar.setFocusPainted(false);
        btnAgendar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgendar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel panelButton = new JPanel();
        panelButton.setOpaque(false);
        panelButton.add(btnAgendar);
        add(panelButton, BorderLayout.SOUTH);

        carregarVeiculos();

        btnAgendar.addActionListener(e -> agendarVistoria());
    }

    // Atualiza lista de veículos
    public void refreshVeiculosList() {
        cmbVeiculo.removeAllItems();
        carregarVeiculos();
    }

    private void carregarVeiculos() {
        List<Veiculo> veiculos = veiculoController.buscarVeiculosPorCliente(clienteLogado.getIdCliente());
        if (veiculos.isEmpty()) {
            cmbVeiculo.addItem("Nenhum veículo cadastrado");
            btnAgendar.setEnabled(false);
        } else {
            for (Veiculo v : veiculos) {
                cmbVeiculo.addItem(v.getPlaca() + " - " + v.getNome_veiculo());
            }
            btnAgendar.setEnabled(true);
        }
    }

    private void agendarVistoria() {
        Date selectedDate = dateChooser.getDate();
        String selectedTimeStr = (String) cmbHora.getSelectedItem();
        String selectedTipo = (String) cmbTipoVistoria.getSelectedItem();

        if (selectedDate == null || selectedTimeStr == null || cmbVeiculo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cmbVeiculo.getSelectedItem().toString().equals("Nenhum veículo cadastrado")) {
            JOptionPane.showMessageDialog(this, "Cadastre um veículo antes de agendar uma vistoria.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Veiculo> veiculos = veiculoController.buscarVeiculosPorCliente(clienteLogado.getIdCliente());
        Veiculo veiculoSelecionado = veiculos.get(cmbVeiculo.getSelectedIndex());

        LocalDate dataAgendamento = Instant.ofEpochMilli(selectedDate.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalTime horaAgendamento = LocalTime.parse(selectedTimeStr);

        Agendamento novoAgendamento = new Agendamento(
                dataAgendamento, horaAgendamento, selectedTipo,
                clienteLogado.getIdCliente(), veiculoSelecionado.getIdVeiculo()
        );

        agendamentoController.adicionarAgendamento(novoAgendamento);
        JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!");
    }
}
