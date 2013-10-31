1. Download gradle fromwww.gradle.org and set gradle in PATH environment varaible. 
2. Download JBOSS(wildfly) from www.jboss.org and put it under dependencies directory. rename its directory name to jboss.
3. Update deployer/env.bat to setup correct MYSQL connection information and run deployer/setup.bat to prepare DB. 
3. Go to modules/server directory and run "gradle eclipse" to generate JDT and WTP project files.
4. Import all project in modules/server directory into eclipse.
5. Go to modules/server directory and run "gradle build" to do a full build. 


