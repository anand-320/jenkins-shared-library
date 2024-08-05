def call() {
  node ( 'ci-server' ) {

    stage('CodeCheckout') {

      sh "find ."
      sh "find . | sed -e '1d' |xargs rm -rf"
      if(env.TAG_NAME ==~ ".*") {
        env.branch_name = "refs/tags/${env.TAG_NAME}"
      } else {
        env.branch_name = "${env.BRANCH_NAME}"
      }
      checkout scmGit(
              branches: [[name: "${branch_name}"]],
              userRemoteConfigs: [[url: "https://github.com/anand-320/expense-${component}"]]
      )
    }

    if (env.TAG_NAME ==~ ',*') {
      stage('Build Code'){
        sh 'docker build -t 942136769355.dkr.ecr.us-east-1.amazonaws.com/expense-${component}:${TAG_NAME} .'
      }
      stage('Release software'){
        sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 942136769355.dkr.ecr.us-east-1.amazonaws.com'
        sh 'docker push 942136769355.dkr.ecr.us-east-1.amazonaws.com/expense-${component}:${TAG_NAME} '
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