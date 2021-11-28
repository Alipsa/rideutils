#!/usr/bin/env bash

mvn -DskipTests install
cd demo
if [[ -f ~/.sdkman/bin/sdkman-init.sh ]]; then
  source ~/.sdkman/bin/sdkman-init.sh
fi
if command -v jdk11 &> /dev/null; then
  source jdk11
fi
mvn compile exec:java -Dglass.gtk.uiScale=200%
#mvn package
#java -Dglass.gtk.uiScale=200% -jar target/rideutils-demo-1.0-SNAPSHOT-jar-with-dependencies.jar
