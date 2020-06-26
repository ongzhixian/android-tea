# Development environment setup

This project focus on building an Android mobile application from scratch using just command-line tools.

## Requirements

We need the following to be installed on the development PC:

1.  Gradle 6.5      
    Download: https://gradle.org/releases/

2.  Java 8 SDK      
    Download: https://www.oracle.com/sg/java/technologies/javase/javase-jdk8-downloads.html

3.  Android SDK
    Download: https://developer.android.com/studio#command-tools


For the purpose of this project we will be using Gradle 6.5 (the latest as of data 2020-06-26) to build Android/Java application.
Java 8 SDK is required to compile Java source files.
Android SDK is required to build Android applications.

## Installation

* Gradle

Installation for Gradle is straight-forward.
Download the binary-zip files and extract them into a folder of your choice.

* Java 8 SE JDK 

Installation for Java JDK 8 can be as straight-forward like Gradle if
you manage to lay hold of a zip-bundled of Java JDK.

If you are downloading a latest version of Oracle Java 8 JDK, 
it will come as installer executable on the Windows platform where
you will then install the JDK into the location of your choice.

* Android SDK

It has been quite a while since I re-looked at Android development from the command-line perspective.

All the tools you need used to be bundled under a singular package call Android SDK tools.

As of date (2020-06-26), it is now broken down into multiple packages:

1.  Command line tools only ()

    Out of the box, it only contains:

    1.  apkanalyzer.bat
    2.  avdmanager.bat
    3.  lint.bat
    4.  screenshot2.bat
    5.  sdkmanager.bat

    The idea here, being that you are given a minimal command-line tools.
    Which you will then use sdkmanager.bat to download the other required packages.

    Android SDK Tools
    Android SDK Build Tools
    Android SDK Platform Tools
    Android Emulator
    Jetifier

    Example filename: commandlinetools-win-6609375_latest.zip
    See: https://developer.android.com/studio/command-line


2.  SDK Platform Tools

    Android SDK Platform-Tools is a component for the Android SDK.
    It can also be downloaded using the command-line tools.

    It includes tools that interface with the Android platform, such as adb, fastboot, and systrace; tools are required for Android app development.
    They're also needed if you want to unlock your device bootloader and flash it with a new system image.
    Example filename: platform-tools_r30.0.3-windows.zip


Confession: I'm feeling lazy to go through the cycle of setting this up as I
            have a working(?) copy of Android SDK installed already.
            I'll come back to this topic of installing Android SDK command-line another day.
            So I'm keeping "99-obsolete-android-sdk.md" as reference.


## Environment variables

ANDROID_HOME=C:\Apps\Android\sdk
ANDROID_SDK_ROOT=C:\Apps\Android\sdk
JAVA_HOME=C:\Apps\Java\JDK
GRADLE_HOME=C:\Apps\gradle

Confession: I didn't really set GRADLE_HOME, as I don't think I need it. 
            But its really a good idea to set the environment variables.
