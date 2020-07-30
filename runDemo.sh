#!/usr/bin/env bash

mvn -DskipTests=true install
cd demo
mvn exec:java
