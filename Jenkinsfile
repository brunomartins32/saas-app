pipeline {
    agent any // Isso significa que o pipeline rodará em qualquer agente disponível (neste caso, na sua VM)

    environment {
        // Defina o nome da imagem Docker para sua aplicação
        // Certifique-se de que 'seu-usuario-dockerhub' é seu username real do Docker Hub
        // ou um nome de imagem local se você não for enviar para um registry.
        DOCKER_IMAGE_NAME = "saas-app"
        SPRING_BOOT_APP_NAME = "saas-app-dev" //
    }

    stages {
        stage('Checkout do Código') {
            steps {
                // O Jenkins já faz o checkout automático do SCM configurado no job.
                // Este passo é mais para clareza ou para operações Git adicionais.
                echo 'Código clonado do GitHub.'
            }
        }

        stage('Compilar Aplicação e Rodar Testes Unitários') {
            steps {
                // Para Maven:
                sh 'mvn clean install' // O 'install' também roda os testes unitários por padrão

                // Se usar Gradle, descomente e ajuste:
                // sh './gradlew clean build' // O 'build' também roda os testes unitários por padrão
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                script {
                    // Navega para a pasta do projeto onde o Dockerfile está
                    // Se seu Dockerfile não estiver na raiz do projeto, ajuste o 'dir'
                    dir('.') {
                        // Constrói a imagem Docker. A tag inclui o número do build do Jenkins.
                        // O Dockerfile deve estar na raiz do seu projeto.
                        docker.build "${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}"
                    }
                }
            }
        }

        stage('Deploy para Ambiente de Desenvolvimento (Docker Local)') {
            steps {
                script {
                    // Pega a imagem que acabamos de construir
                    def appImage = docker.image("${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}")

                    // Para garantir que o contêiner anterior seja parado e removido
                    // '|| true' evita que o pipeline falhe se o contêiner não existir
                    sh "docker stop ${SPRING_BOOT_APP_NAME} || true"
                    sh "docker rm ${SPRING_BOOT_APP_NAME} || true"

                    // Roda um novo contêiner com a imagem recém-construída
                    // Mapeia a porta 8080 do host (VM) para a porta 8080 do contêiner
                    appImage.run("-d --name ${SPRING_BOOT_APP_NAME} -p 8080:8080")

                    echo "Aplicação ${SPRING_BOOT_APP_NAME} deployada no Docker local na VM."
                    echo "Acesse em http://${env.NODE_IP}:8611 (substitua NODE_IP pelo IP da sua VM)."
                }
            }
        }
    }

    post {
        always {
            // Limpa o workspace do Jenkins após cada build
            cleanWs()
        }
        success {
            echo 'Pipeline executado com sucesso!'
        }
        failure {
            echo 'Pipeline falhou! Verifique os logs.'
        }
    }
}