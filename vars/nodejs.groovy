

def call() {
    node {
        git branch: 'main', url: "https://github.com/bohrapankajs/${COMPONENT}.git"
        env.APPTYPE="nodejs"
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()   
        common.testCases()
        env.SONARURL = "172.31.0.59"
        env.NEXUSURL = "172.31.92.32"
        if(env.TAG_NAME != null ) {
            common.artifacts()
        }


    }
}

// Pipeline program
    // pipeline{
    //     agent any
    //     environment {
    //         SONAR = credentials('SONAR')
    //         // NEXUS    = credentials('NEXUS')
    //         SONARURL = "172.31.4.124"
    //         // // NEXUSURL = "172.31.2.247"
    //     }
    //     stages {
    //         stage('Lint Check') {
    //             steps {
    //                 script {
    //                     lintchecks()
    //                 }
    //             } 
    //         }  
    //         stage('Sonat Check') {
    //             steps {
    //                 script {
    //                     env.ARGS="-Dsonar.sources=."
    //                     common.sonarchecks()
    //                 }
    //             } 
    //         }
    //         stage('Test Cases') {
    //             parallel {
    //                 stage('Unit Test') {
    //                     steps {
    //                         script {
    //                             sh "echo Unit test Completed"
    //                         }
    //                     }
    //                 } 
    //                 stage('Interation Test') {
    //                     steps {
    //                         script {
    //                             sh "echo Integration test Completed"
    //                         }
    //                     }
    //                 } 
    //                 stage('Function Test') {
    //                     steps {
    //                         script {
    //                             sh "echo Function test Completed"
    //                         }
    //                     }
    //                 } 
    //             }
    //         }
    //         stage('Preparing Artifacts') {
    //             stage('checck artifacts') {
    //                 env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip ||  true' )
    //                 print UPLOAD_STATUS
    //             }
    //             if(env.UPLOAD_STATUS == "") {
    //                 stage('Preparing the artifacts') {
    //                    sh '''
    //                    npm install
    //                    zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
    //                    '''
    //                 }
    //             }
    //         } 
    //         stage('Uploading Artifacts') {
    //             withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {              
    //                              sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUSURL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"  
    //                     }
    //         }
    //         // stage('XYZ Check') {
    //         //     steps {
    //         //         script {
    //         //             sh "echo xyz checks"
    //         //         }
    //         //     } 
    //         // } 
    //     } // end of stages
    // } // end of pipeline
}