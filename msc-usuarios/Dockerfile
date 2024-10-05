# Argumento para contener el nombre del proyecto
ARG MSVC_MAME=msc-usuarios

FROM --platform=linux/amd64 openjdk:17-alpine as builder

ARG MSVC_MAME

WORKDIR /app/$MSVC_MAME

# Copiamos los siguientes directorios dentro de la carpeta app, la carepta ./.mvn y la carpeta raiz
COPY ./pom.xml /app
COPY ./$MSVC_MAME/.mvn ./.mvn
COPY ./$MSVC_MAME/mvnw .
COPY ./$MSVC_MAME/pom.xml .

# No compila el test ni tampoco ejecuta el test y aplica lo mismo para el main
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

# Otra alternativa que hace lo mismo de arriba
# RUN ./mvnw dependency:go-offline

# Copiamos el codigo del microservicio usuario dentro del folder src de la carpeta raiz
COPY ./$MSVC_MAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM --platform=linux/amd64 openjdk:17-alpine

ARG MSVC_MAME

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MSVC_MAME/target
COPY --from=builder $TARGET_FOLDER/msc-usuarios-0.0.1-SNAPSHOT.jar .
# Argumento para el puerto de la app
ARG PORT_APP=8001
ENV PORT $PORT_APP
EXPOSE $PORT

#CMD sleep 20 && java -jar msc-usuarios-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "msc-usuarios-0.0.1-SNAPSHOT.jar"]