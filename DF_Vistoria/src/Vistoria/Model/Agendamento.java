package Vistoria.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Agendamento {
    private int idAgendamento;
    private LocalDate data_agendamento;
    private LocalTime hora;
    private String tipo_vistoria;
    private String status_agendamento;
    private int idCliente;
    private int idVeiculo;
    
    public Agendamento(){}

    // Construtor completo para buscar dados do banco
    public Agendamento(int idAgendamento, LocalDate data_agendamento, LocalTime hora, String tipo_vistoria, String status_agendamento, int idCliente, int idVeiculo) {
        this.idAgendamento = idAgendamento;
        this.data_agendamento = data_agendamento;
        this.hora = hora;
        this.tipo_vistoria = tipo_vistoria;
        this.status_agendamento = status_agendamento;
        this.idCliente = idCliente;
        this.idVeiculo = idVeiculo;
    }

    // Construtor para criar um novo agendamento (o ID e o status são definidos pelo banco)
    public Agendamento(LocalDate data_agendamento, LocalTime hora, String tipo_vistoria, int idCliente, int idVeiculo) {
        this.data_agendamento = data_agendamento;
        this.hora = hora;
        this.tipo_vistoria = tipo_vistoria;
        this.idCliente = idCliente;
        this.idVeiculo = idVeiculo;
    }

    // Getters e Setters
    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public LocalDate getData_agendamento() {
        return data_agendamento;
    }

    public void setData_agendamento(LocalDate data_agendamento) {
        this.data_agendamento = data_agendamento;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getTipo_vistoria() {
        return tipo_vistoria;
    }

    public void setTipo_vistoria(String tipo_vistoria) {
        this.tipo_vistoria = tipo_vistoria;
    }

    public String getStatus_agendamento() {
        return status_agendamento;
    }

    public void setStatus_agendamento(String status_agendamento) {
        this.status_agendamento = status_agendamento;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    // Métodos utilitários
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return idAgendamento == that.idAgendamento;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAgendamento);
    }

    @Override
    public String toString() {
        return "Agendamento{" +
                "idAgendamento=" + idAgendamento +
                ", data_agendamento=" + data_agendamento +
                ", hora=" + hora +
                ", tipo_vistoria='" + tipo_vistoria + '\'' +
                '}';
    }
}