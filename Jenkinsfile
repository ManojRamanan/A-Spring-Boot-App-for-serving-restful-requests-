 pipeline {

 	agent any
   
    stages {
        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
                sh 'mvn clean install'
            }
        }
        stage('Deploy') {
            steps {
            	sh './mvnw install dockerfile:build'
            	sh '-p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock springio/gs-spring-boot-docker' 
        }
    }
}
}
