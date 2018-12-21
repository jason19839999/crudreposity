#!/bin/sh

DIR=`dirname $0`
cd $DIR
java -cp .:./config:./lib/* ${start-class} --spring.profiles.active=prod $*