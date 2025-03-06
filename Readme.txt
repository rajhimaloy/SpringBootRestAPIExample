I want to learn CICD using Jenikins, so give me the full step by step example for my below code.
*) Created Spring Boot Project with Maven
*) Created @RestController @RequestMapping("/student") public class StudentController {}
*) Created docker-compose-jemkins.yaml for run jenkins in docker:-
	version: '3.8'
	
	networks:
	micro-app-net:
		name: my-micro-app-net
		driver: bridge
	
	volumes:
	jenkins_home:
	jenkins_docker_certs:
	
	services:
	jenkins:
		image: jenkins/jenkins:lts
		container_name: jenkins
		restart: unless-stopped
		privileged: true
		user: root
		ports:
		- "8181:8181"
		- "50000:50000"
		volumes:
		- jenkins_home:/var/jenkins_home
		- /var/run/docker.sock:/var/run/docker.sock  # Allows Jenkins to communicate with Docker
		environment:
		- JAVA_OPTS=-Djenkins.install.runSetupWizard=false
		networks:
		- micro-app-net
	
	jenkins-docker:
		image: docker:dind
		container_name: jenkins-docker
		privileged: true
		restart: unless-stopped
		environment:
		- DOCKER_TLS_CERTDIR=/certs
		volumes:
		- jenkins_docker_certs:/certs/client
		- jenkins_home:/var/jenkins_home
		networks:
		- micro-app-net
	
	#end of scripts
*) Created Dockerfile:-
	# Use an official Java runtime as a parent image
	FROM openjdk:17-jdk-slim
	
	# Set working directory
	WORKDIR /app
	
	# Copy the JAR file from the target folder
	COPY target/SpringBootRestAPIExample-1.0.0.0.jar app.jar
	
	# Expose the application port
	EXPOSE 8080
	
	# Run the application
	CMD ["java", "-jar", "app.jar"]
*) Created Jenkinsfile:-
	pipeline {
		agent any
	
		environment {
			DOCKER_IMAGE = "engrajibkumerghosh/springbootrestapiexample:SpringBootRestAPIExample-v1.0.0"
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
					sh "docker login -u engrajibkumerghosh -p Hema@2020"
					sh "docker tag $DOCKER_IMAGE engrajibkumerghosh/SpringBootRestAPIExample:latest"
					sh "docker push engrajibkumerghosh/SpringBootRestAPIExample:latest"
				}
			}
		}
	}
*) Created GitHub and DockerHub Repository
	GitHub = https://github.com/rajhimaloy/SpringBootRestAPIExample.git
	DockerHub = engrajibkumerghosh/springbootrestapiexample
	
	
*********************************************ChatGPT Solution***************************************	
Answer:- 
Step 1: Start Jenkins Using Docker Compose
Run Jenkins with Docker-in-Docker (DinD)

    Navigate to the folder containing docker-compose-jenkins.yaml

    Start Jenkins:

docker-compose -f docker-compose-jenkins.yaml up -d

Verify that Jenkins is running:

    docker ps

    You should see jenkins and jenkins-docker containers running.

    Access Jenkins at http://localhost:8181.

Step 2: Unlock Jenkins & Install Plugins

    Get the initial admin password:

    docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword

    Open http://localhost:8181, enter the password, and proceed with Install Suggested Plugins.
    Create an admin user and complete the setup.

Step 3: Configure Jenkins for CI/CD
3.1 Install Required Plugins

Go to Manage Jenkins → Plugins and install:
Go to Manage Jenkins → Plugins → Available Plugins → Search Available Plugins(Pipeline) 

    Pipeline
    Git
    Docker Pipeline
    Maven Integration
    Blue Ocean (Optional, for UI visualization)

3.2 Configure Global Tools

Go to Manage Jenkins → Global Tool Configuration, and add:

    Git: Ensure it's installed.
		Scroll to the Git section.
		If Git is installed on your system, Jenkins should detect it automatically.
		If it's missing, install Git on your host machine and restart Jenkins.
    Maven: If not installed, add it with version 3.8.6 or later.
		Find the Maven section.
		Click "Add Maven" → Set a name like Maven-3.8.6.
		Choose Install automatically (or manually set the path if Maven is installed).
    JDK 17: If required.
		Scroll to JDK settings.
		Click "Add JDK", name it JDK17, and either:
			Install automatically
			Or manually set the path (if JDK is already installed).

3.3 Setup Docker Credentials

    Go to Manage Jenkins → Credentials → System → Global Credentials → Add Credentials
    Add DockerHub credentials:
        Username: engrajibkumerghosh
        Password: Hema@2020
        ID: docker-hub

Step 4: Create a Jenkins Pipeline Job

    Go to Jenkins Dashboard → New Item.
    Select Pipeline, name it SpringBootRestAPIExamplePipeline, and click OK.
    In Pipeline Definition, choose Pipeline script from SCM.
    In SCM, select Git and enter:
        Repository URL: https://github.com/rajhimaloy/SpringBootRestAPIExample.git
        Branch: main
        Script Path: Jenkinsfile
    Save the job.

