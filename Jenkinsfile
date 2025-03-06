pipeline {
    /* agent {
            docker {
                image 'maven:3.9.9'  // Use Maven Docker image
            }
    } */
    agent any

    environment {
        //DOCKER_IMAGE = "engrajibkumerghosh/springbootrestapiexample:SpringBootRestAPIExample-v1.0.0"
        DOCKER_HOST = "tcp://jenkins-docker-container:2375"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/rajhimaloy/SpringBootRestAPIExample.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                //sh "docker build -t $DOCKER_IMAGE ."
                sh 'docker build -t engrajibkumerghosh/springbootrestapiexample:SpringBootRestAPIExample-v1.0.0 .'
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
                sh "docker login -u engrajibkumerghosh -p Hema@2020"
                sh "docker tag $DOCKER_IMAGE engrajibkumerghosh/SpringBootRestAPIExample:latest"
                sh "docker push engrajibkumerghosh/SpringBootRestAPIExample:latest"
            }
        }
    }
}
