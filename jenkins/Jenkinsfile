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
                withCredentials([usernamePassword(credentialsId: '2eb747c4-f19f-4601-ab83-359462e62482', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]){
                    sh("git config user.email 'jenkins@jenkins.com'")
                    sh("git config user.name 'jenkins'")
                    //sh("git pull https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/Brights-DevOps-2022-Script/argocd-team4.git chris")
                    sh("git checkout chris")
                    sh("git pull https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/Brights-DevOps-2022-Script/teamtech.git chris")
                    //sh("git status")
                    sh("kustomize edit set image devops2022.azurecr.io/tech:$GIT_COMMIT")
                    sh("git pull https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/Brights-DevOps-2022-Script/teamtech.git chris")
                    //sh("git status")
                    sh("git add nginx-deployment.yaml nginx-service.yaml kustomization.yaml")
                    //sh("git status")
                    sh("git commit -m 'modified nginx with $GIT_COMMIT'")
                    sh("git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/Brights-DevOps-2022-Script/teamtech.git chris")
                }
            }
        }
    }
}