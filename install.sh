#!/bin/bash

# setup mysql database
mysql -u root -ppassword -h localhost  -p < tedi/database.sql 

# setup react for the frontend
cd  frontend
npm update
npm start &

sleep 10
cd ../tedi
mvn spring-boot:run 
