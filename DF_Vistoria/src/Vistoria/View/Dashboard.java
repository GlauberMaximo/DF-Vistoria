package Vistoria.View;

import Vistoria.Controller.AgendamentoController;
import Vistoria.Controller.LaudoController;
import Vistoria.Controller.VistoriaController;
import Vistoria.Controller.VeiculoController;
import Vistoria.Model.Agendamento;
import Vistoria.Model.Cliente;
import Vistoria.Model.Laudo;
import Vistoria.Model.Veiculo;
import Vistoria.Model.Vistoria;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class Dashboard extends JPanel {

    private static final long serialVersionUID = 1L;

    private VeiculoController veiculoController;
    private AgendamentoController agendamentoController;
    private LaudoController laudoController; // NOVO: Adicionando o LaudoController
    private VistoriaController vistoriaController; // NOVO: Adicionando o VistoriaController
    private Cliente clienteLogado;

    private DefaultListModel<String> veiculosModel;
    private DefaultListModel<String> agendamentosModel;
    private DefaultListModel<String> laudosModel; // NOVO: Modelo para a lista de laudos

    public Dashboard(Cliente cliente) {
        this.clienteLogado = cliente;

        veiculoController = new VeiculoController();
        agendamentoController = new AgendamentoController();
        laudoController = new LaudoController(); // NOVO: Inicializando o LaudoController
        vistoriaController = new VistoriaController(); // NOVO: Inicializando o VistoriaController

        setLayout(new GridLayout(1, 3, 20, 20));
        setBackground(Color.decode("#F5F6FA"));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Card Veículos (azul)
        JPanel cardVeiculos = criarCard("Veículos Cadastrados", "carro.png", new Color(41, 128, 185));
        veiculosModel = new DefaultListModel<>();
        JList<String> listaVeiculos = new JList<>(veiculosModel);
        listaVeiculos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaVeiculos.setSelectionBackground(Color.decode("#3498DB"));
        listaVeiculos.setSelectionForeground(Color.WHITE);
        cardVeiculos.add(new JScrollPane(listaVeiculos), BorderLayout.CENTER);
        add(cardVeiculos);

        // Card Agendamentos (verde)
        JPanel cardAgendamentos = criarCard("Agendamentos", "calendario.png", new Color(39, 174, 96));
        agendamentosModel = new DefaultListModel<>();
        JList<String> listaAgendamentos = new JList<>(agendamentosModel);
        listaAgendamentos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaAgendamentos.setSelectionBackground(Color.decode("#2ECC71"));
        listaAgendamentos.setSelectionForeground(Color.WHITE);
        cardAgendamentos.add(new JScrollPane(listaAgendamentos), BorderLayout.CENTER);
        add(cardAgendamentos);

        // NOVO: Card Laudos (roxo/laranja)
        JPanel cardLaudos = criarCard("Laudos", "emitir.png", new Color(155, 89, 182));
        laudosModel = new DefaultListModel<>();
        JList<String> listaLaudos = new JList<>(laudosModel);
        listaLaudos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaLaudos.setSelectionBackground(new Color(155, 89, 182).brighter());
        listaLaudos.setSelectionForeground(Color.WHITE);
        cardLaudos.add(new JScrollPane(listaLaudos), BorderLayout.CENTER);
        add(cardLaudos);

        carregarDados();
    }

    // Método público para recarregar todos os dados do dashboard
    public void refreshDados() {
        carregarDados();
    }

    private JPanel criarCard(String titulo, String iconPath, Color corTitulo) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);

        // Borda arredondada
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(223, 230, 233), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        header.setOpaque(false);

        JLabel lblIcon;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/icones/" + iconPath));
            Image scaledImage = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            lblIcon = new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            lblIcon = new JLabel("?");
        }

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(corTitulo);

        header.add(lblIcon);
        header.add(lblTitulo);
        card.add(header, BorderLayout.NORTH);

        return card;
    }

    private void carregarDados() {
        // Veículos
        List<Veiculo> veiculos = veiculoController.buscarVeiculosPorCliente(clienteLogado.getIdCliente());
        veiculosModel.clear();
        int count = 1;
        for (Veiculo v : veiculos) {
            veiculosModel.addElement(count++ + " - " + v.getPlaca() + " | " + v.getModelo() + " | " + v.getNome_veiculo());
        }
        if (veiculos.isEmpty()) {
            veiculosModel.addElement("Nenhum veículo cadastrado.");
        }

        // Agendamentos
        List<Agendamento> agendamentos = agendamentoController.buscarAgendamentosPorCliente(clienteLogado.getIdCliente());
        agendamentosModel.clear();
        count = 1;
        for (Agendamento a : agendamentos) {
            agendamentosModel.addElement(count++ + " - " + a.getData_agendamento() + " | " + a.getStatus_agendamento());
        }
        if (agendamentos.isEmpty()) {
            agendamentosModel.addElement("Nenhum agendamento encontrado.");
        }
        
        // NOVO: Laudos
        List<Laudo> laudos = laudoController.listarLaudosDoCliente(clienteLogado.getIdCliente());
        laudosModel.clear();
        count = 1;
        if (laudos.isEmpty()) {
            laudosModel.addElement("Nenhum laudo encontrado.");
        } else {
            for (Laudo l : laudos) {
                // Buscando a vistoria e agendamento para detalhes
                Vistoria vistoria = vistoriaController.buscarVistoria(l.getIdVistoria());
                if (vistoria != null) {
                    Agendamento agendamento = agendamentoController.buscarPorId(vistoria.getIdAgendamento());
                    if (agendamento != null) {
                        laudosModel.addElement(count++ + " - Vistoria: " + agendamento.getTipo_vistoria() + " (" + vistoria.getDataVistoria() + ")");
                    }
                }
            }
        }
    }
}