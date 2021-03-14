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
    stage('Copy build to S3') {
        steps{
          withCredentials([sshUserPrivateKey(credentialsId: 'a59a13e3-8e2f-4920-83c9-a49b576e5d58', keyFileVariable: 'myTestKeyPair02')]) {
            sh 'aws s3 cp ./target/training-tomcatweb-integration.war s3://application-pkgs/trainingApp/'
           }//end withCredentials
        }//end steps
    }//end stage
    //stage('Ansible Deploy') {
    //    steps{
    //         withCredentials([sshUserPrivateKey(credentialsId: 'a59a13e3-8e2f-4920-83c9-a49b576e5d58', keyFileVariable: 'myTestKeyPair02')]) {
                //sh 'wget --no-check-certificate --content-disposition https://github.com/ahossain71/aac_pipeline/tree/main/ansible'
                //sh 'curl -LJO https://github.com/ahossain71/aac_pipeline/tree/main/ansible'
                //sh 'wget  https://github.com/ahossain71/aac_pipeline/tree/main/ansible/playbooks/deploy_trainingApp.yml'
    //            sh 'ansible-playbook deploy_trainingApp.yml --user ubuntu --key-file ${myTestKeyPair02}'  
    //        }//end withCredentials
    // }//end steps
    //}//end stage
  }// end stages
}//end pipeline