FROM openjdk:8u151-jdk

EXPOSE 8000
EXPOSE 8001

RUN mkdir /mmorpgServer

COPY target/ /mmorpgServer/

CMD java -jar /mmorpgServer/mmorpgServer-desktop-headless-0.0.1-SNAPSHOT.jar
