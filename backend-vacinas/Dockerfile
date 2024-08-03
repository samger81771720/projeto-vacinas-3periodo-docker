# A construção da imagem Docker utilizando a abordagem de construção multi-stage. A construção multi-stage é eficiente porque permite que você construa a aplicação em um estágio e use apenas os artefatos necessários no estágio final, resultando em uma imagem menor e mais otimizada para produção.


# "MULTI-STAGE - Stage 1": Construíndo(buildando) a aplicação com Maven. Usa Maven para compilar(Compilar é transformar código-fonte(ex.: linguagem Java), em código executável que o computador entende(um arquivo binário (.exe))) e empacotar a aplicação Java, e gera o arquivo WAR e as dependências.

# especifica que o primeiro estágio da construção da imagem Docker usará uma imagem base que combina Maven 3.8.4 com OpenJDK 11. O alias build permite que os resultados dessa etapa sejam usados posteriormente em outro estágio de construção da imagem.
FROM maven:3.8.4-openjdk-11 AS build

# Definindo o diretório de trabalho dentro do container como /app
WORKDIR /app

# Copiando o arquivo local pom.xml(o qual está dentro da pasta do projeto do back end) para o diretorio /app do conteiner
COPY pom.xml .

# Copiando o diretorio local /src para o diretorio /app/src do conteiner
COPY src ./src


# Buildando aplicacao e copiando as dependencias

# " mvn clean package -DskipTests" garante que o código-fonte seja compilado e empacotado sem executar os testes unitários. O resultado típico desse comando é a geração de um arquivo JAR (ou WAR) no diretório target do projeto.
# " mkdir -p dependency-jars " cria o diretório dependency-jars dentro do diretório atual de trabalho (/app, como definido pelo WORKDIR /app). Este diretório será usado para armazenar as dependências do projeto que serão copiadas posteriormente.
# " mvn dependency:copy-dependencies -DoutputDirectory=dependency-jars " executa o plugin Maven que copia todas as dependências (arquivos JAR) do projeto para o diretório dependency-jars. Essas dependências são necessárias para executar o projeto em um ambiente isolado, como um contêiner Docker.
RUN mvn clean package -DskipTests && \
    mkdir -p dependency-jars && \
    mvn dependency:copy-dependencies -DoutputDirectory=dependency-jars

# "MULTI-STAGE - Stage 2" Criando a imagem final com Tomcat. Usa Tomcat e copia os artefatos gerados no primeiro estágio para a imagem final, configurando o ambiente para rodar a aplicação.
# Isso significa que no primeiro estágio do Dockerfile, a imagem Maven é usada para compilar o código-fonte Java e empacotar a aplicação em um arquivo WAR. Isso envolve transformar o código em um formato executável e reunir todas as dependências necessárias para rodar a aplicação. Depois, esses artefatos gerados são usados no segundo estágio com o Tomcat, onde a aplicação será executada.
# O Docker usará uma abordagem de construção multi-stage para otimizar a imagem final. Após usar a imagem maven:3.8.4-openjdk-11 para "construir e empacotar a aplicação no primeiro estágio"(finalidade do maven no primeiro estágio), ele descartará essa imagem e os arquivos temporários associados. A imagem final será baseada apenas na imagem tomcat:10.1-jdk11-openjdk-slim, incluindo apenas os artefatos necessários (o arquivo WAR e as dependências) copiados do primeiro estágio. Isso resulta em uma imagem final menor e mais eficiente.
#  Nesse segundo estágio da construção da imagem docker(o primeiro foi esse: "FROM maven:3.8.4-openjdk-11 AS build") utiliza os artefatos (arquivo WAR e dependências) gerados no primeiro estágio, criando a imagem final com Tomcat que será usada para rodar a aplicação. Construção da imagem Docker a partir de uma imagem base oficial do Tomcat, versão 10.1-jdk11-openjdk-slim. 
FROM tomcat:10.1-jdk11-openjdk-slim

# Remove todos os arquivos e diretórios diretamente dentro de /usr/local/tomcat/webapps/ dentro da imagem Docker. Isso é útil para limpar o diretório de implantação padrão do Tomcat antes de copiar novos artefatos de aplicação para ele.
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiando o arquivo WAR (Web Application Archive, exemplos: "MANIFEST.MF", "servlet", "web.xml", "index") do estágio de build para o diretorio /webapps do Tomcat (e renomeando para ROOT.war)
COPY --from=build /app/target/projeto-vacinas-3periodo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# copia todo o conteúdo do diretório /app/dependency-jars do estágio de construção (build) para o diretório /usr/local/tomcat/lib/ dentro da imagem final do Tomcat.
COPY --from=build /app/dependency-jars /usr/local/tomcat/lib/

# copia o arquivo Context.xml do diretório local(localhost) do projeto para o diretório de configuração do Tomcat dentro da imagem Docker. O destino especificado é /usr/local/tomcat/conf/context.xml.
COPY src/main/webapp/META-INF/Context.xml /usr/local/tomcat/conf/context.xml

# Expondo a porta 8080
EXPOSE 8080

# Executando o Tomcat
CMD ["catalina.sh", "run"]