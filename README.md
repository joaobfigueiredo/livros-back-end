# LivrosBackend

Este projeto foi gerado com [Spring initializr](https://start.spring.io/).

## Baixar código e instalar dependencias

git clone https://github.com/joaobfigueiredo/livros-back-end.git

cd livros-back-end
mvn clean package

## Subindo a aplicação com banco de dados H2

Use o comando abaixo para iniciar a aplicação com o banco de dados H2. Esse banco é geralmente utilizado para fins de estudo e testes, pois seus dados não são salvos permanentemente. Assim, ao parar o servidor, todas as informações registradas serão descartadas.

mvn spring-boot:run -D"spring-boot.run.arguments=--spring.profiles.active=h2"


## Subindo a aplicação com banco de dados MySQL

Para utilizar o banco de dados MySQL, execute o script \src\main\resources\db\criar-banco-de-dados.sql no seu gerenciador de banco de dados. Esse script criará o schema db_livros e as tabelas necessárias para o funcionamento do sistema.

mvn spring-boot:run -D"spring-boot.run.arguments=--spring.profiles.active=mysql"
