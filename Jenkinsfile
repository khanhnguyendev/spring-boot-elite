pipeline {
    agent { label 'Jenkins-Agent' }

    tools {
        jdk 'Java17'
        gradle 'Gradle-8.4'
    }

    environment {
        APP_NAME = "spring-boot-elite-pipeline"
        RELEASE = "1.0.0"
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        IMAGE_NAME = "ngokhanhnguyen97" + "/" + "spring-boot-elite"
        IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
    }

    stages {
        stage("Cleanup Worksapce") {
            steps {
                cleanWs()
            }
        }

        stage("Checkout from SCM") {
            steps {
                git branch: 'main', credentialsId: 'github', url: 'https://github.com/khanhnguyendev/spring-boot-elite'
            }
        }

        stage("Build Application") {
            steps {
                sh "./gradlew main-service:build"
            }
        }

        stage("Login to Dockerhub") {
            steps {
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }

        stage("Build & Push Docker Image") {
            steps {
                script {
                    docker.withRegistry('', DOCKER_PASS) {
                        docker_image = docker.build "${IMAGE_NAME}"
                    }

                    docker.withRegistry('', DOCKER_PASS) {
                        docker_image.push("${IMAGE_TAG}")
                        docker_image.push('latest')
                    }
                }
            }
        }
    }
}