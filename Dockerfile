FROM ubuntu:latest
LABEL authors="ramson"

ENTRYPOINT ["top", "-b"]