FROM ubuntu

LABEL maintainer="gjeanmart <greg@jeanmart.me>"

# UPDATE + BASIC SOFTWARE
RUN apt-get update && apt-get upgrade -y
RUN apt-get install curl -y

# INSTALL JAVA
RUN apt-get install openjdk-8-jdk -y

# INSTALL CLOJURE
RUN curl -s https://download.clojure.org/install/linux-install-1.10.1.492.sh | bash 
RUN curl -s https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein --output /usr/bin/lein
RUN chmod +x /usr/bin/lein

# INSTALL NGINX
RUN apt-get install nginx curl -y

# COPY SOURCE
WORKDIR /project
COPY . /project

# BUILD PROJECT
RUN lein prod

# COPY BUILD TO NGINX
RUN cp -r /project/resources/public/* /usr/share/nginx/html

# RUN NGINX
EXPOSE 80
CMD ["nginx","-g","daemon off;"]