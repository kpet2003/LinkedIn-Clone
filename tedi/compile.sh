#!/bin/bash

rm -r  /home/kon/tedi/tedi/src/main/resources/static
cd ../frontend
NODE_OPTIONS=--openssl-legacy-provider npm run build


cd ..
mkdir tedi/src/main/resources/static/
cp -r frontend/build/* tedi/src/main/resources/static/
cd tedi


mvn spring-boot:run