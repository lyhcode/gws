makekey:
	keytool -genkey -alias gwskey -validity 365

deploy:
	gradle jar
	cp -f build/libs/*.jar lib

signjar:
	#find lib-signed -name "*.jar" -exec jarsigner -storepass $storepass {} gwskey \; -print
	rm -rf lib-signed
	cp -rf lib lib-signed
	read -p "password? " storepass; \
	find lib-signed -name "*.jar" -exec jarsigner -storepass "$$storepass" {} gwskey \; -print
	
	#s3cmd sync lib-signed/* s3://s3.copad.cc/lib/
	#rm -rf lib-signed
