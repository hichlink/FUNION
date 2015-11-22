@echo off
echo [INFO] Install jar to thirdparty repository.

cd %~dp0

mvn deploy:deploy-file -DgroupId=nu.xom -DartifactId=mysqljdbc -Dversion=1.2.5 -Dpackaging=jar -Dfile=xom-1.2.5.jar -Durl=http://192.168.1.20:8081/nexus/content/repositories/thirdparty -DrepositoryId=thirdparty

pause