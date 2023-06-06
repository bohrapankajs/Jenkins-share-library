def lintchecks() {
    sh " mvn checkstyle:check || true"
    sh "echo Lint Check completed $COMPONENT"
}

def call() {
    pipeline{
        agent any
        // environment {
        //     SONAR    = credentials('SONAR')
        //     // NEXUS    = credentials('NEXUS')
        //     SONARURL = "172.31.0.59"
        //     // NEXUSURL = "172.31.2.247"
        // }
        stages {
            stage('Lint Check') {
                steps {
                    script {
                        lintchecks()
                    }

                } 
            }  
            stage('Sonat Check') {
                steps {
                    script {
                        sh "mvn clean compile"
                        // env.ARGS="-Dsonar.java.binaries=target/"
                        common.sonarchecks()
                    }
                } 
            } 
            // stage('XYZ Check') {
            //     steps {
            //         script {
            //             sh "echo xyz checks"
            //         }
            //     } 
            // } 
        } // end of stages
    } // end of pipeline
}