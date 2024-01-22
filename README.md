# spring-boot-jwt-tutorial

spring boot - spring security를 이용한 jwt authorization 구현 및 테스트

H2 DB 기반

resources - data.sql를 통해 application run 시 자동으로 데이터 생성 (admin user db)

## API
- POST /api/signup
  - 회원 가입. 일반적으로 user role로 가입된다.
- POST /api/authenticate
  - 로그인 api. 사용자의 jwt를 response body로 응답
- GET /api/user
  - user의 role 및 정보들을 리턴
- GET /api/user
  - admin, user role 모두 호출할 수 있는 api
- GET /api/user/{username}
  - admin role만 호출할 수 있는 api. 즉 role 동작 테스트 용도

## Test
test.java.com.mason.jwttutorial.controller.ControllerTest.http 


# ref
- https://github.com/SilverNine/spring-boot-jwt-tutorial
- https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt