#!/bin/bash

nameProject='automation-java-restrictios-services-api'
version=$(xmllint --xpath '/*[local-name()="project"]/*[local-name()="version"]/text()' pom.xml |sed -e 's/-SNAPSHOT//g')

echo ">>>>> Isso aí!!! Vamos clonar e buildar o projeto de testes Brow \o/"
echo ">>>>> Jutsu Clone das Sombras o+ "
git clone ssh://git@code.experian.local/tbq/${nameProject}.git
cd ${nameProject}
git checkout develop
git pull origin develop

echo ">>>>> Buildando em 3...2...1 "
/opt/infratransac/jenkins/tools/hudson.tasks.Maven_MavenInstallation/maven-3.2.5/bin/mvn clean compile

#typeProject = (selenium | api)
typeProject='api'

echo ">>>>> Agora é hora de executar os testes. Que a força esteja com você!!"
/opt/infratransac/jenkins/tools/hudson.tasks.Maven_MavenInstallation/maven-3.2.5/bin/mvn verify

echo ">>>>> Acabaram os testes ThunderCats, mas precisamos ter a Visão Além do Alcance com os relatórios gerados!!"

echo ">>>>> Gerando report da versao ${version} "

reportDir="target/cucumber-html-reports/"
jsonDir="target/jsonReports"
resultsDir="/opt/infratransac/qs-reports/cucumber/${typeProject}/${nameProject}-test"
if ! [[ -d "${resultsDir}" ]]; then
    mkdir -p "${resultsDir}"
fi
reportDate=$(date "+%d-%b-%H:%M:%S")
finalDir="${resultsDir}/${version}-${reportDate}"
finalPage="http://spobrjenkins:9094/qs-reports/cucumber/${typeProject}/${nameProject}-test/${version}-${reportDate}/index.html"


cp $reportDir/overview-features.html $reportDir/index.html
cp $jsonDir/cucumber-report.json $reportDir/
cp -r $reportDir/. $finalDir

echo ">>>>> Guerreiros... Relatório disponibilizado: ${finalPage}"
echo ">>>>> Processo finalizado com Sucesso \o/. Espero que tenham atingido o objetivo oO"