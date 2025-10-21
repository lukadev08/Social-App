# Social App — Login, Cadastro e Perfil * Em construção *
 Formulário de login e cadastro da Rede Social™

 ## Descrição
Aplicação de uma rede social com fluxo de cadastro e login. Após o cadastro e autenticação bem‑sucedida o usuário é redirecionado para uma página de perfil que exibe os dados informados no cadastro. Foi utilizado padrões arquitetura que separam as responsabilidades no estilo MVC entre as camadas do Back-end. No front-end foi feito com simples HTML, CSS, Javascript puro interligando as chamadas do back.

## Tecnologias utilizadas
- Java 24
- Spring Framework (Spring Boot, Spring MVC, Spring Data JPA)  
- Banco de dados H2 (in‑memory / console para desenvolvimento)  
- Build: Maven 3.5
- BCrypt: para encoding de Senhas

## Instalação (pré-requisitos)
1. Java 24 instalado (JDK 24).  
2. Maven instalado (ou usar o wrapper presente: mvnw)
3. Clonar o repositório:
    - git clone <URL-do-repositório>
4. Pode ser necessário configurar algumas variáveis de ambiente (como portas locais).

## Como executar
Com Maven:
- mvn clean package
- java -jar target/*.jar
ou
- mvn spring-boot:runr

Acesse:
- Aplicação: http://localhost:8081
- H2 Console (desenvolvimento): http://localhost:8081/h2-console  
  (JDBC URL padrão: jdbc:h2:mem:testdb)

## Uso (fluxo)
1. Acesse a rota de cadastro (/register) pela página index.html no path /frontend.  
2. Interaja com a tela preenchendo os campos solicitados (nome, email, senha, etc.) e submeta.  
3. Faça login em /login com as credenciais cadastradas.  
4. Ao autenticar, o usuário é redirecionado para /profile, onde são exibidos os dados informados no cadastro.

