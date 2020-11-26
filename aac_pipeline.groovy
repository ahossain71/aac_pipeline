//Declarative pipeline
pipeline {
  agent any
  stages {
    stage('checkout'){ 
      steps {
        git branch: 'main', url: 'https://github.com/ahossain71/aac_pipeline.git'
        }
    }
    //stage('build souce'){
    //  steps{
        //mvn package
    //  }
    //}
  }
}