package Vistoria.Controller;

import Vistoria.DAO.LaudoDAO;
import Vistoria.Model.Laudo;

import java.util.List;

public class LaudoController {

    private LaudoDAO laudoDAO;

    public LaudoController() {
        this.laudoDAO = new LaudoDAO();
    }
    
    public void gerarLaudo(String caminhoArquivo, int idVistoria) {
        // Você pode adicionar validações aqui antes de criar o laudo
        Laudo laudo = new Laudo(caminhoArquivo, java.time.LocalDateTime.now(), idVistoria);
        laudoDAO.criarLaudo(laudo);
    }
    
    public Laudo buscarLaudoPorVistoria(int idVistoria) {
        return laudoDAO.buscarLaudoPorIdVistoria(idVistoria);
    }
    
    public List<Laudo> listarLaudosDoCliente(int idCliente) {
        return laudoDAO.buscarLaudosPorIdCliente(idCliente);
    }
    
    // Você pode adicionar outros métodos de controle aqui, como deletar laudo
}