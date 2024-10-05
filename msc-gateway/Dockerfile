FROM --platform=linux/amd64 openjdk:17-alpine as builder

WORKDIR /app/msvc-gateway

# Copiamos los siguientes directorios dentro de la carpeta app, la carepta ./.mvn y la carpeta raiz
COPY ./pom.xml /app
COPY ./msc-gateway/.mvn ./.mvn
COPY ./msc-gateway/mvnw .
COPY ./msc-gateway/pom.xml .

# No compila el test ni tampoco ejecuta el test y aplica lo mismo para el main
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# Otra alternativa que hace lo mismo de arriba
RUN ./mvnw dependency:go-offline

# Copiamos el codigo del microservicio usuario dentro del folder src de la carpeta raiz
COPY ./msc-gateway/src ./src

RUN ./mvnw clean package -DskipTests

FROM --platform=linux/amd64 openjdk:17-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-gateway/target/msc-gateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8090

CMD ["java", "-jar", "msc-gateway-0.0.1-SNAPSHOT.jar"]