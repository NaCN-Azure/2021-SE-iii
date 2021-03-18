FROM openjdk:8u262-jre-slim-buster
ADD ./target/service_coin-0.0.1-SNAPSHOT.jar /backend-coin/backend-coin.jar
ADD runboot.sh /backend-coin/
WORKDIR /backend-coin
RUN chmod a+x runboot.sh
CMD /backend-coin/runboot.sh