FROM openjdk:11-jre-slim
ENV TZ=America/Lima
ADD /*-microservice-*.jar app.jar
EXPOSE 8080
ENTRYPOINT["sh", "-c"]
CMD["exec java -XshowSettings:vm \
               -Duser.timezone=America/Lima \
               -Dspring.profile.active=$PROFILE \
                -jar app.jar"]
