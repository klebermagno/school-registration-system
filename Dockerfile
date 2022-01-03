FROM openjdk:8-jdk
COPY target/school-registration-system-0.0.1-SNAPSHOT.jar school-registration-system-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["sleep","30"]
ENTRYPOINT ["java","-jar","/school-registration-system-0.0.1-SNAPSHOT.jar"]
