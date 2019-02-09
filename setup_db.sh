#!/bin/bash
sudo systemctl start docker
docker run --name cm_sign -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres