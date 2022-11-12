properties([pipelineTriggers([githubPush()])])

pipeline { 
    agent any  
	tools { 
        maven 'Maven 3.8.6' 
        jdk 'Amaon Corretto 19' 
    }
    stages { 
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                ''' 
            }
        }	
       stage ('Build') {
            steps {
                sh 'mvn compile -U' 
            }
        }
       stage ('Package') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true package' 
            }
        }
       stage ('Publish') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=false deploy' 
            }
        }
    }
}