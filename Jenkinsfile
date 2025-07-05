pipeline {
    agent any

    tools {
        jdk 'openjdk21'
        maven 'maven3'
    }

    environment {
        JAVA_HOME = "${tool 'openjdk21'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Clonar código') {
            steps {
                git 'https://github.com/Andresr31/DemoDigitalLibrary.git'
            }
        }

        stage('Compilar') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Ejecutar Pruebas') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Empaquetar') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }

        stage('Ejecutar aplicación') {
            steps {
                sh 'nohup java -jar target/*.jar &'
            }
        }
    }

    post {
        success {
            echo '✅ Build exitoso'
        }
        failure {
            echo '❌ Fallo en el pipeline.'
        }
    }
}
