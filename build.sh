#!/bin/bash
scons build_jsca=0
echo '# Clean old sdk'
rm -rf /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/3.4.0/
echo '# Copy zip'
cp ./dist/mobilesdk-3.4.0-osx.zip /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/
echo '# Unzip'
unzip /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/mobilesdk-3.4.0-osx.zip -d /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/
echo '# Copy zip content'
cp -R /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/mobilesdk/osx/3.4.0 /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/3.4.0
echo '# Clean tmp zip content'
rm -rf /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/mobilesdk
rm -rf /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/modules
rm -rf /Users/avestudio/Library/Application\ Support/Titanium/mobilesdk/osx/mobilesdk-3.4.0-osx.zip
