FROM openjdk:11.0.3-jdk-stretch

EXPOSE 8000
EXPOSE 8001

RUN mkdir /mmorpgServer

COPY target/ /mmorpgServer/

# Workaround for openJDK
RUN jar xf /mmorpgServer/mmorpgServer-desktop-headless-0.0.1-SNAPSHOT.jar META-INF/MANIFEST.MF

CMD java -jar /mmorpgServer/mmorpgServer-desktop-headless-0.0.1-SNAPSHOT.jar
