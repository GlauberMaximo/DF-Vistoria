package Vistoria.Model;

import java.time.LocalDateTime;

public class Desligamento {
    private int id;
    private int idFuncionario;
    private String nomeFuncionario;
    private String motivo;
    private LocalDateTime dataDesligamento;

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getNomeFuncionario() { return nomeFuncionario; }
    public void setNomeFuncionario(String nomeFuncionario) { this.nomeFuncionario = nomeFuncionario; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getDataDesligamento() { return dataDesligamento; }
    public void setDataDesligamento(LocalDateTime dataDesligamento) { this.dataDesligamento = dataDesligamento; }
}
