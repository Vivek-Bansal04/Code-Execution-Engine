#!/bin/sh
javac TempCode.java

if [ $? -eq 0 ]; then
    java TempCode < input.txt
else
    # If there was a compilation error, print an error message and exit
    echo "Compilation failed. Exiting."
    exit 1
fi
