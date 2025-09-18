package Vistoria.View;

import Vistoria.Controller.*;
import Vistoria.Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelLaudo extends JPanel {

    private static final long serialVersionUID = 1L;

    private Cliente clienteLogado;
    private LaudoController laudoController;
    private AgendamentoController agendamentoController;
    private VistoriaController vistoriaController;
    private ClienteController clienteController;
    private VeiculoController veiculoController;
    private PagamentoController pagamentoController;
    private FuncionarioController funcionarioController; // Adicione este Controller

    private JComboBox<String> comboVistorias;
    private JPanel painelDetalhesLaudo;
    private JButton btnImprimir;

    // Mapa para associar o texto do JComboBox com o ID da vistoria
    private Map<String, Integer> mapVistoriaId = new HashMap<>();

    public PanelLaudo(Cliente cliente) {
        this.clienteLogado = cliente;
        
        // Inicializa todos os Controllers
        this.laudoController = new LaudoController();
        this.agendamentoController = new AgendamentoController();
        this.vistoriaController = new VistoriaController();
        this.clienteController = new ClienteController();
        this.veiculoController = new VeiculoController();
        this.pagamentoController = new PagamentoController();
        this.funcionarioController = new FuncionarioController();

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // --- Painel Superior com a caixa de seleção
        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelSuperior.setBackground(new Color(245, 245, 245));
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Selecione o Laudo"));
        
        comboVistorias = new JComboBox<>();
        comboVistorias.setPreferredSize(new Dimension(400, 30));
        comboVistorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboVistorias.addActionListener(e -> exibirDetalhesLaudo());
        
        painelSuperior.add(new JLabel("Laudo da Vistoria: "));
        painelSuperior.add(comboVistorias);
        
        add(painelSuperior, BorderLayout.NORTH);

        // --- Painel Central para exibir os detalhes do laudo
        painelDetalhesLaudo = new JPanel();
        painelDetalhesLaudo.setBackground(Color.WHITE);
        painelDetalhesLaudo.setLayout(new GridBagLayout());
        painelDetalhesLaudo.setBorder(BorderFactory.createTitledBorder("Detalhes do Laudo"));

        add(new JScrollPane(painelDetalhesLaudo), BorderLayout.CENTER);

        // --- Painel Inferior com os botões
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelInferior.setBackground(Color.WHITE);
        
        btnImprimir = new JButton("Imprimir Laudo");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(e -> imprimirLaudo());
        
        JButton btnBaixarPDF = new JButton("Baixar PDF");
        btnBaixarPDF.setEnabled(false);
        btnBaixarPDF.addActionListener(e -> baixarPDF());

        painelInferior.add(btnImprimir);
        painelInferior.add(btnBaixarPDF);

        add(painelInferior, BorderLayout.SOUTH);

        carregarListaLaudos();
    }

    // Método para carregar a lista de laudos do cliente no JComboBox
    private void carregarListaLaudos() {
        comboVistorias.removeAllItems();
        mapVistoriaId.clear();
        
        List<Laudo> laudos = laudoController.listarLaudosDoCliente(clienteLogado.getIdCliente());
        
        if (laudos.isEmpty()) {
            comboVistorias.addItem("Nenhum laudo disponível");
            btnImprimir.setEnabled(false);
        } else {
            for (Laudo laudo : laudos) {
                // Busca o agendamento para pegar a data
                Agendamento agendamento = agendamentoController.buscarPorId(laudo.getIdVistoria());
                if (agendamento != null) {
                    String texto = "Laudo de " + agendamento.getData_agendamento() + " - " + laudo.getIdVistoria();
                    comboVistorias.addItem(texto);
                    mapVistoriaId.put(texto, laudo.getIdVistoria());
                }
            }
            comboVistorias.setSelectedIndex(0);
            btnImprimir.setEnabled(true);
        }
    }

    // Método para exibir os detalhes do laudo selecionado
    private void exibirDetalhesLaudo() {
        String itemSelecionado = (String) comboVistorias.getSelectedItem();
        
        painelDetalhesLaudo.removeAll();
        
        if (itemSelecionado == null || itemSelecionado.equals("Nenhum laudo disponível")) {
            painelDetalhesLaudo.revalidate();
            painelDetalhesLaudo.repaint();
            return;
        }
        
        int idVistoria = mapVistoriaId.get(itemSelecionado);
        
        // Buscando todos os dados relacionados
        Vistoria vistoria = vistoriaController.buscarVistoria(idVistoria);
        Agendamento agendamento = agendamentoController.buscarPorId(vistoria.getIdAgendamento());
        Veiculo veiculo = veiculoController.buscarPorId(agendamento.getIdVeiculo());
        Pagamento pagamento = pagamentoController.buscarPagamento(vistoria.getIdVistoria());
        Funcionario vistoriador = funcionarioController.buscarPorId(vistoria.getIdFuncionario());

        // Configuração do layout para os detalhes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Título
        JLabel lblTitulo = new JLabel("Relatório de Vistoria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelDetalhesLaudo.add(lblTitulo, gbc);
        
        // Linha de separação
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        painelDetalhesLaudo.add(separator, gbc);

        // Dados do Cliente e Veículo
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.gridwidth = 1;
        
        // Linha 2
        gbc.gridx = 0; gbc.gridy = 2; painelDetalhesLaudo.add(new JLabel("Data da Vistoria:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; painelDetalhesLaudo.add(new JLabel(vistoria.getDataVistoria().toString()), gbc);

        // Linha 3
        gbc.gridx = 0; gbc.gridy = 3; painelDetalhesLaudo.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; painelDetalhesLaudo.add(new JLabel(clienteLogado.getNome()), gbc);
        
        // Linha 4
        gbc.gridx = 0; gbc.gridy = 4; painelDetalhesLaudo.add(new JLabel("Veículo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; painelDetalhesLaudo.add(new JLabel(veiculo.getNome_veiculo() + " " + veiculo.getModelo()), gbc);
        
        // Linha 5
        gbc.gridx = 0; gbc.gridy = 5; painelDetalhesLaudo.add(new JLabel("Placa:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; painelDetalhesLaudo.add(new JLabel(veiculo.getPlaca()), gbc);

        // Linha 6
        gbc.gridx = 0; gbc.gridy = 6; painelDetalhesLaudo.add(new JLabel("Chassi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; painelDetalhesLaudo.add(new JLabel(veiculo.getChassi()), gbc);
        
        // Linha 7
        gbc.gridx = 0; gbc.gridy = 7; painelDetalhesLaudo.add(new JLabel("Vistoriador:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; painelDetalhesLaudo.add(new JLabel(vistoriador != null ? vistoriador.getNome() : "Não informado"), gbc);
        
        // Linha 8
        gbc.gridx = 0; gbc.gridy = 8; painelDetalhesLaudo.add(new JLabel("Resultado:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; painelDetalhesLaudo.add(new JLabel(vistoria.getResultado().toString()), gbc);

        // Dados do Pagamento
        gbc.gridy = 9; gbc.gridwidth = 2; painelDetalhesLaudo.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        gbc.gridy = 10; gbc.gridwidth = 1; gbc.gridx = 0; painelDetalhesLaudo.add(new JLabel("Valor do Pagamento:"), gbc);
        gbc.gridx = 1; painelDetalhesLaudo.add(new JLabel(pagamento != null ? "R$ " + pagamento.getValor().toString() : "Pendente"), gbc);

        gbc.gridy = 11; gbc.gridx = 0; painelDetalhesLaudo.add(new JLabel("Forma de Pagamento:"), gbc);
        gbc.gridx = 1; painelDetalhesLaudo.add(new JLabel(pagamento != null ? pagamento.getFormaPagamento().toString() : "Pendente"), gbc);
        
        // Observações
        gbc.gridy = 12; gbc.gridwidth = 2; painelDetalhesLaudo.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        JLabel lblObservacoes = new JLabel("Observações:");
        lblObservacoes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 13;
        painelDetalhesLaudo.add(lblObservacoes, gbc);
        
        JTextArea txtObservacoes = new JTextArea(vistoria.getObservacoes());
        txtObservacoes.setEditable(false);
        txtObservacoes.setLineWrap(true);
        txtObservacoes.setWrapStyleWord(true);
        txtObservacoes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollObs = new JScrollPane(txtObservacoes);
        scrollObs.setPreferredSize(new Dimension(400, 100));
        gbc.gridy = 14;
        painelDetalhesLaudo.add(scrollObs, gbc);
        
        painelDetalhesLaudo.revalidate();
        painelDetalhesLaudo.repaint();
    }

    private void imprimirLaudo() {
        // Implemente a lógica de impressão aqui
        JOptionPane.showMessageDialog(this, "Funcionalidade de impressão em desenvolvimento.");
    }
    
    private void baixarPDF() {
        // Implemente a lógica de download em PDF aqui
        JOptionPane.showMessageDialog(this, "Funcionalidade de download em PDF em desenvolvimento.");
    }

    // Método para ser chamado quando o painel precisar ser atualizado
    public void atualizarLaudos() {
        carregarListaLaudos();
    }
}