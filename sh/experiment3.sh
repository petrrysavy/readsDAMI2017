#!/bin/bash

# this file shows experiment that is used for Influenza and Various datasets
# make sure that maven is installed and paths to assembly algorithms are defined

# first load settings
source settings.sh
export ASSEM_FTYPE=fastq

# build the project
cd ..
mvn install

# here is the list of accessions
# tiny dataset to start with
sequences=( AF389115 AF389119 AY260942 AY260945 AY260949 )
outgroup=AF389115
coverage="2 3 5"
readLength="20 30 70"

# influenza dataset
#sequences=( AF389115 AF389119 AY260942 AY260945 AY260949 AY260955 CY011131 CY011135 CY011143 HE584750 J02147 K00423 AM050555 )
#outgroup=AM050555
#coverage="0.1 0.3 0.5 0.7 1 1.5 2 2.5 3 4 5 7 10 15 20 30 40 50 70 100"
#readLength="3 5 10 15 20 25 30 40 50 70 100 150 200 500"

# various dataset
#sequences=( AB073912 AB236320 AM050555 AY884005 D13784 EU376394 FJ560719 GU076451 JN680353 JN998607 M14707 U06714 U46935 U66304 U81989 X05817 Y13051 )
#outgroup=AY884005
#coverage="0.1 0.3 0.5 0.7 1 1.5 2 2.5 3 4 5 7 10 15 20 30 40 50 70 100"
#readLength="3 5 10 15 20 25 30 40 50 70 100 150 200 500"

# hepatitis dataset
#sequences=( AJ132997 AY859526 JN588558 JX227964 FJ821465 EU155216 EU155260 AM910652 AB690461 JX227965 JX227953 JX227952 JX227967 GQ275355 JX227954 JX227955 AB795432 JX227963 JX227958 JX227972 JX227970 JX227979 JX227962 JX227960 DQ480514 EF424629 EF424628 EF424627 EF424626 EF424625 JX826592 HQ852457 HQ852455 HQ852456 HQ852454 JF343785 JF343787 FJ230883 JF343781 FJ230881 JF343793 JF343792 JF343791 JF343788 JF343789 JF343790 JF343782 HQ852467 HQ852463 HQ852458 HQ852468 HQ852465 HQ852469 HQ852460 HQ852461 HQ852466 HQ852464 HQ852459 JN180456 KF693780 KF693776 JN180455 KF693782 KF693778 JN180452 KF693779 JN180459 JN180460 JN180457 JN180458 KF693783 JN180453 JN180454 KF693781 KF693777 JF343783 FJ230882 FJ230884 JF343784 JF343786 HQ852453 )
#outgroup=AJ132997
#coverage="0.1 0.3 0.5 0.7 1 1.5 2 2.5 3 4 5 7 10 15 20 30 40 50 70 100"
#readLength="3 5 10 15 20 25 30 40 50 70 100 150 200 500"

# here we will store experiment data
folder="experiment_$(date +"%N")"
mkdir "$folder"

# create the settings file
echo files "${#sequences[@]}" "$folder/viruses"   >> "$folder/settings.txt"
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
echo "target $folder/bags"                        >> "$folder/settings.txt"
echo "nthreads 7"                                 >> "$folder/settings.txt" # change as you need, ideally #cores-1
echo "timelimit 1440"                             >> "$folder/settings.txt"
echo "bagsFileType FASTQ"                         >> "$folder/settings.txt"
echo "runassembly true"                           >> "$folder/settings.txt"
echo "assemblers abyss edena ssake velvet spades" >> "$folder/settings.txt"
echo "methods 8"                                  >> "$folder/settings.txt"
echo "maxSize"                                    >> "$folder/settings.txt"
echo "sample 2.0 mongeelkan"                      >> "$folder/settings.txt"
echo "sample 2.0 mongeelkanscale"                 >> "$folder/settings.txt"
echo "sample 2.0 margingaps"                      >> "$folder/settings.txt"
echo "sample 2.0 threshold 0.35"                  >> "$folder/settings.txt"
echo "sample 2.0 tripletsMG"                      >> "$folder/settings.txt"
echo "assemblylongest"                            >> "$folder/settings.txt"
echo "assemblybest"                               >> "$folder/settings.txt"
echo "endinput"                                   >> "$folder/settings.txt"

# now download the sequences
mkdir "$folder/viruses"
for f in "${sequences[@]}"; do
    wget -O "$folder/viruses/$f" "http://www.ebi.ac.uk/ena/data/view/$f&display=fasta"
done

# now generate the bags using ART
cd "$folder/viruses"
for cov in $coverage
do
    for rlen in $readLength
    do
        covstr=$(echo $cov | tr '.' '-')
        printf -v bagsdir "bags_%s_%s" $cov $rlen
        mkdir $bagsdir
        seed=1481810410;
        for f in $(find * -maxdepth 0 -type f); do
            echo "$ART_CMD -ss HS25 -i $f -l $rlen -f $cov -o $f -rs $seed"
            $ART_CMD -ss HS25 -i $f -l $rlen -f $cov -o $f -rs $seed
            mv "$f.fq" "$bagsdir/$f"
            rm "$f.aln"
            let "seed += 1"
        done
    done
done
cd "../.."
mkdir "$folder/bags"
for f in $folder/viruses/bags*; do
    mv "$f" "$folder/bags"
done


# and run the experiment
java -cp target/readsJournal-1.0.jar cz.cvut.fel.ida.reads.experiment.RunExperiment < "$folder/settings.txt" #&>/dev/null

# finish with averaging the results
java -cp target/readsJournal-1.0.jar cz.cvut.fel.ida.reads.experiment.GenerateCubes < "$folder/settings.txt"