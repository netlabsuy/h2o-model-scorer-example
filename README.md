# h2o-model-scorer-example

## To add h2o-genmodel.jar to maven

mvn install:install-file -Dfile=src/main/resources/h2o-genmodel.jar -DgroupId=ai.h2o -DartifactId=h2o-genmodel -Dversion=1.0

## To compile

mvn clean package

## To run

java -jar target/\$JAR\_FILE $MOJO $INPUT\_FILE $OUTPUT\_FILE
