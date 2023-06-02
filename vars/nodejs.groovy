def lintchecks() {
    sh "Installing Jslint"
    sh "npm i jslint"
    sh "node_modules/jslint/bin/jslint.js server.js || true"
    sh "Lint Check completed $COMPONENT"
}


def call() {
    pipeline{
        agent any
        environment {
            SONAR    = credentials('SONAR')
            // NEXUS    = credentials('NEXUS')
            SONARURL = "172.31.0.59"
            // NEXUSURL = "172.31.2.247"
        }
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
                        env.ARGS="-Dsonar.sources=."
                        common.sonarchecks()
                    }
                } 
            } 
            stage('XYZ Check') {
                steps {
                    script {
                        sh "echo xyz checks"
                    }
                } 
            } 
        } // end of stages
    } // end of pipeline
}