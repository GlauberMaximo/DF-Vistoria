package Vistoria.Model;

import java.util.Objects;

public class Funcionario {
	private int idFuncionario;
	private String nome;
	private String email;
	private String matricula;
	private String cargo;
	private String senha;

	public Funcionario() {
	}

	public Funcionario(int idFuncionario, String nome, String email, String matricula, String cargo, String senha) {
		this.idFuncionario = idFuncionario;
		this.nome = nome;
		this.email = email;
		this.matricula = matricula;
		this.cargo = cargo;
		this.senha = senha;
	}

	public Funcionario(String nome, String email, String matricula, String cargo, String senha) {
		this.nome = nome;
		this.email = email;
		this.matricula = matricula;
		this.cargo = cargo;
		this.senha = senha;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Funcionario{" + 
				"idFuncionario=" + idFuncionario + 
				", nome='" + nome + "'" + 
				", matricula='" + matricula + "'" + 
				", cargo='" + cargo + "'" +
				"}";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Funcionario that = (Funcionario) o;
		return Objects.equals(matricula, that.matricula);
	}

	@Override
	public int hashCode() {
		return Objects.hash(matricula);
	}
}