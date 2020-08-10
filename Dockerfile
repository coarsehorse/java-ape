FROM alpine:edge
MAINTAINER coarsehorse
COPY . /java-ape
RUN apk add --no-cache \
    openjdk11 \
    maven
WORKDIR /java-ape
RUN mvn clean install
WORKDIR /java-ape/target
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "java-ape-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
