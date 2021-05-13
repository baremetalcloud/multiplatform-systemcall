pipeline {
    agent any
    options {
        disableConcurrentBuilds()
    }
    tools {
        gradle 'gradle-7.0'
        jdk 'jdk-11.0.9'
    }
    environment {
        SONATYPE_CREDS = credentials('sonatype')
    }
    stages { 
        stage('build') {
            steps {
                sh('gradle clean build')
            }
        }
        stage('publish') {
            steps {
                sh('gradle publish closeAndReleaseRepository')
            }
        }
    }
}