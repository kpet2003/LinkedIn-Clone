#!/bin/bash

cd ../frontend
NODE_OPTIONS=--openssl-legacy-provider npm run build

cd ..
cp -r frontend/build/* tedi/src/main/resources/static/
cd tedi

mvn spring-boot:run