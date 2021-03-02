FROM openjdk:11
ADD /target/website.jar website.jar
EXPOSE 9666
ENTRYPOINT ["java","-jar","/website.jar"]