#!/bin/bash

# TODO: Needs comments, soon!!
offset=0
args=( $@ )


cred_idx=$((1-offset))
bucket_idx=$((2-offset))
data_path_idx=$((3-offset))

update_offset() {
    if [ -z $1 ]; then
        ((offset=$offset+1))
    else
        ((offset=$offset+$1))
    fi
}

# Pass aws credentials in via command line argurments from parent script
# The parent should be on different remote, hence explicit passing
# IFS=$'\n'; credentials=($(cat ${args[cred_idx]})); unset IFS;

IFS=$'\n'; credentials=(cat $1); unset IFS;

aws configure set aws_access_key_id ${credentials[0]}
if [ $? -ne 0 ]; then
    echo -e "Maybe you didn't pass aws s3 credentials? Attempting to continue..."
    update_offset
else
    aws configure set aws_secret_access_key ${credentials[1]}
fi

# input_fileobj="${$PWD##*/}"
# aws s3 cp "s3://${args[bucket_idx]}/${args[data_path_idx]}" "${$(echo $PWD)}/${input_fileobj}"
aws s3 cp "s3://$2/$3" $3

# if [ $? -ne 0 ]; then
#     echo -e "Unable to download files --- continue? y/n"
#     read cont
#     if [ $cont != "yes" &&  $cont != "y" ]; then
#         exit 1
#     else
#         update_offset 2
#     fi
# fi
#
# if [ -e $input_fileobj ]; then
#     if [ -d $input_fileobj ]; then
#         continue
#     fi
#     if [ "${input_fileobj##*.}" == "zip" ]; then
#         unzip $input_fileobj
#     fi
# fi
