FROM java:8
ADD ./target/service_coin-0.0.1-SNAPSHOT.jar /backend-parent/service_coin.jar
ADD runboot.sh /backend-parent/
WORKDIR /backend-parent
RUN chmod a+x runboot.sh
CMD /backend-parent/runboot.sh