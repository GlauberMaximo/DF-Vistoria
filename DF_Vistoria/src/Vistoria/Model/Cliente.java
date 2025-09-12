package Vistoria.Model;

import java.util.Objects;

public class Cliente {
	private int idCliente;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String senha;
	
	public Cliente() {}

	public Cliente(int idCliente, String nome, String cpf, String telefone, String email, String senha) {
		this.idCliente = idCliente;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}

	public Cliente(String nome, String cpf, String telefone, String email, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}

	// Getters e Setters
	public int getIdCliente() { return idCliente; }
	public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
	public String getNome() { return nome; }
	public void setNome(String nome) { this.nome = nome; }
	public String getCpf() { return cpf; }
	public void setCpf(String cpf) { this.cpf = cpf; }
	public String getTelefone() { return telefone; }
	public void setTelefone(String telefone) { this.telefone = telefone; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public String getSenha() { return senha; }
	public void setSenha(String senha) { this.senha = senha; }

	// Método toString() para facilitar a impressão do objeto
	@Override
	public String toString() {
		return "Cliente{" +
				"idCliente=" + idCliente +
				", nome='" + nome + '\'' +
				", cpf='" + cpf + '\'' +
				", telefone='" + telefone + '\'' +
				", email='" + email + '\'' +
				'}'; // Senha não é ideal imprimir por segurança
	}

	// Método equals() e hashCode() para comparar objetos com base no CPF
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return Objects.equals(cpf, cliente.cpf);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}
}