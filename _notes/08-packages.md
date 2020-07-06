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

sdkmanager --install "extras;google;google_play_services" --sdk_root=C:\Apps\Android\sdk
Install google play services
