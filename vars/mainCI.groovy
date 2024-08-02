def call() {
  node ( 'ci-server' ) {
    if (env.TAG_NAME ==~ ',*') {
      stage('Build Code'){
        print 'ok'
      }
      stage('Release software'){
        print 'ok'
      }
    } else {
      stage('lint code') {
        print 'ok'
      }

      if (env.BRANCH_NAME !=~ 'main') {
        stage('Run unit test'){
          print 'ok'
        }
        stage('Run integration test'){
          print 'ok'
        }
      }
      stage('Sonar scan code review'){
        print 'ok'
      }
    }
  }
}