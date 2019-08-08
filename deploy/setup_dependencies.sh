#!/bin/bash

# Installing dependencies for the Icarus simulation
sudo yum -y install java-1.8.0-openjdk.x86_64
sudo yum -y install mysql57-server
sudo service mysqld start
sudo yum -y install aws-cli \
    libcurl-devel \
    unixODBC \
    unixODBC-devel \
    mysql-devel \
    tmux \
    git
