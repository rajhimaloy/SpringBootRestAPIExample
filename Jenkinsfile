pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "myrepo/SpringBootRestAPIExample:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/rajhimaloy/SpringBootRestAPIExample.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $DOCKER_IMAGE ."
            }
        }

        stage('Run Docker Container') {
            steps {
                sh "docker stop SpringBootRestAPIExample || true"
                sh "docker rm SpringBootRestAPIExample || true"
                sh "docker run -d --name SpringBootRestAPIExample -p 8080:8080 $DOCKER_IMAGE"
            }
        }

        stage('Push to Docker Hub') {
            steps {
                sh "docker login -u your_dockerhub_username -p your_password"
                sh "docker tag $DOCKER_IMAGE your_dockerhub_username/SpringBootRestAPIExample:latest"
                sh "docker push your_dockerhub_username/SpringBootRestAPIExample:latest"
            }
        }
    }
}