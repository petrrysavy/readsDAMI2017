#!/bin/bash
# $1 ... velveth command
# $2 ... dataset directory
# $3 ... tmp folder
# $4 ... output target directory
# $5 ... velveth command

TMP_FOLDER=$3
rm -rf $TMP_FOLDER
mkdir -p $TMP_FOLDER

for file in $(find $2/* -maxdepth 0 -type f)
do
    printf "\n\n\n"
    echo "Going to process file : $file";
    echo "Basename : $filename";
    filename=$(basename $file);
    mkdir -p "$TMP_FOLDER/$filename"
    $1 "$TMP_FOLDER/$filename" 9 -short "-$ASSEM_FTYPE" "$file"
    $5 "$TMP_FOLDER/$filename"
    mv $TMP_FOLDER/$filename/contigs.fa $4/$filename
done

rm -rf $TMP_FOLDER
