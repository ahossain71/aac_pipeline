// Declarative pipeline
pipeline {
  agent any
  tools
    {
       maven "Maven"
    }
  stages {
      stage('checkout_application'){ 
        steps {
          git branch: 'master', url: 'https://github.com/ahossain71/trainingApp.git'
          }
      }
      stage('Tools Init') {
        steps {
            script {
                echo "PATH = ${PATH}"
                echo "M2_HOME = ${M2_HOME}"
            def tfHome = tool name: 'Ansible'
            env.PATH = "${tfHome}:${env.PATH}"
              sh 'ansible --version'
            }
        }
      }
    stage('Execute Maven') {
            steps {
              sh 'mvn package'             
          }
        }
    stage('Ansible Deploy') {
        steps{
             withCredentials([sshUserPrivateKey(credentialsId: 'a59a13e3-8e2f-4920-83c9-a49b576e5d58', keyFileVariable: 'myTestKeyPair02')]) {
                sh 'ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ubuntu --key-file ${myTestKeyPair02}'  
            }//end withCredentials
      }//end steps
    }//end stage
  }// end stages
}//end pipeline