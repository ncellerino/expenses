#standard image
FROM frolvlad/alpine-oraclejdk8:slim

MAINTAINER Jorge Cellerino <ncellerino@gmail.com>

VOLUME /tmp

#adding service jar and renaming it to app.jar
ADD back-end-0.1.0.jar app.jar

#This ensures a file modification date on the app.jar file
RUN sh -c 'touch /app.jar'


ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]