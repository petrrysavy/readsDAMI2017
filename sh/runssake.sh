#!/bin/bash
# $1 ... command
# $2 ... dataset directory
# $3 ... tmp folder
# $4 ... output target directory

TMP_FOLDER=$3
rm -rf $TMP_FOLDER
mkdir -p $TMP_FOLDER

# run the assembly
for file in $(find $2/* -maxdepth 0 -type f)
do
    printf "\n\n\n"
    echo "Going to process file : $file";
    filename=$(basename $file);
    echo "$filename";
    $1 -f "$file" -w 1 -p 0 -m 16 -b "$TMP_FOLDER/$filename"
done

# copy the results
cp $TMP_FOLDER/*.contigs $4/
rm -rf $TMP_FOLDER

# clean up filenames
for file in $(find $4/* -maxdepth 0 -type f)
 do
    mv -- "$file" "${file%%.contigs}"
done
