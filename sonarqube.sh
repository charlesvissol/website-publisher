#!/bin/bash


mvn clean compile test verify sonar:sonar -Dsonar.projectKey=website-publisher -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_3a2fae7e529a792b86f2dbc8b90d7dc904b293f6 -f "/home/vissol/softs/dev-projects/website-publisher/pom.xml"
 
