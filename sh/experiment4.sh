#!/bin/bash

# this file shows experiment that is used for Influenza and Various datasets
# make sure that maven is installed and paths to assembly algorithms are defined

# first load settings
source settings.sh
export ASSEM_FTYPE=fastq

# build the project
cd ..
mvn install

# human chromosome dataset
sequences=( chr1 chr2 chr3 chr4 chr5 chr6 chr7 chr8 chr9 chr10 chr11 chr12 chr13 chr14 chr15 chr16 chr17 chr18 chr19 chr20 chr21 chr22 chrX )
outgroup=chrX
coverage="4.32"
readLength="76"

# here we will store experiment data
folder="experiment_$(date +"%N")"
mkdir "$folder"

# create the settings file
echo files "${#sequences[@]}" "$folder/ref"       >> "$folder/settings.txt"
for f in "${sequences[@]}"; do
    echo "$f"                                     >> "$folder/settings.txt"
done
echo "outgroup $outgroup"                         >> "$folder/settings.txt"
echo "coverage $coverage"                         >> "$folder/settings.txt"
echo "readlength $readLength"                     >> "$folder/settings.txt"
echo "cyclic false"                               >> "$folder/settings.txt"
echo "reverse false"                              >> "$folder/settings.txt"
echo "complement true"                            >> "$folder/settings.txt"
echo "seed 42"                                    >> "$folder/settings.txt"
echo "target $folder"                             >> "$folder/settings.txt"
echo "nthreads 7"                                 >> "$folder/settings.txt" # change as you need, ideally #cores-1
echo "timelimit 1440"                             >> "$folder/settings.txt"
echo "bagsFileType FASTQ"                         >> "$folder/settings.txt"
echo "runassembly true"                           >> "$folder/settings.txt"
echo "assemblers abyss edena ssake velvet spades" >> "$folder/settings.txt"
echo "methods 8"                                  >> "$folder/settings.txt"
echo "maxSize"                                    >> "$folder/settings.txt"
echo "mongeelkan"                                 >> "$folder/settings.txt"
echo "mongeelkanscale"                            >> "$folder/settings.txt"
echo "margingaps"                                 >> "$folder/settings.txt"
echo "threshold 0.35"                             >> "$folder/settings.txt"
echo "tripletsMG"                                 >> "$folder/settings.txt"
echo "assemblylongest"                            >> "$folder/settings.txt"
echo "assemblybest"                               >> "$folder/settings.txt"
echo "endinput"                                   >> "$folder/settings.txt"

cd "$folder"

# get the reference
mkdir ref
cd ref
bedtools getfasta -fi $HUMAN_REF_GENOME -bed ../../sh/chromosomes.bed -fo "tmp.fa" -name
awk '/^>chr/ {OUT=substr($0,2) "";print " ">OUT}; OUT{print >OUT}' tmp.fa
for FILE in *; do tail -n +2 "$FILE" > "$FILE.tmp" && mv "$FILE.tmp" "$FILE"; done

# now download the reads
cd ..
mkdir bags_4-32_76
cd bags_4-32_76
while read line;
do
    chr=$(echo $line | cut -d' ' -f1);
    start=$(echo $line | cut -d' ' -f2);
    end=$(echo $line | cut -d' ' -f3);
    name=$(echo $line | cut -d' ' -f4);
    echo $chr  $start $end $name;
    samtools view -h ftp://ftp.1000genomes.ebi.ac.uk/vol1/ftp/phase1/data/HG00155/alignment/HG00155.mapped.ILLUMINA.bwa.GBR.low_coverage.20101123.bam $chr:$start-$end > $name.sam
    samtools view -Sb $name.sam > $name.bam
    bamToFastq -i $name.bam -fq $name
done < ../../sh/chromosomes.bed ;
rm *.bam
rm *.sam

cd ../..

# and run the experiment
#TODO change it to the recently build code
java -cp ~/Dropbox/doktorske/research/reads/code/reads/dist/reads.jar rysavpe1.reads.experiment2.RunExperiment < "$folder/settings.txt" #&>/dev/null

# finish with averaging the results
java -cp ~/Dropbox/doktorske/research/reads/code/reads/dist/reads.jar rysavpe1.reads.experiment2.cubes.GenerateCubes < "$folder/settings.txt"