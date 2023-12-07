FROM registry.docimaxvip.com/base/java8-fonts:latest as java
ENV LANG C.UTF-8
WORKDIR /opt/docimax/quality-inspection
COPY ./services /opt/docimax/quality-inspection
EXPOSE 7301
CMD ["/opt/docimax/quality-inspection/bin/quality-inspection-service"]
