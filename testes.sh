#!/bin/bash

input="1 2 3 4 5 6 7"

for i in ${input[@]}
do
    ./elenco < exemplo$i.txt > saida$i.txt
done