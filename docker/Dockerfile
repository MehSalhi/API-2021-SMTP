FROM alpine:latest AS builder

#Get APK up to date
RUN apk update && apk upgrade

#Install Maven
RUN apk add maven

#Git
RUN apk add git
RUN mkdir /MockMock
RUN git clone https://github.com/HEIGVD-Course-API/MockMock.git /MockMock

#Build
RUN mvn -f /MockMock clean package install
# checkout

# Build release image
FROM alpine:latest AS app

#Copy result
WORKDIR /Executables
COPY --from=builder /MockMock/target/MockMock-1.4.0.one-jar.jar .

RUN apk update && apk add --no-cache openjdk11-jre

EXPOSE 25000
EXPOSE 8282

#Define how to start
ENTRYPOINT ["java", "-jar", "MockMock-1.4.0.one-jar.jar", "-p",  "25000"]
#CMD ["java", "-jar", "-p 25000", "MockMock-1.4.0.one-jar.jar"]



