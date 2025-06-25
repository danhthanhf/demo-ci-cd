pipeline {
    agent any

    tools {
        maven 'Maven_3.9'  // Tên Maven bạn sẽ khai báo trong Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/danhthanhf/demo-ci-cd'
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
