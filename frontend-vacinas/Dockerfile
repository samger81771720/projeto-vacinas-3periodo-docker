# o contêiner final do front será construído com uma imagem do Node.js (ambiente de execução JavaScript) rodando sobre o Alpine Linux (sistema operacional leve e otimizado). Isso resulta em uma combinação eficiente, tanto em termos de funcionalidade quanto em termos de tamanho da imagem.
FROM node:alpine

# Define o diretório de trabalho dentro do contêiner como /app. Todos os comandos subsequentes serão executados neste diretório. Se o diretório não existir, ele será criado.
WORKDIR /app

# Copia os arquivos package.json e package-lock.json (se existir) do diretório atual no host para o diretório de trabalho /app no contêiner. Estes arquivos contêm a lista de dependências do projeto.
COPY package*.json .

# Executa o comando npm install dentro do contêiner para instalar todas as dependências listadas no package.json. Este comando cria a pasta node_modules com todas as dependências necessárias.
RUN npm install

# instala a CLI do Angular globalmente dentro do contêiner para permitir o uso do comando ng. O -g indica que a instalação é global.
RUN npm install -g @angular/cli

# Copia todos os arquivos e diretórios do diretório atual no host para o diretório de trabalho /app no contêiner. Isso inclui o código-fonte da aplicação Angular.
COPY . .

# Informa ao Docker que o contêiner ouvirá na porta 4200. Essa é a porta padrão usada pelo Angular CLI para servir a aplicação durante o desenvolvimento.
EXPOSE 4200

# Define o comando padrão a ser executado quando o contêiner é iniciado. Neste caso, ng serve é usado para iniciar o servidor de desenvolvimento Angular. A opção --host 0.0.0.0 permite que a aplicação Angular seja acessível de fora do contêiner.
CMD ["ng", "serve", "--host", "0.0.0.0"]




