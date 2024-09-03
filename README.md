# Apache Paimon Web UI

This repository is web ui for the [Apache Paimon](https://paimon.apache.org/) project.

## About

Apache Paimon is an open source project of [The Apache Software Foundation](https://apache.org/) (ASF).

## build
1. to install nodejs and npm in your environment 
2. cd paimon-web-ui && npm install
3. cd ../ && mvn clean package -Prelease -DskipTests -Drat.skip=true
4. you find apache-paimon-webui-0.1-SNAPSHOT-bin.tar.gz in paimon-web-dist/target