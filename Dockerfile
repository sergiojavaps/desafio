FROM tomcat:8.5.16-jre8-alpine

MAINTAINER Sérgio Alexandre "sergio.alexandre1981@gmail.com"

WORKDIR /usr/local/tomcat/webapps/

COPY agibank.war ./

CMD ["catalina.sh","run"]