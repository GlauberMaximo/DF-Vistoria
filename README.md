# Estrutura de Pastas e Persistência com MySQL (JDBC)

Este projeto utiliza **JDBC** para realizar a persistência de dados no
banco de dados **MySQL**.\
Abaixo estão as instruções para configuração da estrutura de pastas e
implementação da conexão.

------------------------------------------------------------------------

## 📂 Estrutura de Pastas

No diretório raiz do seu projeto, crie a seguinte estrutura:

    /SeuProjeto
     └── Conexao
         └── ConexaoSQL.java

-   A pasta **`Conexao`** é responsável por armazenar as classes
    relacionadas à conexão com o banco de dados.
-   O arquivo **`ConexaoSQL.java`** implementa a lógica de conexão via
    JDBC.

------------------------------------------------------------------------

## ⚙️ Configuração da Conexão JDBC

No arquivo `ConexaoSQL.java`, implemente a conexão com o banco MySQL.\
Exemplo em **Java**:

``` java
package Vistoria.Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQL {

    // Substitua pelos dados do seu ambiente
    private static final String URL = "jdbc:mysql://localhost:3306/seu_banco";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }

    // Método de teste
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

------------------------------------------------------------------------

## 📦 Dependências

Certifique-se de adicionar o **Driver JDBC do MySQL** ao seu projeto:

-   **Maven**:

    ``` xml
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
    ```

-   **.jar manual**:\
    Baixe o `mysql-connector-java` e adicione ao **classpath** do
    projeto.

------------------------------------------------------------------------

## 🚀 Testando a Conexão

1.  Configure a `URL`, `USER` e `PASSWORD` no `ConexaoSQL.java`.

2.  Execute o método `main` dentro da classe.

3.  Verifique no console:

        Conexão realizada com sucesso!
        Conexão encerrada.

------------------------------------------------------------------------

## 📚 Referências

-   [Documentação Oficial
    JDBC](https://docs.oracle.com/javase/tutorial/jdbc/)\
-   [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

------------------------------------------------------------------------

✅ Agora sua aplicação está pronta para realizar persistência de dados
com MySQL via JDBC.
