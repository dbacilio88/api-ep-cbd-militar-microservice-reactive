def repositoryName = 'https://github.com/dbacilio88/api-ep-cbd-militar-microservice-reactive.git'
def getBuildUser = ''
def repositoryBranchApp = ''
def processRepository = ''
def processPusher = ''
def repositoryUser = ''

node {

  echo "PREPARE NODE START";


  properties([
    pipelineTriggers([
      [$class: 'GenericTrigger',
        genericVariables: [
          [key: 'ref', value: '$.ref'],
          [key: 'repository', value: '$.repository'],
          [key: 'pusher', value: '$.pusher'],
          [key: 'committer_name', value: '$.pusher.name'],
          [key: 'commit', value: '$.after'],
        ],
        causeString: '[$committer_name] pushed from branch [$ref] referencing [$commit]',
        token: 'bacsystem',
        printContributedVariables: true,
        printPostContent: true
      ]
    ])
  ])

  echo "PREPARE NODE END";
  echo "PREPARE BUILDER START";
  wrap([$class: 'BuildUser']) {
    getBuildUser = env.BUILD_USER_ID
    echo "BUILD_USER_ID: ${getBuildUser}";
  }
  echo "PREPARE BUILDER END";
  echo "PREPARE WEBHOOK - i";
  stage("Prepare Webhook") {
    deleteDir()
    echo "PREPARE WEBHOOK START";
    if (env.ref) {
      echo "REF: $ref"
    }
    echo "PREPARE WEBHOOK REF";
    if (env.repository) {
      echo "REPOSITORY: $repository"
    }
    echo "PREPARE WEBHOOK REPOSITORY";
    if (env.pusher) {
      echo "PUSHER: $pusher"
    }
    echo "PREPARE WEBHOOK PUSHER";
    echo "PREPARE WEBHOOK END";
  }
  echo "PREPARE WEBHOOK - e";
  echo "PREPARE WEBHOOK DATA - i";
  stage('Load Webhook Data') {
    script {
      echo "LOAD WEBHOOK DATA SCRIP START";
      echo "LOAD WEBHOOK REF ${env.ref}";
      echo "LOAD WEBHOOK REPOSITORY ${env.repository}";
      echo "LOAD WEBHOOK PUSHER ${env.pusher}";

      if (env.ref && env.repository && env.pusher) {
        echo "READING JSON"
        repositoryBranchApp = ref.replaceAll("refs/heads/", "")
        processRepository = readJSON text: repository
        processPusher = readJSON text: pusher
        repositoryName = processRepository.clone_url.replaceAll("https://", "");
        repositoryUser = processPusher.name
        getBuildUser = repositoryUser
        echo "BUILD: ${getBuildUser}";
        echo "JSON: ${processRepository}";
        echo "PUSHER: ${processPusher}";
        echo "USER: ${repositoryUser}";
        echo "EMAIL: ${processRepository.owner.email}";
        echo "BRANCH: ${repositoryBranchApp}";
        echo "REPOSITORY: ${repositoryName}";
      } else {
        echo "LOAD WEBHOOK DATA SCRIP ELSE";
      }

      echo "LOAD WEBHOOK DATA SCRIP END";
    }
  }
  echo "PREPARE WEBHOOK DATA - e";
}
pipeline {
  echo "PREPARE AGENT - i";
  agent any
  echo "PREPARE AGENT - e";
  echo "PREPARE TOOLS - i";
  tools {
    maven '3.6.3'
    jdk '11.0.16'
  }
  echo "PREPARE TOOLS - e";
  echo "PREPARE ENVIRONMENT - i";
  environment {
    versionProject = ''
    nameProject = ''
    buildNumber = ''
    publishPath = ''
  }
  echo "PREPARE ENVIRONMENT - e";
  echo "PREPARE STAGES - I";
  stages {
  echo "PREPARE STAGES - INITIALIZE I";
    stage("Initialize") {
      steps {
        echo "JAVA_HOME=${tool '11.0.16'}"
        echo "PATH=${PATH}"
        echo "M2=${M2}"
        echo "MAVEN_HOME=${M2}"
      }
    }
  echo "PREPARE STAGES - INITIALIZE E";
  echo "PREPARE STAGES - CHECKSUM I";

    stage('Get checksum git') {
      steps {
        deleteDir()
        echo "REPOSITORY BRANCH: ${repositoryBranchApp}"
        echo "REPOSITORY NAME: ${repositoryName}"
        echo "CREDENTIAL OF: ${repositoryUser}"
        echo "CREDENTIAL OF: ${getBuildUser}"
        script {
          try {
            git branch: "${repositoryBranchApp}", url: "${repositoryName}", credentialsId: "${getBuildUser}"
          } catch (err) {
            echo err.getMessage()
            echo "ERROR USER THAT TRIGGERED THE EVENT DOES NOT HAVE CREDENTIALS IN JENKINS."
          }
          buildNumber = sh(returnStdout: true, script: 'git rev-parse HEAD')
        }
        echo "BUILD NUMBER: ${buildNumber}"
      }
    }
  echo "PREPARE STAGES - CHECKSUM I";
  }
}