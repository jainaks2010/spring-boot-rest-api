pipeline {
    agent any 
    parameters { 
        string description: 'This is the target name for deployment', name: 'name'
        string description: 'This is the target age for deployment', name: 'age'
    } 
    stages {
        stage("print parameter value") {
            steps {
                script {
                    if (name == '' || name == null) {
                        echo "The name parameter is again empty"
                    }
                    else {
                        echo "we'll deploy on the $name name"
                    }

                    if (age == '' || age == null) {
                        echo "The age parameter is again empty"
                    }
                    else {
                        echo "we'll deploy on the $age age"
                    }
                }
            }
        }
    }
}
