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
                    sh 'ln -s /var/jenkins_home/workspace/test/target/transactionmanagement-0.0.1-SNAPSHOT.jar /etc/init.d/transaction'
                    sh 'usermod -a -G sudo jenkins'
                    sh 'sudo /etc/init.d/transaction start'
                
            
            }
        }
    }
}
