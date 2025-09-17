package Vistoria.Controller;

import Vistoria.DAO.VistoriaDAO;
import Vistoria.Model.Vistoria;

import java.util.List;

public class VistoriaController {

	private VistoriaDAO vistoriaDAO;

	public VistoriaController() {
		this.vistoriaDAO = new VistoriaDAO();
	}

	// Criar nova vistoria
	public void criarVistoria(Vistoria vistoria) {
		vistoriaDAO.inserir(vistoria);
	}

	// Buscar vistoria pelo ID
	public Vistoria buscarVistoria(int id) {
		return vistoriaDAO.buscarPorId(id);
	}

	// Listar todas as vistorias
	public List<Vistoria> listarVistorias() {
		return vistoriaDAO.listarTodas();
	}

	// Atualizar vistoria existente
	public void atualizarVistoria(Vistoria vistoria) {
		vistoriaDAO.atualizar(vistoria);
	}

	// Deletar vistoria
	public void deletarVistoria(int id) {
		vistoriaDAO.deletar(id);
	}
}
