FROM openjdk:8u151

EXPOSE 8000
EXPOSE 8001

RUN mkdir /mmorpgServer

COPY target/ /mmorpgServer/

ARG JAR_FILE
# Workaround for openJDK
RUN jar xf /mmorpgServer/${JAR_FILE} META-INF/MANIFEST.MF

CMD java -jar /mmorpgServer/${JAR_FILE}
