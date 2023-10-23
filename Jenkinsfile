pipeline {
    agent { label 'Jenkins-Agent' }

    tools {
        jdk 'Java17'
        gradle 'Gradle-8.4'
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
                sh "./gradlew clean"
            }
        }
    }

}