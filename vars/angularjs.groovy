def call() {
    node {
        git branch: 'main', url: "https://github.com/bohrapankajs/${COMPONENT}.git"
        env.APPTYPE="angularjs"
        common.lintChecks()
        env.ARGS="-Dsonar.java.binaries=target/"
        common.sonarChecks()   
        common.testCases()
        env.SONARURL = "172.31.0.59"
        env.NEXUSURL = "172.31.2.247"
        if(env.TAG_NAME != null ) {
            common.artifacts()
        }


    }
}