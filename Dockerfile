# Używamy obrazu Javy jako podstawy
FROM openjdk:21-jdk

# Kopiujemy plik JAR aplikacji do kontenera
COPY build/libs/ClothesFactoryBackend-0.0.1-SNAPSHOT.jar app.jar


# Eksponujemy port, na którym działa aplikacja
EXPOSE 8080

# Definiujemy polecenie uruchamiające aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]
