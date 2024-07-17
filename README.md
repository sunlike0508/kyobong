# 교봉

교봉문고 도서 관리 장부 시스

---

## API 설명 및 구현 범위

* 호스트 : http://localhost:8080

### 검색어를 통한 책 조회

```
GET /books
```

지은이, 제목 입력 여부에 따라서 책을 조회합니다.

지은이, 제목은 옵션으로 모두 입력하지 않으면 전체 조회를 합니다.

### 책 등록

```
POST /books
```

책 등록 API 입니다

책은 하나 이상의 카테고리를 가지고 있어야 하기 때문에 카테고리 ID를 받는 List를 받습니다.

DB 구성은 책과 카테고리는 ManyToMany 관계입니다. 하지만 보통 ManytoMany는 성능 이슈 및 객체지향 개념은 아니기 때문에

일대다, 다대일, 일대다 개념으로 중간 매핑테이블을 만들었습니다.

### 책 수정

```
PATCH /books/{id}
```

책 제목, 지은이, 대여 가능 여부, 카테고리를 선택적으로 수정할 수 있습니다.

### 카테고리 등록

```
POST /categories
```

책을 등록하기 위해 카테고리가 필수입니다. 따라서 없는 카테고리를 등록하기 위해서 먼저 카테고리를 등록하기 위해 만들었습니다.

---

### 카테고리 별 책 검색

```
GET /books/categories/{id}
```

원하는 검색 카테고리로 등록된 책을 조회합니다.

---

## 개발 환경, DataBase, API 테스트 실행 방법

* 개발 환경
    * IntelliJ
    * spring boot 3.3.1
    * 자바 17
    * Junit 5
    * H2 Database
    * Spring RestDocs


* (테스트) DB
    * SpringBootApplication 실행되면 과제에서 내어준 데이터 내용으로 구성된 src/main/resources/product.json 파일을
      InitDataProcess.class가 읽고 h2(in-memory)에 자동으로 데이터가 추가 됩니다.
    ``` 
    http://localhost:8080/h2-console 에서 데이터 확인 가능
    ```


* API 호출 HTTP 파일 위치
    * src/test/java/kyobong/controller/http에 있는 파일들을 통해 POSTMAN처럼 API 호출 가능합니다.

---

## 기타추가정보

* Spring Rest Docs 적용

1. git 명령어 테스트 코드실행, 혹은 IDE(IntelliJ) 테스트실행

   ``` 
   ./gradlew :test
   ```

   build/generated-snippets 하위 폴더에 adoc 파일이 생성되면 정상


2. git 명령어 build 실행, 혹은 IDE 빌드 실행

   ``` 
   ./gradlew build
   ```

   src/main/resources/static/docs 폴더에 html 파일 생성되면 정상


3. Appplication 실행 후 http://localhost:8080/docs/index.html 접속 후 확인