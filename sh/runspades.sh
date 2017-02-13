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
    echo "Basename : $filename";
    filename=$(basename $file);
    cp $file "$TMP_FOLDER/$filename.fa"
    mkdir -p "$TMP_FOLDER/$filename"
    echo "$1 --sc --only-assembler -s $TMP_FOLDER/$filename.fa -o $TMP_FOLDER/$filename"
    $1 --sc --only-assembler -s "$TMP_FOLDER/$filename.fa" -o "$TMP_FOLDER/$filename"
    mv $TMP_FOLDER/$filename/contigs.fasta $4/$filename
done

rm -rf $TMP_FOLDER
