#!/usr/bin/env bash

mvn -DskipTests install
cd demo
mvn exec:java
