package Vistoria.Controller;

import Vistoria.DAO.FuncionarioDAO;
import Vistoria.Model.Funcionario;
import java.util.List;

public class FuncionarioController {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    /**
     * Adiciona um novo funcionário.
     * @param funcionario O objeto Funcionario a ser adicionado.
     */
    public void adicionarFuncionario(Funcionario funcionario) {
        // Validação completa dos campos obrigatórios
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()
                || funcionario.getMatricula() == null || funcionario.getMatricula().trim().isEmpty()
                || funcionario.getEmail() == null || funcionario.getEmail().trim().isEmpty()
                || funcionario.getSenha() == null || funcionario.getSenha().trim().isEmpty()
                || funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            System.err.println("Erro: Nome, Matrícula, Email, Senha e Cargo são campos obrigatórios.");
            return;
        }

        // Verifica se a matrícula já existe no banco de dados
        if (funcionarioDAO.buscarPorMatricula(funcionario.getMatricula()) != null) {
            System.err.println("Erro: Já existe um funcionário com a matrícula " + funcionario.getMatricula());
            return;
        }

        funcionarioDAO.inserir(funcionario);
    }
    
    /**
     * Autentica um funcionário com base na matrícula e senha.
     * @param matricula A matrícula informada.
     * @param senha A senha informada.
     * @return O objeto Funcionario se a autenticação for bem-sucedida, caso contrário, retorna null.
     */
    public Funcionario autenticar(String matricula, String senha) {
        Funcionario funcionario = funcionarioDAO.buscarPorMatricula(matricula);

        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            return funcionario;
        }

        return null;
    }
    
    /**
     * Busca um funcionário pelo ID.
     * @param id O ID do funcionário.
     * @return O objeto Funcionario ou null se não encontrado.
     */
    public Funcionario buscarPorId(int id) {
        return funcionarioDAO.buscarPorId(id);
    }

    /**
     * Lista todos os funcionários.
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarTodos() {
        return funcionarioDAO.listarTodos();
    }

    /**
     * Atualiza os dados de um funcionário.
     * @param funcionario O objeto Funcionario com os dados atualizados.
     */
    public void atualizarFuncionario(Funcionario funcionario) {
        funcionarioDAO.atualizar(funcionario);
    }

    /**
     * Deleta um funcionário pelo ID.
     * @param id O ID do funcionário a ser deletado.
     */
    public void deletarFuncionario(int id) {
        funcionarioDAO.deletar(id);
    }
}