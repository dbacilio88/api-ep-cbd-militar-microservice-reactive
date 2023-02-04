def repositoryName = 'https://github.com/dbacilio88/api-ep-cbd-militar-microservice-reactive.git'
def getBuildUser = ''
def repositoryBranchApp = ''
def processRepository = ''
def processPusher = ''
def repositoryUser = ''

node {
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

  wrap([$class: 'BuildUser']) {
    getBuildUser = env.BUILD_USER_ID
    echo "BUILD_USER_ID: ${getBuildUser}";
  }

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
}
pipeline {

  agent any

  tools {
    maven '3.6.3'
    jdk '11.0.16'
  }

  environment {
    versionProject = ''
    nameProject = ''
    buildNumber = ''
    publishPath = ''
  }

  stages {
    stage("Initialize") {
      steps {
        echo "JAVA_HOME=${tool '11.0.16'}"
        echo "PATH=${PATH}"
        echo "M2=${M2}"
        echo "MAVEN_HOME=${M2}"
      }
    }

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
  }
}