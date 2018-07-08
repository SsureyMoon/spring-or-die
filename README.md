# Spring Study Repo
This repository is for hand-on studying the book [Tobi's Spring](http://toby.epril.com/?page_id=1062).

이 코드 저장소는 [토비의 스프링](http://toby.epril.com/?page_id=1062)을 공부하기 위한 목적으로 만들어졌습니다.

## Development env
The development/test environment is base on docker containers.
It is independent of IntelliJ or Eclipse,
since tasks are executed via CLI.

이 프로젝트의 개발/테스트 환경은 도커 컨테이너 기반입니다.
앱을 실행/테스트하는 작업들은 CLI를 통해 실행되기 때문에,
IntelliJ나 Eclipse 같은 특정 IDE로 부터 독립적으로 사용하실 수 있습니다.  
```
$ docker-compose up
```

## Test
Run gradle test in the container `app`.

Gradle 테스트를 `app` 컨테이너 내부에서 실행합니다.
```
$ docker-compose exec app /bin/bash -c "./gradlew test"
```
