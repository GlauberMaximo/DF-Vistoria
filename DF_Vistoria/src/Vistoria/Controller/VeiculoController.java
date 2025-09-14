package Vistoria.Controller;

import Vistoria.DAO.VeiculoDAO;
import Vistoria.Model.Veiculo;
import java.util.List;

public class VeiculoController {

    private VeiculoDAO veiculoDAO;

    public VeiculoController() {
        this.veiculoDAO = new VeiculoDAO();
    }

    /**
     * Adiciona um novo veículo. Assume que as validações de entrada
     * já foram realizadas na camada de interface.
     * @param veiculo O objeto Veiculo a ser adicionado.
     */
    public void adicionarVeiculo(Veiculo veiculo) {
        // A lógica de validação foi movida para a View (PanelCadastrarVeiculo).
        // A responsabilidade do Controller é apenas chamar a camada DAO.
        veiculoDAO.inserir(veiculo);
    }
    
    // --- Métodos de Busca e Manipulação ---
    
    /**
     * Busca um veículo pelo seu ID.
     * @param id O ID do veículo.
     * @return O objeto Veiculo ou null se não for encontrado.
     */
    public Veiculo buscarPorId(int id) {
        return veiculoDAO.buscarPorId(id);
    }
    
    /**
     * Busca um veículo pela placa no banco de dados.
     * @param placa A placa do veículo.
     * @return O objeto Veiculo ou null se não for encontrado.
     */
    public Veiculo buscarPorPlaca(String placa) {
        return veiculoDAO.buscarPorPlaca(placa);
    }
    
    /**
     * Busca um veículo pelo chassi no banco de dados.
     * @param chassi O chassi do veículo.
     * @return O objeto Veiculo ou null se não for encontrado.
     */
    public Veiculo buscarPorChassi(String chassi) {
        return veiculoDAO.buscarPorChassi(chassi);
    }
    
    /**
     * Lista todos os veículos cadastrados.
     * @return Uma lista de objetos Veiculo.
     */
    public List<Veiculo> listarTodos() {
        return veiculoDAO.listarTodos();
    }
    
    /**
     * Lista todos os veículos de um cliente específico.
     * @param idCliente O ID do cliente.
     * @return Uma lista de objetos Veiculo pertencentes ao cliente.
     */
    public List<Veiculo> buscarVeiculosPorCliente(int idCliente) {
        return veiculoDAO.buscarVeiculosPorCliente(idCliente);
    }

    /**
     * Atualiza as informações de um veículo.
     * @param veiculo O objeto Veiculo com as informações atualizadas.
     */
    public void atualizarVeiculo(Veiculo veiculo) {
        veiculoDAO.atualizar(veiculo);
    }

    /**
     * Deleta um veículo pelo seu ID.
     * @param id O ID do veículo a ser deletado.
     */
    public void deletarVeiculo(int id) {
        veiculoDAO.deletar(id);
    }
}