pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = "saas-app"
        CONTAINER_NAME = "saas-app-dev"
        APP_PORT = "8611"    // Porta do container
        HOST_PORT = "8611"   // Porta no host (evita conflito com Jenkins)
    }

    stages {
        stage('Checkout do Código') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/brunomartins32/saas-app.git']]
                ])
            }
        }

        stage('Build e Testes') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Construir Imagem Docker') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE_NAME}:${env.BUILD_ID}")
                }
            }
        }

        stage('Deploy Local') {
            steps {
                script {
                    // Parar e remover container existente
                    sh "docker stop ${CONTAINER_NAME} || true"
                    sh "docker rm ${CONTAINER_NAME} || true"

                    // Iniciar novo container com fallback de porta
                    def port = HOST_PORT
                    def maxRetries = 3

                    while (maxRetries > 0) {
                        try {
                            docker.run(
                                "${DOCKER_IMAGE_NAME}:${env.BUILD_ID}",
                                "--name ${CONTAINER_NAME} -d -p ${port}:${APP_PORT} --restart unless-stopped"
                            )
                            echo "✅ Aplicação rodando na porta ${port}"
                            break
                        } catch (err) {
                            echo "⚠️ Porta ${port} ocupada, tentando próxima..."
                            sh "docker stop ${CONTAINER_NAME} || true"
                            sh "docker rm ${CONTAINER_NAME} || true"
                            port = (port as int) + 1
                            maxRetries--
                        }
                    }

                    if (maxRetries == 0) {
                        error("❌ Todas as portas tentadas estão ocupadas")
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo "🚀 Deploy realizado com sucesso!"
            echo "🔗 Acesse: http://localhost:${port}"
        }
        failure {
            echo "❌ Pipeline falhou - Verifique os logs"
        }
    }
}