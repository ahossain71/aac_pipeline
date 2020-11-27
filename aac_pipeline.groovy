//Declarative pipeline
pipeline {
  agent any
  stages {
    stage('checkout_application'){ 
      steps {
        git branch: 'main', url: 'https://github.com/ahossain71/trainingApp.git'
        }
    }
    //stage('build souce'){
    //  steps{
        //mvn package
    //  }
    //}
  }
}