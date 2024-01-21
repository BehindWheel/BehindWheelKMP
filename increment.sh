#!/bin/bash
# creating variables builds numbers and release versions
BUILD_NUMBER=$(cat ./app/android/version.properties | head -n 1 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
MINOR_VERSION=$(cat ./app/android/version.properties | head -n 2 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
MAJOR_VERSION=$(cat ./app/android/version.properties | head -n 3 | tail -n 1 | sed -r 's/^[^0-9]*([0-9]+).*/\1/')
BUILD_NUMBER=$((BUILD_NUMBER+1))
#replace build number in the file app/android/version.properties
REPLACE=$((BUILD_NUMBER-1))
sed -i "s/BUILD_VERSION=$REPLACE/BUILD_VERSION=$BUILD_NUMBER/g" ./app/android/version.properties

echo "Release v$MAJOR_VERSION.$MINOR_VERSION.$BUILD_NUMBER" 

#Add var in github env
#echo "{environment_variable_name}={value}" >> $GITHUB_ENV
echo "BUILD_NUMBER=$BUILD_NUMBER" >> $GITHUB_ENV
echo "MINOR_VERSION=$MINOR_VERSION" >> $GITHUB_ENV
echo "MAJOR_VERSION=$MAJOR_VERSION" >> $GITHUB_ENV
