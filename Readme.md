



다음 기술 스택으로 구현되었습니다.
- Java 8
- Spring Boot(+Maven)
- JPA, QueryDSL
- Spring Security
- MySQL

---
## 도메인 요구사항
- 글 작성, 수정, 삭제,
    + 쓰기 IO에 대한 날짜
- 글 좋아요
    + 좋아요 매핑 이력
- 글 목록
    + 좋아요 갯수
### 기타 보안사항
- 사용자 인가

---
## 인터페이스 명세(REST API)
*불필요한 메서드 제한 보안 이슈*로 모든 method는 GET/POST로 구현하려 하였으나,
*내부망, 또는 보안 처리가 되어있다는 가정 하에,*
관습적인 좋은 REST API 설계 패턴의 메소드로 변경합니다.
### 글 작성

1. request   
   uri : `/article/new`   
   method : POST   
   body :
```Json
{ 
  "writerId" : "글 작성자 accountId",
  "title" : "제목",
  "content" : "본문 내용"
}  
```
2. response
```Json
{
  "processResult": "success/fail/null",
  "resultDetail": "",
  "resultData":{
    "articleId": 4,
    "writerId": "글 작성자 accountId",
    "title": "제목",
    "content": "본문 내용",
    "regdate": "2022-06-07T00:47:57.034+00:00",
    "lastUpdate": "2022-06-07T00:47:57.034+00:00",
    "deldate": null,
    "likeCount": 0,
    "like": false
  }
}
```

### 글 수정
1. request   
   uri : `/article/{memberAccountId}`   
   method : PUT   
   body :
```Json
{ 
   "articleId" : "글 아이디", 
   "title" : "제목", 
   "content" : "본문 내용"
}  
```
2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData":{
      "articleId": 1,
      "writerId": "글 작성자 accountId",
      "title": "제목",
      "content": "본문 내용",
      "regdate": "2022-06-07T00:16:25.000+00:00",
      "lastUpdate": "2022-06-07T00:16:25.000+00:00",
      "deldate": null,
      "likeCount": 0,
      "like": false
   }
}  
```
- 커밋 이전의 데이터를 가져옵니다.

### 글 삭제
1. request   
   uri : `/article/{articleId}/{memberAccountId}`   
   method : DELETE

2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData":{
      "articleId": 1,
      "writerId": "글 작성자 accountId",
      "title": "제목",
      "content": "본문 내용",
      "regdate": "2022-06-07T00:16:25.000+00:00",
      "lastUpdate": "2022-06-07T04:04:44.000+00:00",
      "deldate": null,
      "likeCount": 0,
      "like": false
   }
}
```
- 커밋 이전의 데이터를 가져옵니다.

### 글 단 건(view)
1. request   
   uri : `/article/view/{articleId}/{memberAccountId}`   
   method : GET

2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData":{
      "articleId": 3,
      "writerId": "글 작성자 accountId",
      "title": "제목",
      "content": "본문 내용",
      "regdate": "2022-06-07T00:46:39.000+00:00",
      "lastUpdate": "2022-06-07T00:46:39.000+00:00",
      "deldate": null,
      "likeCount": 2,
      "like": true
   }
}
```

### 글 목록(list)
1. request   
   uri : `/list/{startIdx}/{count}/{memberAccountId}`   
   method : GET

2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData":{
      "dtoList":[
         {"articleId": 2, "writerId": "222", "title": "test1", "content": "111",…},
         {"articleId": 3, "writerId": "222", "title": "test1", "content": "111",…},
         {"articleId": 4, "writerId": "222", "title": "test1", "content": "111",…},
         {"articleId": 5, "writerId": "222", "title": "test1", "content": "111",…},
         {"articleId": 6, "writerId": "222", "title": "test1", "content": "111",…}
      ]
   }
}
```
- 프런트에서 요구하는, 그리고 구현할 전략에 대해서 파악 후, 제공 데이터 수준을 재정의 해야할 필요성을 느낍니다.

### 글 좋아요
1. request   
   uri : `/like/{articleId}/{memberAccountId}`   
   method : PUT

2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData": null
}
```

### 글 좋아요 취소
1. request   
   uri : `/like/{articleId}/{memberAccountId}`   
   method : DELETE

2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData": null
}
```

### 글 좋아요 한 아이디 목록
1. request   
   uri : `/like/list/{articleId}`   
   method : GET

