FROM openjdk:8u262-jre-slim-buster
ADD service/service_coin/target/service_coin-0.0.1-SNAPSHOT.jar /backend-coin/backend-coin.jar
ADD runboot.sh /backend-coin/
ADD service/DomainDefault/chemistry.csv /backend-coin/chemistry.csv
ADD service/DomainDefault/finanial.csv /backend-coin/finanial.csv
ADD service/DomainDefault/movie.csv /backend-coin/movie.csv
ADD service/DomainDefault/pokemon.csv /backend-coin/pokemon.csv
WORKDIR /backend-coin
RUN chmod a+x runboot.sh
CMD /backend-coin/runboot.sh