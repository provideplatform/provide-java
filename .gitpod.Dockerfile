FROM gitpod/workspace-full

RUN mkdir /workspace/talend
RUN sudo apt-get install systemd
RUN sudo systemctl enable docker
RUN sudo systemctl start docker 
RUN sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
ADD remote-engine-gen-2-2020-12.tar.gz /talend
