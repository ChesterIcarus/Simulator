#!/bin/bash

# Installing python3.6
sudo yum -y groupinstall development
sudo yum -y install python-devel MySQL-python
sudo yum -y install zlib-devel openssl-devel libffi-devel python36 python36-devel
python3.6 -m venv env
source env/bin/activate
pip install --upgrade pip
pip install awscli
