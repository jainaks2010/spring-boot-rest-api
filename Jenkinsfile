def JOB_NAME = env.JOB_NAME.split('/')[1]
def TAG_NAME = ""
pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }
 
    stages {
        stage('Initialize') {
          echo 'Hello World'
        }
    }
}
