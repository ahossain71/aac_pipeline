//Declarative pipeline
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
             withCredentials([sshUserPrivateKey(credentialsId: '2be93d5b-70f9-4db2-adc3-f307b7b86c48', keyFileVariable: 'myKEY')]) {
                sh 'ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ubuntu --key-file ${myKEY}'  
            }//end withCredentials
      }//end steps
    }//end stage
  }// end stages
}//end pipeline