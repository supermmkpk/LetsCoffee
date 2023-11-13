FROM eclipse-temurin:11 as build

# build
WORKDIR /app

RUN apt-get -y update
RUN apt install wget
RUN apt install unzip
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm
RUN rpm -ivh google-chrome-stable_current_x86_64.rpm
RUN wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/` curl -sS chromedriver.storage.googleapis.com/LATEST_RELEASE`/chromedriver_linux64.zip
RUN mkdir chrome
RUN unzip /tmp/chromedriver.zip chromedriver -d /app/chrome/

COPY . /app

RUN if [ -f "./gradlew" ]; then chmod +x ./gradlew; fi
RUN --mount=type=cache,id=test-gradle,target=/root/.gradle ./gradlew clean bootjar -x test --build-cache -i -s --no-daemon

# runner
FROM eclipse-temurin:11-jre

RUN set -o errexit -o nounset \
  && groupadd --system --gid 1000 java \
  && useradd --system --gid java --uid 1000 --shell /bin/bash --create-home java

WORKDIR /app
COPY --from=build --chown=java:java /app/ .

USER java

CMD java -jar -Dspring.profiles.active=${PROFILE:=prod} `find . -type f -name "*.jar" ! -path "*-plain.jar" ! -path "*-wrapper.jar" | head -1`