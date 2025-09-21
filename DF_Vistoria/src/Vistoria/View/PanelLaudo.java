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
	// Removido: private JButton btnImprimir;

	private Map<String, Integer> mapVistoriaId = new HashMap<>();
	private boolean isUpdating = false;

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

		// Removemos a criação e adição do botão "Imprimir Laudo"
		JButton btnBaixarPDF = new JButton("Baixar PDF");
		btnBaixarPDF.setBackground(new Color(187, 208, 235));
		btnBaixarPDF.setEnabled(true);
		btnBaixarPDF.addActionListener(e -> baixarPDF());
		
		// Apenas adicionamos o botão de baixar PDF
		painelInferior.add(btnBaixarPDF);

		add(painelInferior, BorderLayout.SOUTH);

		carregarListaLaudos();
	}

	private void carregarListaLaudos() {
		isUpdating = true;
		comboVistorias.removeAllItems();
		mapVistoriaId.clear();

		List<Laudo> laudos = laudoController.listarLaudosDoCliente(clienteLogado.getIdCliente());

		if (laudos.isEmpty()) {
			comboVistorias.addItem("Nenhum laudo disponível");
			// Removido: btnImprimir.setEnabled(false);
		} else {
			for (Laudo laudo : laudos) {
				// Correção: A busca por ID de Agendamento deve ser feita com o ID do
				// Agendamento, não da Vistoria
				Vistoria vistoria = vistoriaController.buscarVistoria(laudo.getIdVistoria());
				if (vistoria != null) {
					Agendamento agendamento = agendamentoController.buscarPorId(vistoria.getIdAgendamento());
					if (agendamento != null) {
						String texto = "Laudo de " + agendamento.getData_agendamento() + " - " + laudo.getIdVistoria();
						comboVistorias.addItem(texto);
						mapVistoriaId.put(texto, laudo.getIdVistoria());
					}
				}
			}
			if (comboVistorias.getItemCount() > 0) {
				comboVistorias.setSelectedIndex(0);
				// Removido: btnImprimir.setEnabled(true);
			} else {
				comboVistorias.addItem("Nenhum laudo disponível");
				// Removido: btnImprimir.setEnabled(false);
			}
		}
		isUpdating = false;
	}

	private void exibirDetalhesLaudo() {
		String itemSelecionado = (String) comboVistorias.getSelectedItem();
		painelDetalhesLaudo.removeAll();

		if (itemSelecionado == null || itemSelecionado.equals("Nenhum laudo disponível")) {
			painelDetalhesLaudo.revalidate();
			painelDetalhesLaudo.repaint();
			return;
		}

		Integer idVistoria = mapVistoriaId.get(itemSelecionado);
		if (idVistoria == null) {
			System.err.println("ID da vistoria não encontrado no mapa para o item: " + itemSelecionado);
			painelDetalhesLaudo.revalidate();
			painelDetalhesLaudo.repaint();
			return;
		}

		Vistoria vistoria = vistoriaController.buscarVistoria(idVistoria);
		if (vistoria == null) {
			JOptionPane.showMessageDialog(this, "Vistoria não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
			painelDetalhesLaudo.revalidate();
			painelDetalhesLaudo.repaint();
			return;
		}

		Agendamento agendamento = agendamentoController.buscarPorId(vistoria.getIdAgendamento());
		Veiculo veiculo = veiculoController.buscarPorId(agendamento.getIdVeiculo());
		Pagamento pagamento = pagamentoController.buscarPagamento(vistoria.getIdVistoria());
		Funcionario vistoriador = funcionarioController.buscarPorId(vistoria.getIdFuncionario());

		painelDetalhesLaudo.setLayout(new BorderLayout(10, 10));

		// ===========================
		// CABEÇALHO
		// ===========================
		JPanel cabecalho = new JPanel(new BorderLayout());
		cabecalho.setBackground(Color.WHITE);

		JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/imagens/logo.png")));
		cabecalho.add(lblLogo, BorderLayout.WEST);

		JLabel lblTitulo = new JLabel("RELATÓRIO DE VISTORIA", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		cabecalho.add(lblTitulo, BorderLayout.CENTER);

		JLabel lblData = new JLabel("Emitido em: " + java.time.LocalDate.now(), SwingConstants.RIGHT);
		lblData.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		cabecalho.add(lblData, BorderLayout.EAST);

		painelDetalhesLaudo.add(cabecalho, BorderLayout.NORTH);

		// ===========================
		// CORPO PRINCIPAL
		// ===========================
		JPanel corpo = new JPanel();
		corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
		corpo.setBackground(Color.WHITE);

		// --- seção dados do laudo ---
		corpo.add(criarSecao("Dados do Laudo",
				new String[][] { { "Data da Vistoria:", vistoria.getDataVistoria().toString() },
						{ "Resultado:", vistoria.getResultado().toString() },
						{ "Vistoriador:", vistoriador != null ? vistoriador.getNome() : "Não informado" } }));

		// --- seção cliente/veículo ---
		corpo.add(criarSecao("Cliente e Veículo",
				new String[][] { { "Cliente:", clienteLogado.getNome() },
						{ "Veículo:", veiculo.getNome_veiculo() + " " + veiculo.getModelo() },
						{ "Placa:", veiculo.getPlaca() }, { "Chassi:", veiculo.getChassi() } }));

		// --- seção pagamento ---
		corpo.add(criarSecao("Pagamento",
				new String[][] { { "Valor:", pagamento != null ? "R$ " + pagamento.getValor().toString() : "Pendente" },
						{ "Forma:", pagamento != null ? pagamento.getFormaPagamento().toString() : "Pendente" } }));

		// --- observações ---
		JPanel panelObs = new JPanel(new BorderLayout());
		panelObs.setBackground(Color.WHITE);
		JLabel lblObs = new JLabel("Observações:");
		lblObs.setFont(new Font("Segoe UI", Font.BOLD, 14));
		JTextArea txtObs = new JTextArea(vistoria.getObservacoes());
		txtObs.setLineWrap(true);
		txtObs.setWrapStyleWord(true);
		txtObs.setEditable(false);
		txtObs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		panelObs.add(lblObs, BorderLayout.NORTH);
		panelObs.add(new JScrollPane(txtObs), BorderLayout.CENTER);
		panelObs.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		corpo.add(panelObs);

		painelDetalhesLaudo.add(corpo, BorderLayout.CENTER);

		painelDetalhesLaudo.revalidate();
		painelDetalhesLaudo.repaint();
	}

	/**
	 * Cria uma seção de dados em formato de tabela 2 colunas.
	 */
	private JPanel criarSecao(String titulo, String[][] dados) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createTitledBorder(titulo));

		JPanel grid = new JPanel(new GridLayout(0, 2, 10, 5));
		grid.setBackground(Color.WHITE);

		for (String[] linha : dados) {
			JLabel lblKey = new JLabel(linha[0]);
			lblKey.setFont(new Font("Segoe UI", Font.BOLD, 13));
			JLabel lblVal = new JLabel(linha[1]);
			lblVal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
			lblVal.setForeground(Color.DARK_GRAY);

			grid.add(lblKey);
			grid.add(lblVal);
		}

		panel.add(grid, BorderLayout.CENTER);
		return panel;
	}

	// private void imprimirLaudo() {
	// JOptionPane.showMessageDialog(this, "Funcionalidade de impressão em
	// desenvolvimento.");
	// }

	private void baixarPDF() {
	    String itemSelecionado = (String) comboVistorias.getSelectedItem();
	    
	    if (itemSelecionado == null || itemSelecionado.equals("Nenhum laudo disponível")) {
	        JOptionPane.showMessageDialog(this, "Nenhum laudo selecionado.", "Atenção", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    Integer idVistoria = mapVistoriaId.get(itemSelecionado);
	    if (idVistoria == null) {
	        JOptionPane.showMessageDialog(this, "ID da vistoria não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Busca todos os objetos de modelo necessários
	    Vistoria vistoria = vistoriaController.buscarVistoria(idVistoria);
	    Agendamento agendamento = agendamentoController.buscarPorId(vistoria.getIdAgendamento());
	    Veiculo veiculo = veiculoController.buscarPorId(agendamento.getIdVeiculo());
	    Pagamento pagamento = pagamentoController.buscarPagamento(vistoria.getIdVistoria());
	    Funcionario vistoriador = funcionarioController.buscarPorId(vistoria.getIdFuncionario());
	    Cliente cliente = clienteController.buscarClientePorId(clienteLogado.getIdCliente()); // Cliente já está logado

	    if (vistoria == null || agendamento == null || veiculo == null || vistoriador == null) {
	        JOptionPane.showMessageDialog(this, "Dados do laudo incompletos. Não é possível gerar o PDF.", "Erro", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Instancia e chama a nova classe para gerar o PDF
	    GerarPdfLaudo gerador = new GerarPdfLaudo();
	    gerador.gerarPdf(vistoria, cliente, veiculo, pagamento, vistoriador);
	}

	public void atualizarLaudos() {
		carregarListaLaudos();
	}
}