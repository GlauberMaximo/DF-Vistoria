package Vistoria.Model;

import java.time.LocalDate;

public class Vistoria {

	private int idVistoria;
	private LocalDate dataVistoria;
	private ResultadoVistoria resultado;
	private String observacoes;
	private int idAgendamento;
	private int idFuncionario;

	public Vistoria() {
	}

	// Construtor completo para recuperar do banco de dados (com ID)
	public Vistoria(int idVistoria, LocalDate dataVistoria, ResultadoVistoria resultado, String observacoes,
			int idAgendamento, int idFuncionario) {
		this.idVistoria = idVistoria;
		this.dataVistoria = dataVistoria;
		this.resultado = resultado;
		this.observacoes = observacoes;
		this.idAgendamento = idAgendamento;
		this.idFuncionario = idFuncionario;
	}

	// Construtor para criar uma nova vistoria (sem o ID, pois é auto_increment no
	// banco)
	public Vistoria(LocalDate dataVistoria, ResultadoVistoria resultado, String observacoes, int idAgendamento,
			int idFuncionario) {
		this.dataVistoria = dataVistoria;
		this.resultado = resultado;
		this.observacoes = observacoes;
		this.idAgendamento = idAgendamento;
		this.idFuncionario = idFuncionario;
	}

	// Getters
	public int getIdVistoria() {
		return idVistoria;
	}

	public LocalDate getDataVistoria() {
		return dataVistoria;
	}

	public ResultadoVistoria getResultado() {
		return resultado;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public int getIdAgendamento() {
		return idAgendamento;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	// Setters
	public void setIdVistoria(int idVistoria) {
		this.idVistoria = idVistoria;
	}

	public void setDataVistoria(LocalDate dataVistoria) {
		this.dataVistoria = dataVistoria;
	}

	public void setResultado(ResultadoVistoria resultado) {
		this.resultado = resultado;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setIdAgendamento(int idAgendamento) {
		this.idAgendamento = idAgendamento;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
}