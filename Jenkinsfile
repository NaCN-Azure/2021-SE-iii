pipeline {
    agent any

    stages {
        stage('Maven Build') {
            steps{
                echo 'Maven Build and Cobertura Stage'
                sh '/opt/apache-maven-3.6.3/bin/mvn clean -DskipTests=true package'
            }
	    }

	     stage('Jacoco Report') {
                    steps{
                        echo 'Jacoco Stage'
                        sh '/opt/apache-maven-3.6.3/bin/mvn test'
                    }
                }

	    stage('Image Clear'){
	        steps{
	            echo 'Image Clear Stage'
	            sh "if (docker ps -a| grep coin) then (docker container stop coin && docker container rm coin) fi"
                sh "if (docker images | grep coin) then (docker rmi \$(docker images coin -q)) fi"
	        }
	    }

        stage('Image Build'){
            steps{
                echo 'Image Build Stage'
                sh "docker build . -t coin:${BUILD_ID}"
            }
        }

	    stage('Image-user Clear'){
	        steps{
	            echo 'Image-user Clear Stage'
	            sh "mv Dockerfile Dockerfile-default"
	            sh "mv Dockerfile-user Dockerfile"
	            sh "if (docker ps -a| grep coin-user) then (docker container stop coin-user && docker container rm coin-user) fi"
                sh "if (docker images | grep coin-user) then (docker rmi \$(docker images coin-user -q)) fi"
	        }
	    }

        stage('Image-user Build'){
            steps{
                echo 'Image-user Build Stage'
                sh "docker build . -t coin-user:${BUILD_ID}"
            }
        }

        stage('Deploy'){
            steps{
                sh "docker run -p 8002:8002 --name coin -v /log:/log -d coin:${BUILD_ID}"
                sh "docker run -p 8004:8004 --name coin-user -v /log:/log -d coin-user:${BUILD_ID}"
            }
        }
    }
    post {
            success {
                // publish html
                publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: '',
                    reportFiles: 'index.html',
                    reportName: 'Jacoco Report'
                ]
            }
        }
}