#!/bin/bash
mkdir -p out && javac -d out src/*.java || exit 1
if [ "$1" = "gui" ]; then java -cp out PasswordGeneratorGUI; else java -cp out Main; fi
