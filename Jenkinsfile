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
          [key: 'commit', value: '$.after']
        ],
        causeString: '[$committer_name] pushed from branch [$ref] referencing [$commit]',
        token: 'api-ep-cbd-militar-microservice-reactive',
        printContributedVariables: true,
        printPostContent: true
      ]
    ])
  ])

  wrap([$class: 'BuildUser']) {
    getBuildUser = env.BUILD_USER_ID
  }

  stage("Prepare Webhook") {
    deleteDir()
    if (env.ref) {
      echo "REF: $ref"
    }
    if (env.repository) {
      echo "REPOSITORY: $repository"
    }
    if (env.pusher) {
      echo "PUSHER: $pusher"
    }
  }

  stage('Load Webhook Data') {
    script {
      if (env.ref && env.repository && env.pusher) {
        echo "READING JSON"
        repositoryBranchApp = ref.replaceAll("refs/heads/", "")
        processRepository = readJSON text: repository
        processPusher = readJSON text: pusher
        repositoryName = processRepository.clone_url.replaceAll("https://", "")
        repositoryUser = processPusher.name
        getBuildUser = repositoryUser

        echo "USER: ${repositoryUser}";
        echo "EMAIL: ${processRepository.owner.email}";
        echo "BRANCH: ${repositoryBranchApp}";
        echo "REPOSITORY: ${repositoryName}";
      }
    }
  }

  pipeline {
    agent any
    tools {
      maven 'maven_3.6.3'
      jdk 'openjdk11'
    }

    environment {
      versionProject = ''
      nameProject = ''
      buildNumber = ''
      publishPath = ''
      envGitHubCredentials = credentials('github-enterprise-cicd-account')
      envRegistryCredentials = credentials('nexus-cicd-account')
    }

    stages {
      stage("Initialize") {
        steps {
          echo "JAVA_HOME=${tool 'openjdk11'}"
          echo "PATH=${PATH}"
          echo "M2_HOME=${M2_HOME}"
          echo "MAVEN_HOME=${M2_HOME}"
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
              git branch: "${repositoryBranchApp}", url: "https://${repositoryName}", credentialsId: "${getBuildUser}"
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
}