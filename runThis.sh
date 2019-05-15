#!/bin/bash

# Executes slicing app. Can be executed from any location, absolute or relative path - handy for creating Desktop launchers

orig_dir=`pwd`
work_dir=`dirname $0`
cd $work_dir
java -jar ./out/artifacts/Slicing_jar/Slicing.jar
cd $orig_dir

