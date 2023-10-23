pipeline {
    agent { label 'Jenkins-Agent' }

    tool {
        jdk 'Java17'
        maven 'Gradle-8.4'
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