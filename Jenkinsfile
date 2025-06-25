pipeline {
    agent any

    tools {
        maven 'Maven_3.9' // Đảm bảo bạn đã add tool name "Maven_3.9" trong Jenkins global config
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/danhthanhf/demo-ci-cd.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Run Demo') {
            steps {
                sh 'java -jar target/*.jar &'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t my-spring-app .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker run -d -p 8082:8080 my-spring-app'
            }
        }
    }
}
