1. O que foi feito
   * Estrutura de Entidades: Foi criada uma estrutura de entidades que inclui Usuarios, Responsaveis e Tarefas, representando os dados do sistema. As entidades têm relações apropriadas, como chaves estrangeiras.
   * Controladores REST: Foram implementados controladores REST para Usuario e Tarefa. Esses controladores gerenciam as operações CRUD e expõem endpoints que a aplicação front-end pode chamar.
   * Serviços: Implementados serviços que contêm a lógica de negócios para manipular as entidades, como a validação de dados e a interação com o banco de dados.
   * Testes Unitários: Foram criados testes unitários utilizando JUnit e Mockito para garantir que os controladores e serviços funcionem corretamente. Os testes verificam se as operações de criação de usuários e tarefas retornam os status HTTP esperados.
   * Segurança: A aplicação implementa autenticação e criptografia de senhas usando BCrypt.
   * Integração com o Banco de Dados: O projeto é configurado para se conectar a um banco de dados PostgreSQL, utilizando o Spring Data JPA para facilitar as operações de banco de dados.
   * Documentação com Swagger: O projeto utiliza Swagger para documentar as APIs.
2. Como usar
   * Instalar o java jdk 11
   * Clonar o repositório
     ```
     git clone https://github.com/AllysonGustavo1/taskify-back-end.git
     ```
   * Navegar até o projeto
     ```
     cd caminho/para/o/projeto
     ```
   * Compilar e baixar dependências
     ```
     mvn clean install
     ```
   * Executar o projeto apertando play no intellij ou
     ```
     mvn spring-boot:run
     ```