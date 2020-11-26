//Declarative pipeline
pipeline {
  agent any
  stages {
    stage('clone sources') { 
      steps {
        git url: 'https://github.com/ahossain71/aac_pipeline.git'
        }
    }
    stage('build souce') {
      steps{
        mvn package
      }
    }
  }
}