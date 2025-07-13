pipeline {
    agent any

    environment {
        // Configurações fixas
        APP_IMAGE = "saas-app"
        APP_CONTAINER = "saas-app-prod"  // Nome do container de produção
        CONTAINER_PORT = "8611"         // Porta interna do container
        HOST_PORT = "8611"              // Porta fixa no host
    }

    stages {
        stage('Build') {
            steps {
                script {
                    docker.build("${APP_IMAGE}:${env.BUILD_ID}")
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // 1. Para e remove o container existente
                    sh """
                        docker stop ${APP_CONTAINER} || true
                        docker rm ${APP_CONTAINER} || true
                    """

                    // 2. Implanta em novo container na porta 8611
                    sh """
                        docker run -d \
                          --name ${APP_CONTAINER} \
                          -p ${HOST_PORT}:${CONTAINER_PORT} \
                          --network my-network \
                          --restart unless-stopped \
                          ${APP_IMAGE}:${env.BUILD_ID}
                    """

                    echo "✅ Aplicação rodando em: http://${env.NODE_IP}:${HOST_PORT}"
                }
            }
        }
    }

    post {
        failure {
            echo "❌ Falha no deploy - Verifique os logs com:"
            echo "docker logs ${APP_CONTAINER}"
        }
    }
}