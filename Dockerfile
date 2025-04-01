# Używamy obrazu Javy jako podstawy
FROM openjdk:21-jdk-slim

# Instalujemy telnet, aby sprawdzić połączenie
RUN apt-get update && apt-get install -y telnet
RUN apt-get update && apt-get install -y telnet ca-certificates && update-ca-certificates


ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"


# Kopiujemy plik JAR aplikacji do kontenera
COPY build/libs/ClothesFactoryBackend-0.0.1-SNAPSHOT.jar app.jar

# Eksponujemy port, na którym działa aplikacja
EXPOSE 8080 5005

# Definiujemy polecenie uruchamiające aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]
