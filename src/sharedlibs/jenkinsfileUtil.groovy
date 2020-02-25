class jenkinsfileUtil implements Serializable {

  
  //def mvnHome= "/usr"
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
  def buildMaven(String buildParameters = '', boolean failNever=false) {
     // Run the maven build
     //steps.sh "'${mvnHome}/bin/mvn' compile test-compile"
    // steps.sh "mvn compile test-compile"
     def failNeverParam=""
     if(failNever){
         failNeverParam="--fail-never"
     }
    // steps.sh "'${mvnHome}/bin/mvn' ${failNeverParam} -DskipITs ${buildParameters} install"
       steps.sh "mvn ${failNeverParam} -DskipITs ${buildParameters} package"
  
  }
  
  def deployMaven (){
   steps.sh "java -jar ./target/*.jar"
  }



}
