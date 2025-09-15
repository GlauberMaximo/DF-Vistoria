# Instruções de Estrutura de Pastas para Persistência com MySQL JDBC

Este projeto requer a criação de uma estrutura específica para a persistência de dados utilizando o MySQL via JDBC. Siga atentamente as instruções abaixo para que o projeto funcione corretamente.

## Passos para Configuração

1. **Criar a Pasta `Conexao`**
   
   No diretório raiz do seu projeto, crie uma pasta chamada `Conexao`. Essa pasta será responsável por armazenar todos os arquivos relacionados à conexão com o banco de dados.

   ```
   /SeuProjeto
     └── Conexao
   ```

2. **Criar o Arquivo `ConexaoSQL`**

   Dentro da pasta `Conexao`, crie um arquivo chamado `ConexaoSQL` (por exemplo, `ConexaoSQL.java` se estiver usando Java). Este arquivo será responsável por implementar a lógica de conexão e persistência no banco de dados MySQL utilizando JDBC.

   ```
   /SeuProjeto
     └── Conexao
         └── ConexaoSQL.java
   ```

3. **Implementação da Conexão JDBC**

   No arquivo `ConexaoSQL`, implemente a conexão com o banco de dados MySQL utilizando JDBC. Certifique-se de incluir as dependências do driver JDBC do MySQL no seu projeto.

   Exemplo básico de implementação em Java:

   ```java
package Vistoria.Conexao;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQL {
	
	private static final String URL = "";
    private static final String USER = ""; 
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    // Método de teste da conexão
    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Conexão realizada com sucesso!");
            try {
                conn.close();
                System.out.println("Conexão encerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

   ```

   > **Observação:** Substitua `"seu_banco"`, `"seu_usuario"` e `"sua_senha"` pelos dados do seu ambiente.

---

Com esses passos, sua aplicação estará pronta para realizar a persistência de dados no MySQL utilizando JDBC. Qualquer dúvida, consulte a documentação do JDBC ou abra uma issue neste repositório.
