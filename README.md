# spoti5
Build process
GitHub
To clone the object into your computer, enter:

	$ git clone https://github.com/emiilax/spoti5.git

(This project is in a private repository, therefore you can only do this if you are invited)

APK file
There is an .apk file in the Apk folder. Send that .apk file to your email and download it into your android phone, and install.

Terminal
First thing you have to do is make sure that you have Gradle installed and a device connected (either a virtual device or other android phone). Make your way into the the project root(enter /your/path/spoti5/EcoBussing).

To build project enter:
	
	$ gradle build

To install the application into your plugged-in device, enter:

	$ gradle installDebug

To uninstall the application from your plugged-in device, enter:
	
	$ gradle uninstallDebug

Android studio
Open Android Studio, if “Welcome to Android Studio” window is active, press open existing project and go to wherever you have put it. 

If you already have started Android Studio, press File > Open > (find the project and open) 
Make sure to open the EcoBussing folder when opening in Android studio.

To try our devmode and simulate a trip you have to switch branch. You do this by:

	$ git fetch origin dev:dev
  
 	$ git checkout dev


All documentation files are in the folder "Documentation"
