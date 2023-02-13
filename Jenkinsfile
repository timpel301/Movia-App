pipeline {
    agent any
    environment {
        ACRCreds = credentials('acr_creds')
    }
    stages {
        stage('ACR LOGIN + PUSH') {
            steps {
                sh 'docker login devops2022.azurecr.io -u ${ACRCreds_USR} -p ${ACRCreds_PSW}'
                sh "echo Git commit hash: $GIT_COMMIT"
                sh "docker build . -t devops2022.azurecr.io/tech:$GIT_COMMIT"
                sh "docker push devops2022.azurecr.io/tech:$GIT_COMMIT"
            }
        }
        //stage('Deploy nginx on K8S') {
        //    agent {
        //        docker {
        //            image 'alpine/k8s:1.23.16'
        //        }
        //    }
        //    environment {
        //        KUBECONFIG = credentials('k8s_config')
        //    }
        //    steps {
        //        script {
        //            //sh 'kubectl apply -f nginx-namespace.yaml'
        //            //sh 'kubectl apply -f nginx-deployment.yaml -n chrisspacethree'
        //            //sh 'kubectl apply -f nginx-service.yaml -n chrisspacethree'
        //            //sh 'kubectl set image -n chrisspacethree deployment/nginx-deployment nginx=devops2022.azurecr.io/chrisnginx:$GIT_COMMIT'
        //            //sh 'kubectl get service -n chrisspacethree'
        //
        //        }
        //    }
        //}
        stage('git push'){
            agent {
                docker {
                    image 'alpine/k8s:1.23.16'
                }
            }
            steps{
                withCredentials([usernamePassword(credentialsId: 'b1add8da-78ed-4990-a20b-8f166c709c9c', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]){
                    sh("git config user.email 'jenkins@jenkins.com'")
                    sh("git config user.name 'jenkins'")
                    //sh("git pull https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/Brights-DevOps-2022-Script/argocd-team4.git chris")
                    sh("git checkout main")
                    sh("git pull https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/chriskovski/teamtech.git chris")
                    //sh("git status")
                    sh("kustomize edit set image devops2022.azurecr.io/tech:$GIT_COMMIT")
                    sh("git pull https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/chriskovski/teamtech.git chris")
                    //sh("git status")
                    sh("git add kustomization.yaml")
                    //sh("git status")
                    sh("git commit -m 'modified nginx with $GIT_COMMIT'")
                    sh("git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/chriskovski/teamtech.git chris")
                }
            }
        }
    }
}