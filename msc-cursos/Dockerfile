FROM --platform=linux/amd64 openjdk:17-alpine as builder

WORKDIR /app/msvc-cursos

# Copiamos los siguientes directorios dentro de la carpeta app, la carepta ./.mvn y la carpeta raiz
COPY ./pom.xml /app
COPY ./msc-cursos/.mvn ./.mvn
COPY ./msc-cursos/mvnw .
COPY ./msc-cursos/pom.xml .

# No compila el test ni tampoco ejecuta el test y aplica lo mismo para el main
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# Otra alternativa que hace lo mismo de arriba
RUN ./mvnw dependency:go-offline

# Copiamos el codigo del microservicio usuario dentro del folder src de la carpeta raiz
COPY ./msc-cursos/src ./src

RUN ./mvnw clean package -DskipTests

FROM --platform=linux/amd64 openjdk:17-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/msvc-cursos/target/msc-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

CMD ["java", "-jar", "msc-cursos-0.0.1-SNAPSHOT.jar"]