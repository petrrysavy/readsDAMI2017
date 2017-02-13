#!/bin/bash
# $1 ... command
# $2 ... dataset directory
# $3 ... tmp folder
# $4 ... output target directory

TMP_FOLDER=$3
rm -rf $TMP_FOLDER
mkdir -p $TMP_FOLDER

for file in $(find $2/* -maxdepth 0 -type f)
do
    printf "\n\n\n"
    echo "Going to process file : $file";
    filename=$(basename $file);
    echo "Basename : $filename";
    $1 -r "$file" -prefix "$TMP_FOLDER/$filename"
    $1 -e "$TMP_FOLDER/$filename.ovl" -prefix "$TMP_FOLDER/$filename"
done


cp $TMP_FOLDER/*.fasta $4/
rm -rf $TMP_FOLDER
for file in $(find $4/* -maxdepth 0 -type f)
 do
    mv -- "$file" "${file%%_contigs.fasta}"
done