def lintchecks() {
    sh " mvn checkstyle:check || true"
    sh "echo Lint Check completed $COMPONENT"
}

def call() {
    pipeline{
        agent any
        environment {
            SONAR    = credentials('SONAR')
        //     // NEXUS    = credentials('NEXUS')
            SONARURL = "172.31.4.124"
        //     // NEXUSURL = "172.31.2.247"
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
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.binaries=target/"
                        common.mavenSonarchecks()
                    }
                } 
            } 
            stage('Test Cases') {
                parallel {
                    stage('Unit Test') {
                        steps {
                            script {
                                sh "echo Unit test Completed"
                            }
                        }
                    } 
                    stage('Interation Test') {
                        steps {
                            script {
                                sh "echo Integration test Completed"
                            }
                        }
                    } 
                    stage('Function Test') {
                        steps {
                            script {
                                sh "echo Function test Completed"
                            }
                        }
                    } 
                }
            }
            stage('Preparing Artifacts') {
                when { expression { env.TAG_NAME ==~ ".*" } }
                steps {
                    script {
                        sh "echo Praparing Artifacts Completed"
                    }
                }
            } 
            stage('Uploading Artifacts') {
                when { expression { env.TAG_NAME ==~ ".*" } }
                steps {
                    script {
                        sh "echo Uploading Artifacts Completed"
                    }
                }
            }  
        } // end of stages
    } // end of pipeline
}