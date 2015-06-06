mkdir /projects
mkdir /projects/chordsequence
git pull
cp -ur ./data /projects/chordsequence/
chmod -R 777 /projects/chordsequence/data/
cp -ur ./template /projects/chordsequence/
cp -ur ./webapps /var/lib/tomcat8
