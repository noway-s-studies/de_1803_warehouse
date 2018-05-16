# warehouse
Debreceni Egyetem - Progtech/Progkorny feladat

Raktárkészlet nyilvántartó alkalmazás

Rendszerkövetelmények: 
 - Java 8 JRE
 - Apache Maven

Linux (Ubuntu) előkészületek:
 - sudo apt-get install default-jdk
 - sudo apt-get install default-jre
 - sudo apt-get install git
 - sudo apt-get install maven
 - sudo apt-get install openjfx (OpenJDK esetén)

Telepítés és futtatás:
 - Tároló klónozása
 - az "mvn clean package" parancs kiadása a "warehouse" mappában
 - "java -jar target\Warehouse-1.0-jar-with-dependencies.jar XYZ" parancs kiadásával indul az alkalmazás

Alapbeállításon létrehoz egy lokális adatbázist. A "warehouse\src\main\resources\META-INF\persistence.xml" fájlt módosítva hozzákacsolható egyéb adatbázis szerverekhez (pl.: Heroku, MySql) is.

A "XYZ" paraméter az adatbázis jelszava, elhagyása esetén parancssori bekérés történik.
