// Declarative pipeline
pipeline {
  agent any
  tools
    {
       maven "Maven 3.6.3"
    }
  stages {
      stage('checkout_application'){ 
        steps {
          git branch: 'main', url: 'https://github.com/ahossain71/trainingApp.git'
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
        steps {
            //sh "ansible-playbook deploy_trainingApp.yml -i inventories/dev/hosts:trainingweb -- user jenkins --key-file ~/.ssh/DevOpsKeyPair"
            sh "ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml -i $workspace/inventories/dev/hosts.ini --user ec2-user --key-file ~/.ssh/DevOpsKeyPair"
 
        }
    }

  }// end stages
}//end pipeline