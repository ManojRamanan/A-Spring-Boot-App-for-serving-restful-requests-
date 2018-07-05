pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2 -dp 8090:8090 -v /var/run/docker.sock:/var/run/docker.sock ' 
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
              sh 'JENKINS_NODE_COOKIE=dontKillMe nohup java -jar /var/jenkins_home/workspace/transactionmanagementapp/target/transactionmanagement-0.0.1-SNAPSHOT.jar & exit '
            }
        }
    }
}
