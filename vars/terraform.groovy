def call() {

properties([
  parameters([
     choice(name: 'ENV', choices: 'dev\nprod', description: 'Chose the env to deploy'),
     choice(name: 'ACTION', choices: 'apply\ndestroy', description: 'Chose the action'),
     string(name: 'APP_VERSION', choices: 'APP_VERSION', description: 'Enter the name of the version to deploy')
    
  ])
])

node {
    ansiColor('xterm') {
        git branch: 'main', url: "https://github.com/bohrapankajs/${REPONAME}.git"

        stage('Terraform Init') {
                sh ''' 
                    cd ${TERRAFORM_DIR}
                    terrafile -f env-${ENV}/Terrafile
                    terraform init --backend-config=env-${ENV}/${ENV}-backend.tfvars -no-color
                '''
            }

        stage('Terraform Plan') {

                sh ''' 
                    cd ${TERRAFORM_DIR}
                    terraform plan -var-file=env-${ENV}/${ENV}.tfvars -var APP_VERSION=${APP_VERSION} -no-color
                '''
            }

        stage('Terraform Action') {
                sh ''' 
                    cd ${TERRAFORM_DIR}
                    terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve -var APP_VERSION=${APP_VERSION} -no-color
                ''' 
            }
        }
    }
}

// pipeline {
//     agent any 
//     parameters {
//         choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Select The Environment')
//         choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Select Apply or Destroy')
//     }
//     options {
//         ansiColor('xterm')    // Add's color to the output : Ensure you install AnsiColor Plugin.
//     }
//     stages {
//         stage('Terraform Init') {
//             steps {
//                 sh "terrafile -f env-${ENV}/Terrafile"
//                 sh "terraform init --backend-config=env-${ENV}/${ENV}-backend.tfvars"
//             }
//         }

//         stage('Terraform Plan') {
//             steps {
//                 sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
//             }
//         }

//         stage('Terraform Apply') {
//             steps {
//                 sh "terraform ${ACTION} -var-file=env-${ENV}/${ENV}.tfvars -auto-approve "
//             }
//         }

//     }
// }