Step 5: Update the Jenkinsfile

Modify the Jenkinsfile to use the Docker credentials safely:

pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "engrajibkumerghosh/springbootrestapiexample:SpringBootRestAPIExample-v1.0.0"
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
                withDockerRegistry([credentialsId: 'docker-hub', url: '']) {
                    sh "docker tag $DOCKER_IMAGE engrajibkumerghosh/springbootrestapiexample:latest"
                    sh "docker push engrajibkumerghosh/springbootrestapiexample:latest"
                }
            }
        }
    }
}

Step 6: Run the Jenkins Pipeline

    Go to Jenkins Dashboard → SpringBootPipeline.
    Click Build Now.
    Monitor progress in Build History.

Step 7: Verify Deployment

    Check if the container is running:

docker ps

You should see the SpringBootRestAPIExample container.

Test the REST API:

    curl http://localhost:8080/student

    Or open http://localhost:8080/student in a browser.

Step 8: Automate Deployment with Webhooks (Optional)
Setup GitHub Webhook

    Go to your GitHub repository:
    Settings → Webhooks → Add Webhook
    Payload URL: http://your-jenkins-server:8181/github-webhook/
    Content type: application/json
    Select Just the push event.
    Click Add webhook.

Now, every push to GitHub triggers the Jenkins build automatically.
🎯 Summary

✅ Jenkins runs inside Docker
✅ Spring Boot app is containerized and deployed
✅ CI/CD pipeline automates build & deployment
✅ GitHub triggers Jenkins builds

Now, every commit will build, test, and deploy your application seamlessly! 🚀

Let me know if you need any refinements. 😊


***********************************************Troublshoot*******************************
I am getting this error:-
This is not loading :- http://localhost:8181/
PS C:\Development\SpringBoot-Dev-1\SpringBootRestAPIExample> docker-compose -f docker-compose-jenkins.yaml up -d
olete"
time="2025-03-06T10:52:09+08:00" level=warning msg="a network with name my-micro-app-net exists but was not created for project \"springbootrestapiexample\".\nSet `external: true` to use an existing network"
[+] Running 2/2
 ✔ Container jenkins-container         Started                                                                                                           0.9s 
 ✔ Container jenkins-docker-container  Started 

PS C:\Development\SpringBoot-Dev-1\SpringBootRestAPIExample> docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
Error response from daemon: No such container: jenkins
PS C:\Development\SpringBoot-Dev-1\SpringBootRestAPIExample> docker exec jenkins-container cat /var/jenkins_home/secrets/initialAdminPassword
cat: /var/jenkins_home/secrets/initialAdminPassword: No such file or directory
PS C:\Development\SpringBoot-Dev-1\SpringBootRestAPIExample>

Solution is port need to change 8181:8080 Because Jenkins run on 8080 in Docker.

Now Jenkins is running and loading in browser with localhost:8181, but no initial password coming..... see

2025-03-06 03:12:31.727+0000 [id=34]    INFO    jenkins.InitReactorRunner$1#onAttained: Completed initialization
2025-03-06 03:12:31.883+0000 [id=24]    INFO    hudson.lifecycle.Lifecycle#onReady: Jenkins is fully up and running
2025-03-06 03:12:39.780+0000 [id=51]    INFO    h.m.DownloadService$Downloadable#load: Obtained the updated data file for hudson.tasks.Maven.MavenInstaller   
2025-03-06 03:12:39.782+0000 [id=51]    INFO    hudson.util.Retrier#start: Performed the action check updates server successfully at the attempt #1
PS C:\Development\SpringBoot-Dev-1\SpringBootRestAPIExample> docker exec jenkins-container cat /var/jenkins_home/secrets/initialAdminPassword
cat: /var/jenkins_home/secrets/initialAdminPassword: No such file or directory
PS C:\Development\SpringBoot-Dev-1\SpringBootRestAPIExample> 

My current docker-compose-jemkins.yaml is:- 

version: '3.8'

networks:
  #my-micro-app-net:
    #external: true
  micro-app-net:
    name: my-micro-app-net
    driver: bridge

volumes:
  jenkins_home:
  jenkins_docker_certs:

services:
  jenkins:
    image: jenkins/jenkins:lts
    container_name: jenkins-container
    restart: unless-stopped
    privileged: true
    user: root
    ports:
      - "8181:8080"  # Corrected port mapping
      - "50000:50000"
    volumes:
      - jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
    environment:
      - JAVA_OPTS=-Djenkins.install.runSetupWizard=false
    networks:
      #- my-micro-app-net
      - micro-app-net

  jenkins-docker:
    image: docker:dind
    container_name: jenkins-docker-container
    privileged: true
    restart: unless-stopped
    environment:
      - DOCKER_TLS_CERTDIR=/certs
    volumes:
      - jenkins_docker_certs:/certs/client
      - jenkins_home:/var/jenkins_home
    networks:
      #- my-micro-app-net
      - micro-app-net
	  

