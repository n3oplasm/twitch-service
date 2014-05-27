#twitch-service

Sign up for a Github account if you don't have one already and let me know what your username is. I'll add you as a collaborator to the project so that you can commit. Once you have an account, configure command-line git to use your Github username and email:

    git config --global user.name "n3oplasm"
    git config --global user.email "n3oplasm@yahoo.com"

The project is currently configured to use Java 7 so you may need to update your Java version if you haven't done so in a while. 

##Prerequisites

I'm using Apache Maven for dependency management (see http://maven.apache.org/) so we'll need to install and use the Maven SCM (Source Code Management) connector for Git in Eclipse before checking out the project. The instructions below should get you a working check-out in Eclipse.

* Download and install the latest version of "Eclipse IDE for Java EE Developers" from http://www.eclipse.org/downloads/. The Java EE version has some extras that will be helpful later.

* Start Eclipse and select the New > Other... menu option then select Maven > Checkout Maven Projects from SCM and click next. Near the bottom of the dialog that comes up click the link to the "m2e marketplace". In the marketplace search for "egit", select m2e-egit from the list and click finish to start installing the SCM connector.

* Once the egit connector is installed and Eclipse has restarted select New > Other... > Maven > Checkout Maven Projects from SCM again. Under the SCM URL drop-down select "git", use the following url and click finish to check out the project: "https://github.com/n3oplasm/twitch-service.git"

At this point you should be able to run com.landmarkstreamers.twitch.Main in Eclipse to test that everything is working.

##Using Egit

To commit: to commit changes to your local git repository. After typing a message click the "Commit and Push" button to finish. When you have made one or more commits that you wish to push to Github, right click the project again, select Team > Push to Upstream and enter your Github username and password (checking Store in Secure Store will prevent Eclipse from prompting you for this again).

To update: Same as commits except after right clicking the project select Team > Pull from the context menu.
