def sonarchecks() {
    sh "echo Sonarcheck started"
   sh "sonar-scanner -Dsonar.host.url=http://172.31.4.124:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password ${ARGS}"
   sh "bash -x sonar-quality-gate.sh admin password 172.31.4.124 ${COMPONENT}"
    sh "echo Sonarcheck Check completed $COMPONENT"
}
def mavenSonarchecks() {
    sh "Sonarcheck started"
    sh "sonar-scanner -Dsonar.host.url=http://${SONARURL}:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.login=XXXXXXXX -Dsonar.sources=."
    sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} S{SONARURL} ${COMPONENT}"
    sh "Sonarcheck Check completed $COMPONENT"
}
