FROM openjdk:11-jre-slim
ENV TZ=America/Lima
ADD /*-microservice-*.jar app.jar
EXPOSE 8003
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -XshowSettings:vm \
               -Duser.timezone=America/Lima \
               -Dspring.profile.active=prod \
                -jar app.jar"]
