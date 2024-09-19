#!/bin/bash

# setup mysql database
mysql -u root -ppassword -h localhost  -p < /home/kon/tedi/tedi/database.sql 

# setup react for the frontend
cd ../frontend
npm update
npm start &

cd ../tedi
mvn spring-boot:run 
