pipeline {
    agent {
        docker {
            image 'openjdk/java:8' 
            args '-v /var/run/docker.sock:/var/run/docker.sock ' 
        }
    }
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
            	sh 'docker run -v /root/.m2:/root/.m2 -p 8030:8030 -t springio/gs-spring-boot-docker'
        }
    }
}
}
