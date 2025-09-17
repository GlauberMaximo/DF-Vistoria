package Vistoria.Controller;

import Vistoria.DAO.PagamentoDAO;
import Vistoria.Model.Pagamento;

import java.util.List;

public class PagamentoController {

    private PagamentoDAO pagamentoDAO;

    public PagamentoController() {
        this.pagamentoDAO = new PagamentoDAO();
    }

    public void criarPagamento(Pagamento pagamento) {
        pagamentoDAO.inserir(pagamento);
    }

    public Pagamento buscarPagamento(int idPagamento) {
        return pagamentoDAO.buscarPorId(idPagamento);
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoDAO.listarTodos();
    }

    public void atualizarPagamento(Pagamento pagamento) {
        pagamentoDAO.atualizar(pagamento);
    }

    public void deletarPagamento(int idPagamento) {
        pagamentoDAO.deletar(idPagamento);
    }
}
