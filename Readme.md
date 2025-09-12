# DF-Vistoria

## Descrição

O **DF-Vistoria** é um projeto Java desenvolvido para auxiliar no processo de vistorias, permitindo o registro, consulta e gerenciamento de informações de forma prática.

## Pré-requisitos

- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Eclipse IDE](https://www.eclipse.org/downloads/)
- Git (opcional, para clonar o repositório)

## Instalação e Configuração

1. **Clone o repositório (opcional):**
   ```
   git clone https://github.com/seu-usuario/DF-Vistoria.git
   ```

2. **Abra o Eclipse e importe o projeto:**
   - Vá em `File > Import > Existing Projects into Workspace`.
   - Selecione a pasta do projeto `DF-Vistoria`.

3. **Crie o arquivo de conexão:**
   - No pacote `Vistoria.Conexao`, crie um novo arquivo Java chamado `ConexaoSQL.java`.
   - Este arquivo deve conter as configurações de acesso ao banco de dados (usuário, senha, URL, etc).
   - **Atenção:** O arquivo `ConexaoSQL.java` está listado no `.gitignore` e não será enviado para o repositório por questões de segurança. Cada desenvolvedor deve criar o seu próprio arquivo localmente.

   **Exemplo de estrutura do arquivo:**
   ```java
   // pacote: Vistoria.Conexao
   package Vistoria.Conexao;

   public class ConexaoSQL {
       public static final String URL = "jdbc:seu_banco://localhost:porta/nome_banco";
       public static final String USUARIO = "seu_usuario";
       public static final String SENHA = "sua_senha";
   }
   ```

4. **Compile e execute o projeto:**
   - Clique com o botão direito no arquivo principal (`Main.java` ou similar) e selecione `Run As > Java Application`.

## Uso

- Utilize a interface da aplicação para realizar, consultar e gerenciar vistorias conforme as opções disponíveis.

## Observações

- O arquivo `ConexaoSQL.java` **não deve ser enviado ao repositório**.
- Para dúvidas ou sugestões, utilize as issues do repositório.

## Licença

Este projeto está sob a licença MIT.