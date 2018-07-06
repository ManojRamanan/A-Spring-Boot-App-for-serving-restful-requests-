pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2 -dp 8030:8030 -v /var/run/docker.sock:/var/run/docker.sock ' 
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
                    
                    sh 'JENKINS_NODE_COOKIE=dontKillMe mvn spring-boot:run &'
                
            
            }
        }
    }
}
