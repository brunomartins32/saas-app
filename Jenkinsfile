pipeline {
    agent any

    environment {
        // Configurações fixas
        APP_IMAGE = "saas-app"
        APP_CONTAINER = "saas-app-prod"  // Nome do container de produção
        CONTAINER_PORT = "8611"         // Porta interna do container (onde sua app roda)
        HOST_PORT = "8611"              // Porta fixa no host que você quer usar
    }

    stages {
        stage('Build') {
            steps {
                script {
                    // Constroi a imagem Docker
                    docker.build("${APP_IMAGE}:${env.BUILD_ID}")
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // 1. Para e remove o container existente (se houver)
                    sh """
                        docker stop ${APP_CONTAINER} || true
                        docker rm ${APP_CONTAINER} || true
                    """

                    // 2. Implanta em um NOVO container na porta 8611
                    sh """
                        docker run -d \
                          --name ${APP_CONTAINER} \
                          -p ${HOST_PORT}:${CONTAINER_PORT} \  # Mapeia 8611→8080
                          --network my-network \  # Rede personalizada (opcional)
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