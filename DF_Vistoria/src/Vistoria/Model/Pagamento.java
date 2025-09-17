package Vistoria.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {

    private int idPagamento;
    private FormaPagamento formaPagamento;
    private BigDecimal valor;
    private LocalDate dataPagamento;
    private int idVistoria;
    
    public Pagamento(){}

    // Construtor completo para recuperar do banco de dados (com ID)
    public Pagamento(int idPagamento, FormaPagamento formaPagamento, BigDecimal valor, LocalDate dataPagamento, int idVistoria) {
        this.idPagamento = idPagamento;
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.idVistoria = idVistoria;
    }

    // Construtor para criar um novo pagamento (sem o ID, pois é auto_increment no banco)
    public Pagamento(FormaPagamento formaPagamento, BigDecimal valor, LocalDate dataPagamento, int idVistoria) {
        this.formaPagamento = formaPagamento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.idVistoria = idVistoria;
    }
    
    // Getters e Setters
    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public int getIdVistoria() {
        return idVistoria;
    }

    public void setIdVistoria(int idVistoria) {
        this.idVistoria = idVistoria;
    }
}