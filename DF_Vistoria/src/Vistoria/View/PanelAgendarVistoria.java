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
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
    
    // Referência para o Dashboard
    private final Dashboard dashboardPanel;

    // Construtor agora recebe a referência do Dashboard
    public PanelAgendarVistoria(Cliente cliente, Dashboard dashboard) {
        this.clienteLogado = cliente;
        this.agendamentoController = new AgendamentoController();
        this.veiculoController = new VeiculoController();
        this.dashboardPanel = dashboard; // Armazena a referência

        setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Agendar Nova Vistoria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(40, 55, 95));

        JLabel lblData = new JLabel("Data da Vistoria:");
        dateChooser = new JDateChooser();

        JLabel lblHora = new JLabel("Horário:");
        cmbHora = new JComboBox<>(new String[]{
                "08:00", "09:00", "10:00", "11:00",
                "14:00", "15:00", "16:00", "17:00"
        });

        JLabel lblTipo = new JLabel("Tipo de Vistoria:");
        cmbTipoVistoria = new JComboBox<>(new String[]{
                "Vistoria de Transferência",
                "Vistoria Cautelar",
                "Vistoria Prévia"
        });

        JLabel lblVeiculo = new JLabel("Selecione o Veículo:");
        cmbVeiculo = new JComboBox<>();

        btnAgendar = new JButton("Confirmar Agendamento");
        btnAgendar.setBackground(new Color(0, 128, 255));
        btnAgendar.setForeground(Color.WHITE);
        btnAgendar.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // ---- GroupLayout ----
        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.CENTER)
        		.addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, 938, Short.MAX_VALUE)
        		.addComponent(btnAgendar, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(80)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(lblHora, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cmbHora, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)))
        			.addGap(50)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblVeiculo, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblTipo, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(cmbVeiculo, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cmbTipoVistoria, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
        			.addGap(80))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(20)
        			.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
        			.addGap(62)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        				.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        					.addComponent(lblTipo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        					.addComponent(cmbTipoVistoria, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(90)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cmbHora, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblVeiculo, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblHora, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
        					.addGap(55)
        					.addComponent(btnAgendar, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addGap(83)
        					.addComponent(cmbVeiculo, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        setLayout(layout);

        carregarVeiculos();
        btnAgendar.addActionListener(e -> agendarVistoria());
    }

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
        
        // Chamada para atualizar o dashboard
        if (dashboardPanel != null) {
            dashboardPanel.refreshDados();
        }
    }
}