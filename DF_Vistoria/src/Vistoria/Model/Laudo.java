package Vistoria.Model;

import java.time.LocalDateTime;

public class Laudo {

	private int idLaudo;
	private String caminhoArquivo;
	private LocalDateTime dataGeracao;
	private int idVistoria;

	public Laudo(){}
	
	// Construtor completo para recuperar do banco de dados (com ID)
	public Laudo(int idLaudo, String caminhoArquivo, LocalDateTime dataGeracao, int idVistoria) {
		this.idLaudo = idLaudo;
		this.caminhoArquivo = caminhoArquivo;
		this.dataGeracao = dataGeracao;
		this.idVistoria = idVistoria;
	}

	// Construtor para criar um novo laudo (sem o ID, pois é auto_increment no
	// banco)
	public Laudo(String caminhoArquivo, LocalDateTime dataGeracao, int idVistoria) {
		this.caminhoArquivo = caminhoArquivo;
		this.dataGeracao = dataGeracao;
		this.idVistoria = idVistoria;
	}

	// Getters e Setters
	public int getIdLaudo() {
		return idLaudo;
	}

	public void setIdLaudo(int idLaudo) {
		this.idLaudo = idLaudo;
	}

	public String getCaminhoArquivo() {
		return caminhoArquivo;
	}

	public void setCaminhoArquivo(String caminhoArquivo) {
		this.caminhoArquivo = caminhoArquivo;
	}

	public LocalDateTime getDataGeracao() {
		return dataGeracao;
	}

	public void setDataGeracao(LocalDateTime dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public int getIdVistoria() {
		return idVistoria;
	}

	public void setIdVistoria(int idVistoria) {
		this.idVistoria = idVistoria;
	}
}