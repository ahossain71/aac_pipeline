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
            //sh "ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ec2-user --key-file ~/.ssh/DevOpsKeyPair"
            //sh "ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ec2-user --key-file cff1d3fe-236f-43ca-8ff5-5f37ec63422d"   
         }
        //steps {
        //    withCredentials([sshUserPrivateKey(credentialsId: mySshKey, keyFileVariable: KEY')]) {
        //        sh "ssh -i ${KEY} 10.10.10.10 -C \'docker run -n my-nginx -p 8080:80 nginx\'"
        //  }
        //}
        steps {
            withCredentials([sshUserPrivateKey(credentialsId: cff1d3fe-236f-43ca-8ff5-5f37ec63422d, keyFileVariable: 'myKEY')]) {
                sh "ansible-playbook ./ansible/playbooks/deploy_trainingApp.yml --user ec2-user --key-file ${myKEY}"   
          }
      }
    }
  }// end stages
}//end pipeline