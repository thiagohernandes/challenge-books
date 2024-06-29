FROM azul/zulu-openjdk:21

ADD ./target/application.jar /app/
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=90", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF8", "-Duser.timezone=America/Sao_Paulo", "-jar", "/app/application.jar"]
EXPOSE 8090/tcp