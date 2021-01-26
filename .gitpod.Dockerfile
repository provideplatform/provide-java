FROM gitpod/workspace-full

RUN mkdir ~/talend
RUN sudo service docker start
RUN sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
RUN wget https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
RUN sudo cp minikube-linux-amd64 /usr/local/bin/minikube
RUN sudo chmod 755 /usr/local/bin/minikube
RUN minikube config set driver docker
RUN curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
RUN chmod +x ./kubectl
RUN sudo mv ./kubectl /usr/local/bin/kubectl
ADD remote-engine-gen-2-2020-12.tar.gz ~/talend
