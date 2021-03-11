// Declarative pipeline
pipeline {
  agent any
  tools
    {
       maven "Maven"
    }
  stages {
     /* stage('checkout_application'){ 
        steps {
          git branch: 'main', url: 'https://github.com/ahossain71/trainingApp.git'
          }
      }
    */
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
             withCredentials([sshUserPrivateKey(credentialsId: '669f3c2b-55b1-4d3a-9255-7ad60413dc48', keyFileVariable: 'myTestKeyPair02')]) {
                sh 'ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ubuntu --key-file ${myTestKeyPair02}'  
            }//end withCredentials
      }//end steps
    }//end stage
  }// end stages
}//end pipeline