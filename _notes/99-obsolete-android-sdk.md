# Notes on working with Android SDK command-line tools only

# Obsolete!

The SDK Tools package is deprecated and no longer receiving updates.
The last revision being SDK Tools, Revision 26.1.1 (September 2017).
See: https://developer.android.com/studio/releases/sdk-tools

## tldr;

sdkmanager --list > package-summary-list.txt
sdkmanager --list --verbose > package-list.txt

## sdkmanager

Commands to know:

sdkmanager --list > list2.txt
Gives a summary list of available packages

sdkmanager --list --verbose > list1.txt
Gives a detail list of available packages.
The summary list may truncate package names making it hard to install packages.

sdkmanager --update --sdk_root=C:\Apps\Android\sdk
Update all installed packages

sdkmanager --install "build-tools;25.0.3" --sdk_root=C:\Apps\Android\sdk
Install a package call "build-tools;25.0.3"

## Installation

From the Android website, there will be a zip file like the below.
sdk-tools-windows-3859397.zip

Inside the zip file, there will a "tools".
Extract this "tools" folder in a temporary folder (eg. C:\temp\tools)
Go to the "bin" folder (ie. C:\temp\tools\bin), and run the following command:

sdkmanager --update --sdk_root=C:\Apps\Android\sdk

## summary for ubuntu-mate
sdkmanager --list --sdk_root=/opt/android/sdk
sdkmanager "tools" --sdk_root=/opt/android/sdk
sdkmanager "build-tools;25.0.3" --sdk_root=/opt/android/sdk
sdkmanager "platforms;android-21" --sdk_root=/opt/android/sdk
sdkmanager --update --sdk_root=/opt/android/sdk

Where:
C:\Apps\Android\sdk is the folder where you want to install the Android SDK tools.

## Minimal packages to install

To begin development with Android SDK, you need the following minimally:

1.  Android SDK Tools
2.  Android SDK Platform-tools
3.  Android SDK Build-tools (highest version)
4.  Android SDK Platform 

### Updating/Installing Android SDK Tools and Android SDK Platform-tools

When you initially extracted the package from the zip file, it will only have:
  Path           | Version | Description                | Location       
  -------        | ------- | -------                    | -------        
  tools          | 26.0.1  | Android SDK Tools          | tools\         

ZX: If installing to a new directory, run the following:
```
sdkmanager "tools" --sdk_root=C:\Apps\Android\sdk
```

After you run the update command:
```
sdkmanager --update --sdk_root=C:\Apps\Android\sdk
```

It will have:

Installed packages:
  Path           | Version | Description                | Location       
  -------        | ------- | -------                    | -------        
  emulator       | 26.1.4  | Android Emulator           | emulator\      
  patcher;v4     | 1       | SDK Patch Applier v4       | patcher\v4\    
  platform-tools | 26.0.1  | Android SDK Platform-Tools | platform-tools\
  tools          | 26.1.1  | Android SDK Tools          | tools\         


### Installing Android SDK Build-tools (highest version)

This left the build tools to be installed.
The version of build tools to be install depends on your project.
For example, in my "build.gradle", "buildToolsVersion" is stated as 25.0.3

buildToolsVersion '25.0.3'

In general, it is recommended to install the latest version of build tools.
However, it is a good practice to go with the version of build tools stated 
in "build.gradle" first and make sure that everything works first before
upgrading to the latest version of build tools.

Path               | Version | Description                   
-------            | ------- | -------                       
build-tools;25.0.3 | 25.0.3  | Android SDK Build-Tools 25.0.3
build-tools;26.0.0 | 26.0.0  | Android SDK Build-Tools 26    
build-tools;26.0.1 | 26.0.1  | Android SDK Build-Tools 26.0.1
build-tools;26.0.2 | 26.0.2  | Android SDK Build-Tools 26.0.2

```
sdkmanager --install "build-tools;25.0.3" --sdk_root=C:\Apps\Android\sdk
```

So now installed packages should look like:

Installed packages:
  Path               | Version | Description                    | Location           
  -------            | ------- | -------                        | -------            
  build-tools;25.0.3 | 25.0.3  | Android SDK Build-Tools 25.0.3 | build-tools\25.0.3\
  emulator           | 26.1.4  | Android Emulator               | emulator\          
  patcher;v4         | 1       | SDK Patch Applier v4           | patcher\v4\        
  platform-tools     | 26.0.1  | Android SDK Platform-Tools     | platform-tools\    
  tools              | 26.1.1  | Android SDK Tools              | tools\             


### Installing Android SDK Platform 

The version of build tools to be install depends on your project.
For example, in my "build.gradle", "compileSdkVersion" is stated as "android-21"

compileSdkVersion 'android-21'

```
sdkmanager --install "platforms;android-21" --sdk_root=C:\Apps\Android\sdk
```

Confession: I did not actually run the above command, it was executed when I run gradle.
            It somehow detects that I did not have the package and install it for me.

Checking the license for package Android SDK Platform 21 in C:\Apps\Android\sdk\licenses
License for package Android SDK Platform 21 accepted.
Preparing "Install Android SDK Platform 21 (revision: 2)".
"Install Android SDK Platform 21 (revision: 2)" ready.
Finishing "Install Android SDK Platform 21 (revision: 2)"
Installing Android SDK Platform 21 in C:\Apps\Android\sdk\platforms\android-21
"Install Android SDK Platform 21 (revision: 2)" complete.
"Install Android SDK Platform 21 (revision: 2)" finished.

In any case, installed packages should now look like:

Installed packages:
  Path                 | Version | Description                    | Location             
  -------              | ------- | -------                        | -------              
  build-tools;25.0.3   | 25.0.3  | Android SDK Build-Tools 25.0.3 | build-tools\25.0.3\  
  emulator             | 26.1.4  | Android Emulator               | emulator\            
  patcher;v4           | 1       | SDK Patch Applier v4           | patcher\v4\          
  platform-tools       | 26.0.1  | Android SDK Platform-Tools     | platform-tools\      
  platforms;android-21 | 2       | Android SDK Platform 21        | platforms\android-21\
  tools                | 26.1.1  | Android SDK Tools              | tools\             

## Other packages to install specific to this project

In the dependencies section of "build.gradle", we have:


compile 'com.android.support.constraint:constraint-layout:1.0.2'
compile 'com.android.support:appcompat-v7:22.2.0'
compile 'com.android.support:design:22.2.0'
compile 'com.google.android.gms:play-services-vision:9.4.0+'

extras;m2repository;com;android;support;constraint;constraint-layout;1.0.2               | 1            | ConstraintLayout for Android 1.0.2
extras;android;m2repository                                                              | 47.0.0       | Android Support Repository                                          
extras;google;google_play_services                                                       | 46           | Google Play services                                                

# Environment variables
SET ANDROID_SDK_ROOT=C:\Apps\Android\sdk


# SDK component licence acceptance

You have not accepted the license agreements of the following SDK components:
  [Android SDK Platform 21, Android SDK Build-Tools 25.0.3].
  Before building your project, you need to accept the license agreements and complete the installation of the missing components using the Android Studio SDK Manager.

```
sdkmanager --licenses
```
or 
```
yes | sdkmanager --licenses
```

# Uninstall packages

Sometimes the fastest way to solve is to re-install the package.
To uninstall a package:

```
sdkmanager --uninstall "platforms;android-21" --sdk_root=D:\Apps\Android\sdk
sdkmanager --install "platforms;android-21" --sdk_root=D:\Apps\Android\sdk
```

