FROM ubuntu

LABEL maintainer="gjeanmart <greg@jeanmart.me>"

# ENV
ENV DEBUG=false
ENV FIREBASE_API_KEY=
ENV FIREBASE_AUTH_DOMAIN=
ENV FIREBASE_DB_URL=
ENV FIREBASE_PROJECT_ID=
ENV FIREBASE_STORAGE_BUCKET=

# UPDATE + BASIC SOFTWARE
RUN apt-get update && apt-get upgrade -y
RUN apt-get install -y curl

# INSTALL NGINX
RUN apt-get install -y nginx

# INSTALL JAVA
RUN apt-get install -y openjdk-8-jdk

# INSTALL NPM
RUN curl -sL https://deb.nodesource.com/setup_11.x  | bash -
RUN apt-get -y install nodejs
RUN npm install

# INSTALL CLOJURE AND CO
RUN curl -s https://download.clojure.org/install/linux-install-1.10.1.492.sh | bash 
RUN curl -s https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein --output /usr/bin/lein
RUN chmod +x /usr/bin/lein
RUN npm install -g shadow-cljs

# INSTALL NGINX
RUN apt-get install nginx -y

# COPY SOURCE
WORKDIR /project
COPY . /project

# PREPARE RUNTIME SCRIPT
RUN chmod +x /project/devops/run.sh

# first run
RUN lein prod

# RUN NGINX
EXPOSE 80
CMD /project/devops/run.sh