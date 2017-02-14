#!/bin/bash

# redefine paths as you need

# paths to assembly algorithms
ABYSS_CMD=abyss-pe
EDENA_CMD=/opt/EdenaV3.131028/bin/edena
SPADES_CMD=/opt/SPAdes-3.9.0-Linux/bin/spades.py
SSAKE_CMD=/opt/ssake_v3.8.4/SSAKE
VELVETH_CMD=/opt/velvet_1.2.10/velveth
VELVETG_CMD=/opt/velvet_1.2.10/velvetg

# path to ART used for generating reads
ART_CMD=/opt/art_bin_MountRainier/art_illumina

# path to human reference genome
# you can download it by
# wget ftp://ftp.ensembl.org/pub/release-86/fasta/homo_sapiens/dna/Homo_sapiens.GRCh38.dna.primary_assembly.fa.gz
# then unzipping
# gunzip Homo_sapiens.GRCh38.dna.primary_assembly.fa.gz
# and changing the latter path
HUMAN_REF_GENOME=/home/petr/Documents/reads/1000genome/chroms/Homo_sapiens.GRCh38.dna.primary_assembly.fa