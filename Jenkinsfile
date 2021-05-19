pipeline {
    agent any

    stages {
        stage('Image Clear'){
	        steps{
	            echo 'Image Clear Stage'
	            sh "if (docker ps | grep coin-fe) then (docker container stop coin-fe && docker container rm coin-fe) fi"
                sh "if (docker images | grep coin-fe) then (docker rmi \$(docker images coin-fe -q)) fi"
	        }
	    }
        stage('Build'){
	        steps{
	            echo 'Build Stage'
	            sh "npm install"
	            sh "npm install d3 --save-dev"
	            sh "yarn install"
	            sh "yarn upgrade"
                sh "npm run build"
	        }
	    }
        stage('Image Build'){
            steps{
                echo 'Image Build Stage'
                sh "docker build . -t coin-fe:${BUILD_ID}"
            }
        }
        stage('Deploy'){
            steps{
                sh "docker run -p 9001:9001 --name coin-fe -v /log:/log -d coin-fe:${BUILD_ID}"
            }
        }
    }
}
