#!/bin/bash
# script for running gene assembly from java code
# as user you need to setup path for various assemblers

TMP_FOLDER=/tmp/reads
# location of the script source - assembly scripts are in the same directory
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo "Running assembly script located in directory : $DIR"
echo "Command : $@";

source $DIR/settings.sh

# now the arguments processing
if [ "$#" -ne 3 ]; then
    echo "Illegal number of arguments.";
    exit;
fi

# prepare directory for the output
OUTPUT="$3/$2"
mkdir -p $OUTPUT
mkdir -p $TMP_FOLDER

# run the assember
if [ "$1" == "abyss" ]; then
    $DIR/runabyss.sh $ABYSS_CMD $3 "$TMP_FOLDER/abyss" $OUTPUT
elif [ "$1" == "edena" ]; then
    $DIR/runedena.sh $EDENA_CMD $3 "$TMP_FOLDER/edena" $OUTPUT
elif [ "$1" == "spades" ]; then
    $DIR/runspades.sh $SPADES_CMD $3 "$TMP_FOLDER/spades" $OUTPUT
elif [ "$1" == "ssake" ]; then
    $DIR/runssake.sh $SSAKE_CMD $3 "$TMP_FOLDER/ssake" $OUTPUT
elif [ "$1" == "velvet" ]; then
    $DIR/runvelvet.sh $VELVETH_CMD $3 "$TMP_FOLDER/velvet" $OUTPUT $VELVETG_CMD
else
    echo "Unknown assebler : $1";
    exit;
fi
