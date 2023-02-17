pipeline {
    agent any
    environment {
        ACRCreds = credentials('acr_creds')
    }
    stages {
        stage('ACR LOGIN & PUSH') {
            steps {
                sh 'docker login devops2022.azurecr.io -u ${ACRCreds_USR} -p ${ACRCreds_PSW}'
                sh "echo Git commit hash: $GIT_COMMIT"
                sh "docker build . -t devops2022.azurecr.io/tech:$GIT_COMMIT"
                sh "docker push devops2022.azurecr.io/tech:$GIT_COMMIT"
                sh "docker build -t devops2022.azurecr.io/teamtechbackend:$GIT_COMMIT ./backend"
                sh "docker push devops2022.azurecr.io/teamtechbackend:$GIT_COMMIT"
            }
        }
        stage('HELM ADD REPO & INSTALL CHART') {
            agent {
                docker {
                    image 'alpine/helm:3.11.1'
                    args '--entrypoint=""'
                    label 'helm'
                    reuseNode true
                    user 'root'
                }
            }
            steps{
                sh('helm repo add prometheus-community https://prometheus-community.github.io/helm-charts')
                sh('helm repo add stable https://kubernetes-charts.storage.googleapis.com/')
                sh('helm repo update')
                sh('helm install prometheus prometheus-community/kube-prometheus-stack')
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
        stage('GIT PULL & PUSH'){
            agent {
                docker {
                    image 'alpine/k8s:1.23.16'
                }
            }
            steps{
                withCredentials([usernamePassword(credentialsId: 'b1add8da-78ed-4990-a20b-8f166c709c9c', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]){
                    sh("git config user.email 'jenkins@jenkins.com'")
                    sh("git config user.name 'jenkins'")
                    sh("git checkout main")
                    sh("git fetch && git merge -X theirs -m 'merge'")
                    //sh("git status")
                    sh("cd ./kustomize && kustomize edit set image devops2022.azurecr.io/tech:$GIT_COMMIT && cd ..")
                    sh("cd ./kustomize && kustomize edit set image devops2022.azurecr.io/teamtechbackend:$GIT_COMMIT && cd ..")
                    //sh("git status")
                    sh("git add kustomize/kustomization.yaml")
                    //sh("git status")
                    sh("git commit -m 'modified frontend and backend with $GIT_COMMIT'")
                    sh("git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/chriskovski/teamtech.git main")
                }
            }
        }
    }
}