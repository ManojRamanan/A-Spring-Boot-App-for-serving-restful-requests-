pipeline {
   
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
            	docker {
            		image 'springio/gs-spring-boot-docker' 
            		args '-p 8080:8080 -v /var/run/docker.sock:/var/run/docker.sock ' 
            	}
        }
    }
}
}
