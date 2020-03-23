class jenkinsfileUtil implements Serializable {

  def buildTimestamp
  def steps
  def script
  def type
  jenkinsfileUtil(steps,script) {
      this.steps = steps
      this.script = script
  }

  def setbuildtimestamp (){
    this.buildTimestamp=steps.sh(script: "date '+%Y%m%d%H%M%S'", returnStdout: true).trim()
     steps.echo "buildTimestamp: ${buildTimestamp}"
    
  }

/*
  * This method compile  maven projects
  */
  def buildMaven(String buildParameters = '') {
       steps.sh "mvn --fail-never -DskipITs ${buildParameters} package"
  }
  
  def deployMaven (){
  steps.sh " docker build -t my-java-app ."
  steps.sh " docker rm -f my-running-app "
  steps.sh "docker run -d -p 9090:9090 --name my-running-app my-java-app"

  }



}
