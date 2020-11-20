# Desafio Agibank

Para iniciar o sistema, após executar checkout dos fontes, executar o comando dentro do diretório raiz do projeto: ./mvnw spring-boot:run

O sistema possui uma tela, com as funções de upload de arquivos e visualização dos dados calulados, do arquivo '.dat', 'resume.done.dat'.

O sistema possui um job, que fica processando apenas os arquivos '.dat', localizados no diretório '%HOMEPATH%/data/in',
calculando e enviando em tempo real, os dados analisados para o arquivo de destino, 'resume.done.dat' que se encontra no diretório %HOMEPATH%/data/out.

Após iniciar o projeto, você poderá executar o sistema de duas formas:
1. Via http, realizando upload dos arquivos, no endereço http://localhost:8080/.
   A tela realiza a importação e leitura dos arquivos '.dat', criação do arquivo de saída 'resume.done.dat', e um link para visualização dos dados, do desafio calculados. 
2. Copiar os lotes de  arquivos para o diretório '%HOMEPATH%/data/in'.
  O job é responsável por fazer a leitura dos arquivos .dat, processar, calcular e gerar o arquivo 'resume.done.dat' em tempo real.  
