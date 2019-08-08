#!/bin/bash


# NOTE: The paths for the file for the config should be relative to the directory
#       you are running the bash script from. The other files are relative to the
#       directory the config is located in. Not applicable when using absolute path
icarus_flags=( "-config simulation/MATSim_input/configs/chrisConfig.xml" \
    "-outputdir /home/ec2-user/output" \
    "-network ../networks/arizona_matsim_from_osm.xml" \
    "-plans ../plans/only_car_plans_from_mag.xml")

s3_bucket="icarus-simulation-data"
s3_input_data="0.3-CAR_ONLY_CHRIS/input.zip"
s3_output_data="0.3-CAR_ONLY_CHRIS/output.zip"
icarus_jar="icarus.jar"

# The below are required to handle setting up a blank ec2 instance.
# Python is a requirement for using the AWS s3 CLI
aws_cred=".credentials"
aws_user="ec2-user"
get_dependencies="setup_dependencies.sh"
get_python="setup_python.sh"
get_data="setup_data.sh"
get_simulation="setup_simulation_1x130gb.sh"


echo "Enter the address of a currently running AWS instance which you'd like to run an"
echo "instance of the Icarus simulation, followed by [ENTER]:"

read aws_url

if [ -z "${AWS_SSH_AUTH_KEY}" ]; then
    echo "Must set environment variable AWS_SSH_AUTH_KEY with RSA valid key for given instance"
    exit
fi

# Uploading the following files first to the AWS instance

upload_files=($get_dependencies $get_python $get_data $get_simulation $aws_cred)
# These files should do everything we need for a simulation
for file in "${upload_files[@]}"; do
    echo "$(echo $PWD)/${file}"
    if [ -f "$(echo $PWD)/${file}" ]; then
        scp -o "StrictHostKeyChecking=no" -i $AWS_SSH_AUTH_KEY $file \
            "${aws_user}@${aws_url}:/home/${aws_user}/${file##*/}"
    else
        exit 1
    fi
done

icarus_outputdir=""
for flag in "${icarus_flags[@]}"; do
    if [[ $flag == *"-outputdir"* ]]; then
        outputflag=( $flag )
        icarus_outputdir=${outputflag[1]}
    fi
done
if [ $icarus_outputdir == "" ]; then
    icarus_outputdir="output"
    icarus_flags=( "${icarus_flags[@]}" "-outputdir ${icarus_outputdir}")
fi

function join { local IFS=" "; echo "$*"; }
icarus_args=$(join ${icarus_flags[@]})

# Add this line to the following command, directly before the call to ${get_simulation} to assist in debugging
# echo "${get_simulation} ${s3_bucket} ${s3_output_data} ${icarus_jar} ${outputdir} ${icarus_flags[@]}" > icarus_flags.out

ssh -tt "-o StrictHostKeyChecking no" \
    -i $AWS_SSH_AUTH_KEY "${aws_user}@${aws_url}" /bin/bash << EOF
sudo chmod +rwx *
sudo /bin/bash ${get_dependencies} ${get_python}
sudo /bin/bash ${get_data} ${aws_cred} ${s3_bucket} ${s3_input_data}
sudo /bin/bash ${get_simulation} ${s3_bucket} ${s3_output_data} ${icarus_jar} ${outputdir} ${icarus_flags[@]}
EOF
