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
    private FuncionarioController funcionarioController;

    private JComboBox<String> comboVistorias;
    private JPanel painelDetalhesLaudo;
    private JButton btnImprimir;

    private Map<String, Integer> mapVistoriaId = new HashMap<>();
    private boolean isUpdating = false; // Flag para controle

    public PanelLaudo(Cliente cliente) {
        this.clienteLogado = cliente;
        
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

        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelSuperior.setBackground(new Color(245, 245, 245));
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Selecione o Laudo"));
        
        comboVistorias = new JComboBox<>();
        comboVistorias.setPreferredSize(new Dimension(400, 30));
        comboVistorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboVistorias.addActionListener(e -> {
            if (!isUpdating) {
                exibirDetalhesLaudo();
            }
        });
        
        painelSuperior.add(new JLabel("Laudo da Vistoria: "));
        painelSuperior.add(comboVistorias);
        
        add(painelSuperior, BorderLayout.NORTH);

        painelDetalhesLaudo = new JPanel();
        painelDetalhesLaudo.setBackground(Color.WHITE);
        painelDetalhesLaudo.setLayout(new GridBagLayout());
        painelDetalhesLaudo.setBorder(BorderFactory.createTitledBorder("Detalhes do Laudo"));

        add(new JScrollPane(painelDetalhesLaudo), BorderLayout.CENTER);

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

    private void carregarListaLaudos() {
        isUpdating = true; // Inicia a atualização
        comboVistorias.removeAllItems();
        mapVistoriaId.clear();
        
        List<Laudo> laudos = laudoController.listarLaudosDoCliente(clienteLogado.getIdCliente());
        
        if (laudos.isEmpty()) {
            comboVistorias.addItem("Nenhum laudo disponível");
            btnImprimir.setEnabled(false);
        } else {
            for (Laudo laudo : laudos) {
                Agendamento agendamento = agendamentoController.buscarPorId(laudo.getIdVistoria());
                if (agendamento != null) {
                    String texto = "Laudo de " + agendamento.getData_agendamento() + " - " + laudo.getIdVistoria();
                    comboVistorias.addItem(texto);
                    mapVistoriaId.put(texto, laudo.getIdVistoria());
                }
            }
            if(comboVistorias.getItemCount() > 0){
                comboVistorias.setSelectedIndex(0);
                btnImprimir.setEnabled(true);
            } else {
                 comboVistorias.addItem("Nenhum laudo disponível");
                 btnImprimir.setEnabled(false);
            }
        }
        isUpdating = false; // Finaliza a atualização
    }

    private void exibirDetalhesLaudo() {
        String itemSelecionado = (String) comboVistorias.getSelectedItem();
        
        painelDetalhesLaudo.removeAll();
        
        if (itemSelecionado == null || itemSelecionado.equals("Nenhum laudo disponível")) {
            painelDetalhesLaudo.revalidate();
            painelDetalhesLaudo.repaint();
            return;
        }

        // CORREÇÃO: Verificação adicional para garantir que o item exista no mapa
        Integer idVistoria = mapVistoriaId.get(itemSelecionado);
        if (idVistoria == null) {
            System.err.println("ID da vistoria não encontrado no mapa para o item: " + itemSelecionado);
            painelDetalhesLaudo.revalidate();
            painelDetalhesLaudo.repaint();
            return;
        }

        Vistoria vistoria = vistoriaController.buscarVistoria(idVistoria);
        Agendamento agendamento = agendamentoController.buscarPorId(vistoria.getIdAgendamento());
        Veiculo veiculo = veiculoController.buscarPorId(agendamento.getIdVeiculo());
        Pagamento pagamento = pagamentoController.buscarPagamento(vistoria.getIdVistoria());
        Funcionario vistoriador = funcionarioController.buscarPorId(vistoria.getIdFuncionario());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel lblTitulo = new JLabel("Relatório de Vistoria");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelDetalhesLaudo.add(lblTitulo, gbc);
        
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        painelDetalhesLaudo.add(separator, gbc);

        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = 2; painelDetalhesLaudo.add(new JLabel("Data da Vistoria:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; painelDetalhesLaudo.add(new JLabel(vistoria.getDataVistoria().toString()), gbc);

        gbc.gridx = 0; gbc.gridy = 3; painelDetalhesLaudo.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; painelDetalhesLaudo.add(new JLabel(clienteLogado.getNome()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; painelDetalhesLaudo.add(new JLabel("Veículo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; painelDetalhesLaudo.add(new JLabel(veiculo.getNome_veiculo() + " " + veiculo.getModelo()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 5; painelDetalhesLaudo.add(new JLabel("Placa:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; painelDetalhesLaudo.add(new JLabel(veiculo.getPlaca()), gbc);

        gbc.gridx = 0; gbc.gridy = 6; painelDetalhesLaudo.add(new JLabel("Chassi:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6; painelDetalhesLaudo.add(new JLabel(veiculo.getChassi()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 7; painelDetalhesLaudo.add(new JLabel("Vistoriador:"), gbc);
        gbc.gridx = 1; gbc.gridy = 7; painelDetalhesLaudo.add(new JLabel(vistoriador != null ? vistoriador.getNome() : "Não informado"), gbc);
        
        gbc.gridx = 0; gbc.gridy = 8; painelDetalhesLaudo.add(new JLabel("Resultado:"), gbc);
        gbc.gridx = 1; gbc.gridy = 8; painelDetalhesLaudo.add(new JLabel(vistoria.getResultado().toString()), gbc);

        gbc.gridy = 9; gbc.gridwidth = 2; painelDetalhesLaudo.add(new JSeparator(SwingConstants.HORIZONTAL), gbc);

        gbc.gridy = 10; gbc.gridwidth = 1; gbc.gridx = 0; painelDetalhesLaudo.add(new JLabel("Valor do Pagamento:"), gbc);
        gbc.gridx = 1; painelDetalhesLaudo.add(new JLabel(pagamento != null ? "R$ " + pagamento.getValor().toString() : "Pendente"), gbc);

        gbc.gridy = 11; gbc.gridx = 0; painelDetalhesLaudo.add(new JLabel("Forma de Pagamento:"), gbc);
        gbc.gridx = 1; painelDetalhesLaudo.add(new JLabel(pagamento != null ? pagamento.getFormaPagamento().toString() : "Pendente"), gbc);
        
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
        JOptionPane.showMessageDialog(this, "Funcionalidade de impressão em desenvolvimento.");
    }
    
    private void baixarPDF() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de download em PDF em desenvolvimento.");
    }

    public void atualizarLaudos() {
        carregarListaLaudos();
    }
}