2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData":{
      "dtoList":[
         {
            "likeId": 1,
            "articleId": 3,
            "memberAccountId": "사용자1"
         },
         {
            "likeId": 2,
            "articleId": 3,
            "memberAccountId": "사용자2"
         }
      ]
   }
}
```

### 신규 사용자 (사용자 동기화 인터페이스)
- 요구사항 이외의 내부 데이터 동기화용 경로입니다.
- 타 인터페이스 방식을 적용시킬 수 있습니다.
1. request   
   uri : `/Member/new`   
   method : POST   
   body :
```Json
{ 
   "nickname" : "닉네임", 
   "accountType" : "계정 타입", 
   "accountId" : "계정 아이디",
   "quit" : "탈퇴 여부"
}  
```
- accountType
  - 
- quit
    - 0 : 미탈퇴
2. response
```Json
{
   "processResult": "success",
   "resultDetail": "",
   "resultData":{
      "accountId": "계정 아이디",
      "nickname": "닉네임",
      "accountType": "",
      "quit": "0"
   }
}
```


---

- 레이어 간 비즈니스 로직을, 커스텀 Exception 의 다형성 객체들로 관리하려 하였으나,
- 우선 기본 예외를 통해서 비즈니스 로직을 처리했습니다.

---

## 구현 방식
1. 영속성 컨텍스트를 활용하여, 데이터 캐싱 전략을 적극 사용하려 했습니다.
    - 미쳐 엔티티의 LAZY, EAGER 에 대한 이슈를 생각하지 못하여, 현재 구성 방식으로 전환했습니다.
    - 다만 현재 방식은 잠금으로 인한 우려가 있어, 개선을 고민 중 입니다.
2. 로직 처리는 네 단계의 레이어를 가집니다.
    1. Controller   
       라우팅과 입력 데이터를 처리합니다.
        - 각 도메인의 세부 요구사항 파악으로 공통된 코드를 AOP 혹은 인터셉터로 옮기는 것이 필요해 보입니다.
    2. Service
        - 순수 비즈니스 로직만을 처리합니다.
        - 비즈니스 로직의 에러를 최종 처리합니다.
        - 사용자 예외 클래스를 통한 예외 통제를 적용하려 하였습니다.
            - 해당 방식이 레이어간 예외 소통에 유용하다고 생각하였습니다.
    3. DAO
        - 데이터베이스 단일 세션 접근을 목표로 구현된 계층입니다.
        - 세션과 트랜잭션 관리가 명시적입니다.
        - 데이터 IO가 필요한 비즈니스 로직을 처리합니다.
        - 다만 잠금으로 인한 우려가 있습니다.
        - 개선이 필요합니다.
    4. Repository
        - Entity에 대한 단일 기능을 정의합니다.
3. 데이터 객체
    1. Entity
        - 테이블과 일대일 매핑된 객체입니다.
        - LAZY 전략만을 사용합니다.
    2. DTO
        - 데이터의 레이어간 이동에 사용됩니다.
        - 비즈니스 로직 처리에서 생기는 데이터 가공에 대해, 영속 데이터와의 분리를 위함입니다.
        - Mapper와 비교하여, 서버에서 정적 타입 언어를 지향하는 이유와 같습니다.
    3. Factory
        - DTO를 Entity와 연동하기 위한 객체입니다.
    4. VO (폐기)
        - Decorator 패턴
        - Factory가 더 좋은 경험을 제공하여 폐기하였습니다.
        - 다만 DTO 보다 LAZY 경험을 제공해 줄 수 있기 때문에, 차후 사용이 가능하다고 보여 남겼습니다.
        - 다시 사용할 경우, 그룹 이름에 대한 고민이 필요하다고 생각합니다.
4. 인가
    1. Filter
        1. MemberFilter
            - Spring Security의 Filter 사이에 두었습니다.
            - Interceptor, AOP 중 해당 방식이 가장 적합하여, 구현했습니다.
5. 각 도메인에 대하여..
    1. 글
        - 좋아요 갯수를 넣는 캐싱 전략을 사용합니다.
        - 좋아요는 글과 함께 빈번하게 조회되기 때문에, 일일히 그때마다 따로 조회하는 것보단,
          캐싱 전략을 활용합니다.
    2. 좋아요
        - 일일히 좋아요 갯수를 조회하는 것보다, 트랜잭션을 이용한 잠금으로 확인 후, 단순 사칙연산을 활용합니다.
        - 인프라 환경에 따라, 스케쥴링 등의 전략으로 좋아요 갯수 확인이 필요 할 수 있습니다.
    3. 회원
        - 동기화를 위한 인터페이스 구현이 필요해보입니다.
        - 과제 요구 사항 외, 테스트 경험을 위하여, 회원 생성 기능을 추가 구현했습니다.
---
## 필요 외부 소프트웨어
- MySql


---
## VER1(ALPHA) 구현 소감 - 2022.06.07
과제 시작일을 목요일로 하였는데요.   
저는 계획과 분석을 중요시하여,
- 목, 금 : 기술 및 요구사항 분석/설계
- 토, 일 : 서비스 구현
- 월 : 요구사항 검토 및 완료
- 화, 수 : 사후 검토   
  일정을 계획하였습니다.

하지만,  
JPA와 QeryDsl, 필터 구현에서 예상치 못한 상황으로   
4~5 번 구조를 바꿨습니다.

실제 일정은
- 목, 금 : 기술 및 요구사항 분석/설계
- 토, 일, 월 : 서비스 구현
- 화 : 요구사항 검토 및 완료
- 수 : 사후 검토

요구 사항 이외에,   
추가하고 싶은 여러 기능 및 설계들이 있었지만,   
필수 요구 사항 구현에 그친점이 아쉽습니다.


## todo
2022.06.07 
- 잠김과 Entity의 LAZY 이슈 사이의 베스트 프렉티스에 대한 고민이 좀 더 필요하다고 생각합니다.
- AOP 구현이 가능한 공통 코드가 보이며, 차후 고도화할 필요성을 느낍니다.
- MySQL 실행계획 캐싱 전략, 인덱스 활용을 고려해봐야 합니다.
- 첫 연결시, 세션 영속 컨텍스슽로 관리 추가 + 영속성 컨텍스트 캐싱 전략 고도화
- aop 추가로, Controller와 Service 간략화
- DAO 삭제 => Service로

2022.06.15 
- ~~영속성 컨텍스트를 서비스마다 생성하는 이상한 짓을 했습니다. DI로 풀어낼 것.~~
- ~~GET/POST로만 되어있는, 관습적인 좋은 REST API 설계 패턴의 메소드로 변경해야 합니다.~~
- ~~domain/repository의 데코레이터 인터페이스 및 클래스 폴더를 추가합니다.~~

2022.06.27
- 모델이 IO를 거치며, OLD 영역으로 보관되는 것으로 보입니다. GC를 고려하여, 에덴 영역에서만 남도록 작업이 필요합니다. 
