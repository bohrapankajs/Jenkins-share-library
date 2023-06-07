def sonarchecks() {
    sh "echo Sonarcheck started"
   sh "sonar-scanner -Dsonar.host.url=http://${SONARURL}:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.login=XXXXXXXX ${ARGS}"
   sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONARURL} ${COMPONENT}"
    sh "echo Sonarcheck Check completed $COMPONENT"
}
def mavenSonarchecks() {
    sh "Sonarcheck started"
    sh "sonar-scanner -Dsonar.host.url=http://${SONARURL}:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.login=XXXXXXXX -Dsonar.sources=."
    sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} S{SONARURL} ${COMPONENT}"
    sh "Sonarcheck Check completed $COMPONENT"
}
