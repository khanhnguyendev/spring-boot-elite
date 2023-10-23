pipeline {
    agent { label 'Jenkins-Agent' }

    tools {
        jdk 'Java17'
        gradle 'Gradle-8.4'
    }

    environment {
        APP_NAME = "spring-boot-elite-pipeline"
        RELEASE = "1.0.0"
        DOCKER_USER = "ngokhanhnguyen97"
        DOCKER_PASS = 'dockerhub'
        IMAGE_NAME = "${DOCKER_USER}" + "/" + "${APP_NAME}"
        IMAGE_TAG = "${RELEASE}"-${BUILD_NUMBER}
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