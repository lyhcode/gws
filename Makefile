makekey:
	keytool -genkey -alias gwskey -validity 365

deploy:
	gradle jar
	
	rm -rf lib-signed
	#cp -rf lib lib-signed
	mkdir lib-signed
	cp build/libs/*.jar lib-signed
	
	read -p "Storepass: " storepass
	find lib-signed -name "*.jar" -exec jarsigner -storepass $storepass {} gwskey \; -print
	
	#s3cmd sync lib-signed/* s3://s3.copad.cc/lib/
	#rm -rf lib-signed
