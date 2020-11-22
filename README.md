# Desafio Agibank
### Análise de dados
O sistema deve ler dados do diretório padrão, localizado em %HOMEPATH%/data/in.
O sistema deve ler somente arquivos .dat. Depois de processar todos os arquivos dentro do diretório padrão de entrada, o sistema deve criar um arquivo dentro do diretório de saída padrão, localizado em %HOMEPATH%/data/out.
O nome do arquivo deve seguir o padrão, {flat_file_name} .done.dat.
O conteúdo do arquivo de saída deve resumir os seguintes dados:
- Quantidade de clientes no arquivo de entrada
- Quantidade de vendedor no arquivo de entrada
- ID da venda mais cara
- O pior vendedor
O sistema deve estar funcionando o tempo todo.
Todos os arquivos novos estar disponível, tudo deve ser executado

### The system performs the process in two ways:
1. upload batches of files to http://localhost/8080.
2. The system automatically creates directories. Copy batches of files manually to directory %HOMEPATH%data/in, and wait for the system job to finish processing. The job verifies the directory of the files in real time, if the files are added, removed or edited, the job runs the process of generating the resume.done.dat file.  

### Run 
- ./mvnw spring-boot:run
- http://localhost/8080/.

### What will you need:
- java 8.
- Apache Maven 3.6.0.
- Tomcat 8 (optional, only if you want to use catalina with the .war file published on your machine).

