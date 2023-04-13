docker build --tag=gateway:latest --platform=linux/amd64 .
docker tag gateway:latest aledanna/gateway
docker push aledanna/gateway