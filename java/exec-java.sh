#!/bin/sh

mvn -e exec:java -Dexec.mainClass=sandbox.$*
