version=$(date "+%Y%m%d%H%M%S")
mvn -e -B -U clean package -Dmaven.test.skip=true
docker build -t java-scaffold:${version} .
docker run -dp 8080:8080 java-scaffold:${version}