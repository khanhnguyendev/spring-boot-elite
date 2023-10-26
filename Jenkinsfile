pipeline {
    agent { label 'Jenkins-Agent' }

    environment {
        AWS_CREDENTIALS_ID = 'aws_khanhnguyendev'
        AWS_DEFAULT_REGION = 'ap-northeast-1'
        AWS_ECR_REPO = '028527707779.dkr.ecr.ap-northeast-1.amazonaws.com/spring-boot-elite'
    }

    tools {
        jdk 'Java17'
        gradle 'Gradle-8.3'
    }

    stages {
        stage("Cleanup Worksapce") {
            steps {
                cleanWs()
            }
        }

        stage("Checkout from SCM") {
            steps {
                git branch: 'deploy-k8s-using-jenkins', credentialsId: 'github', url: 'https://github.com/khanhnguyendev/spring-boot-elite'
            }
        }

        stage("Login to ECR") {
            steps {
                script {
                    withCredentials([[
                        $class: 'AmazonWebServicesCredentialsBinding',
                        accessKeyVariable: 'AWS_ACCESS_KEY_ID',
                        credentialsId: AWS_CREDENTIALS_ID,
                        secretKeyVariable: 'AWS_SECRET_ACCESS_KEY'
                    ]]) {
                        sh "aws ecr get-login-password --region $AWS_DEFAULT_REGION | docker login --username AWS --password-stdin $AWS_ECR_REPO"
                    }
                }
            }
        }

        stage("Build & Push Docker Image") {
            steps {
                script {
                    sh "./gradlew main-service:jib"
                }
            }
        }
    }
}