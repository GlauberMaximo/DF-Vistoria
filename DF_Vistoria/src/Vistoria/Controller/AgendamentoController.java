package Vistoria.Controller;

import Vistoria.DAO.AgendamentoDAO;
import Vistoria.Model.Agendamento;
import java.time.LocalDate;
import java.util.List;

public class AgendamentoController {

    private AgendamentoDAO agendamentoDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
    }

    /**
     * Adiciona um novo agendamento, com validações de data e campos.
     * @param agendamento O objeto Agendamento a ser adicionado.
     */
    public void adicionarAgendamento(Agendamento agendamento) {
        // Validação básica de campos obrigatórios
        if (agendamento.getData_agendamento() == null ||
            agendamento.getHora() == null ||
            agendamento.getTipo_vistoria() == null || agendamento.getTipo_vistoria().trim().isEmpty() ||
            agendamento.getIdCliente() <= 0 ||
            agendamento.getIdVeiculo() <= 0) {
            System.err.println("Erro: Todos os campos do agendamento são obrigatórios.");
            return;
        }

        // Validação de data: o agendamento não pode ser em uma data passada
        if (agendamento.getData_agendamento().isBefore(LocalDate.now())) {
            System.err.println("Erro: Não é possível agendar para uma data passada.");
            return;
        }
        
        // Outras validações, como horários de funcionamento, podem ser adicionadas aqui.
        
        agendamentoDAO.inserir(agendamento);
    }

    /**
     * Busca um agendamento pelo seu ID.
     * @param id O ID do agendamento.
     * @return O objeto Agendamento ou null se não for encontrado.
     */
    public Agendamento buscarPorId(int id) {
        return agendamentoDAO.buscarPorId(id);
    }
    
    /**
     * Lista todos os agendamentos.
     * @return Uma lista de objetos Agendamento.
     */
    public List<Agendamento> listarTodos() {
        return agendamentoDAO.listarTodos();
    }

    /**
     * Lista os agendamentos de um cliente específico.
     * @param idCliente O ID do cliente.
     * @return Uma lista de objetos Agendamento pertencentes ao cliente.
     */
    public List<Agendamento> buscarAgendamentosPorCliente(int idCliente) {
        return agendamentoDAO.buscarAgendamentosPorCliente(idCliente);
    }
    
    /**
     * Atualiza as informações de um agendamento.
     * @param agendamento O objeto Agendamento com as informações atualizadas.
     */
    public void atualizarAgendamento(Agendamento agendamento) {
        agendamentoDAO.atualizar(agendamento);
    }

    /**
     * Deleta um agendamento pelo seu ID.
     * @param id O ID do agendamento a ser deletado.
     */
    public void deletarAgendamento(int id) {
        agendamentoDAO.deletar(id);
    }
}