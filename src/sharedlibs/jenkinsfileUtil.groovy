class jenkinsfileUtil implements Serializable {

  
  def mvnHome= "usr"
  /*
  * Get information from the main pipeline
  */
  def steps
  def script
  //def docker
  def type
  jenkinsfileUtil(steps,script,String type = '') {
    /*  def remoteConfigs=script.scm.getUserRemoteConfigs()
      for (Object remoteConfig : remoteConfigs) {
        currentCredentialsId= remoteConfig.getCredentialsId()
        steps.echo "CurrentCredentialsId for ${remoteConfig} --> ${currentCredentialsId}"
      }
*/
      this.steps = steps
      this.script = script
      steps.echo "steps: ${steps}"
      steps.echo "script: ${script}"
    //  docker=new Docker(this.script)
      this.type=type
  }


/*
  * This method compile the doce from maven
  */
  def buildMaven(String buildParameters = '', boolean failNever=true) {
     // Run the maven build
     steps.sh "'${mvnHome}/bin/mvn' compile test-compile"
     def failNeverParam=""
     if(failNever){
         failNeverParam="--fail-never"
     }
     steps.sh "'${mvnHome}/bin/mvn' ${failNeverParam} -DskipITs ${buildParameters} install"
     try {
         steps.step( [ $class: 'JacocoPublisher' ] )
     }
     catch(Exception e) {
         //TODO - To be removed on the new version of plugin 2.2.1 of jacoco, verson 2.2.0 had issues with gitlba plugin
     }
     try {
         steps.junit allowEmptyResults: true, testResults: '**/**/target/surefire-reports/TEST-*.xml'
     }catch(Exception e){
        //TODO: Remove when QA are implemented
     }
  }



}
