FROM maven:3.9.9-amazoncorretto-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine
COPY --from=build target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"pipeline {
                                   agent any
                                   stages {
                                       stage('Verificar Repositório') {
                                           steps {
                                               checkout([$class: 'GitSCM', branches: [[name: '*/main']], useRemoteConfigs: [[url: 'https://github.com/Pablo-Damascena-Maia/MINISTOCK']]])
                                           }
                                       }

                                       stage('Instalar Dependências') {
                                           steps {
                                               script {
                                                   // Atualiza o PATH se necessário
                                                   env.PATH = "/usr/bin:$PATH"
                                                   // Instalar as dependências Maven antes de compilar o projeto
                                                   sh 'mvn clean install'  // Instala as dependências do Maven
                                               }
                                           }
                                       }

                                       stage('Construir Imagem Docker') {
                                           steps {
                                               script {
                                                   def appName = 'ministock'
                                                   def imageTag = "${appName}:${env.BUILD_ID}"

                                                   // Construir a imagem Docker
                                                   sh "docker build -t ${imageTag} ."
                                               }
                                           }
                                       }

                                       stage('Fazer Deploy') {
                                           steps {
                                               script {
                                                   def appName = 'ministock'
                                                   def imageTag = "${appName}:${env.BUILD_ID}"

                                                   // Parar e remover o container existente, se houver
                                           		sh "docker stop ${appName} || exit 0"
                                           		sh "docker rm -v ${appName} || exit 0"  // Remover o container e os volumes associados

                                                   // Executar o novo container
                                                   sh "docker run -d --name ${appName} -p 8404:8404 ${imageTag}"

                                               }
                                           }
                                       }
                                   }
                                   post {
                                       success {
                                           echo 'Deploy realizado com sucesso!'
                                       }
                                       failure {
                                           echo 'Houve um erro durante o deploy.'
                                       }
                                   }
                               }