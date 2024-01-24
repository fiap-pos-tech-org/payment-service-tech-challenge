FROM maven:3.9.2-eclipse-temurin-17-alpine@sha256:4d9daa0d5471f52a318df5c4aa9d3ab6d5ade68bb5421a4844090cf5b140fbb2 AS build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM eclipse-temurin:17.0.7_7-jre-focal@sha256:901eeb64e3d1e74d261e82e4158386407b95628eaf723058fb96d4efb9141b88
RUN mkdir /app
RUN apt-get update && apt-get install -y dumb-init
COPY --from=build /app/target/servico-pagamento-*.jar /app/java-application.jar
WORKDIR /app
RUN addgroup --system servico-pagamento-app && useradd -r servico-pagamento-app -g servico-pagamento-app
RUN chown -R servico-pagamento-app:servico-pagamento-app /app
USER servico-pagamento-app
EXPOSE 8080
CMD "dumb-init" "java" "-jar" "java-application.jar"
