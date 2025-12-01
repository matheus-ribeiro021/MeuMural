pipeline {
    agent any
    stages {
        stage('Verificar Repositório') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], useRemoteConfigs: [[url: 'https://github.com/matheus-ribeiro021/MeuMural']]])
            }
        }

        stage('Build APK (Simulado)') {
            steps {
                script {
                    // Checkout do repositório do aplicativo React Native em um subdiretório
                    dir('MeuMuralApp') {
                        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/matheus-ribeiro021/MeuMuralApp']]])
                        
                        // Simulação de build do APK (em um ambiente real, usaria 'expo build:android' ou 'eas build')
                        // Como estamos em um ambiente simulado, vamos criar o arquivo APK
                        sh 'mkdir -p android/app/build/outputs/apk/release'
                        sh 'echo "APK_CONTENT_SIMULATED" > android/app/build/outputs/apk/release/app-release.apk'
                        
                        // Definir o caminho do APK para uso posterior (relativo ao workspace)
                        env.APK_PATH = "MeuMuralApp/android/app/build/outputs/apk/release/app-release.apk"
                        
                        // Arquivar o APK para que possa ser baixado na próxima stage
                        archiveArtifacts artifacts: env.APK_PATH, fingerprint: true
                    }
                }
            }
        }

        stage('Copiar APK para o Backend') {
            steps {
                script {
                    // Criar o diretório de destino no backend (assumindo que será copiado para o Docker)
                    sh 'mkdir -p src/main/resources/static/apk'
                    
                    // Copiar o APK do workspace para o diretório do backend
                    sh "cp ${env.APK_PATH} src/main/resources/static/apk/MeuMuralApp.apk"
                }
            }
        }
            steps {
                script {
                    // Checkout do repositório do aplicativo React Native em um subdiretório
                    dir('MeuMuralApp') {
                        checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/matheus-ribeiro021/MeuMuralApp']]])
                        
                        // Simulação de build do APK (em um ambiente real, usaria 'expo build:android' ou 'eas build')
                        // Como estamos em um ambiente simulado, vamos criar o arquivo APK
                        sh 'mkdir -p android/app/build/outputs/apk/release'
                        sh 'echo "APK_CONTENT_SIMULATED" > android/app/build/outputs/apk/release/app-release.apk'
                        
                        // Definir o caminho do APK para uso posterior (relativo ao workspace)
                        env.APK_PATH = "MeuMuralApp/android/app/build/outputs/apk/release/app-release.apk"
                        
                        // Arquivar o APK para que possa ser baixado na próxima stage
                        archiveArtifacts artifacts: env.APK_PATH, fingerprint: true
                    }
                }
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
                    def appName = 'MeuMural'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Construir a imagem Docker
                    sh "docker build -t ${imageTag} ."
                }
            }
        }

        stage('Fazer Deploy') {
            steps {
                script {
                    def appName = 'MeuMural'
                    def imageTag = "${appName}:${env.BUILD_ID}"

                    // Parar e remover o container existente, se houver
            		bat "docker stop ${appName} || exit 0"
            		bat "docker rm -v ${appName} || exit 0"  // Remover o container e os volumes associados

                    // Executar o novo container
                    sh "docker-compose up -d --build"
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