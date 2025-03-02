# ArchUnit Test Guide

이 문서는 ArchUnit을 사용하여 특정 아키텍처 규칙을 검증하는 방법을 설명합니다.  
테스트 대상은 다음과 같습니다.

- `@AuditTable` 어노테이션이 적용된 클래스의 테이블 네이밍 규칙 검증
- Request 및 Response DTO 클래스의 필드에 `@Schema` 어노테이션 적용 여부 검증

---

## 사용 예시

### 1. `@AuditTable` 어노테이션 검사
- `@AuditTable`이 적용된 클래스의 테이블 이름이 `_log`로 끝나는지 확인합니다.
- 네이밍 규칙을 준수하지 않을 경우 테스트가 실패합니다.

#### 예제 코드
```java
@AuditTable(value = "user_log") // 올바른 네이밍
public class UserEntity { }
```
```java
@AuditTable(value = "user_history") // 테스트 실패
public class UserEntity { }
```

#### 실행 결과
```
Class org.example.entity.UserEntity has @AuditTable with name 'user_history', but it should end with '_log'
```

---

### 2. DTO 필드에 `@Schema` 어노테이션 적용 검사
- Request 및 Response DTO 클래스를 대상으로 모든 필드가 `@Schema` 어노테이션을 포함하는지 확인합니다.
- `@Schema`가 적용되지 않은 필드가 있을 경우 테스트가 실패합니다.

#### 예제 코드
```java
public class LoginRequestDto {
    @Schema(description = "사용자 아이디") // 적용됨
    private String username;

    @Schema(description = "비밀번호") // 적용됨
    private String password;
}
```
```java
public class UserResponseDto {
    @Schema(description = "사용자 ID") // 적용됨
    private Long id;

    private String username; // @Schema 없음 → 테스트 실패
}
```

#### 실행 결과
```
Field 'username' in class org.example.dto.UserResponseDto does not have @Schema annotation
```

---

## 실행 방법
### 1. Gradle을 사용하여 테스트 실행
```sh
./gradlew test
```

### 2. Maven을 사용하여 테스트 실행
```sh
mvn test
```

### 3. 특정 ArchUnit 테스트만 실행
```sh
./gradlew test --tests "org.example.com.archunitjavatest.AuditTableNamingTest"
./gradlew test --tests "org.example.com.archunitjavatest.SchemaAnnotationTest"
```

---

## 프로젝트 파일 구조
```
/src/test/java/org/example/com/archunitjavatest/
 ├── AuditTableNamingTest.java  // `@AuditTable` 네이밍 규칙 검사
 ├── SchemaAnnotationTest.java  // DTO 필드에 `@Schema` 어노테이션 적용 검사
 ├── ArchUnitTestConfig.java    // ArchUnit 설정 파일 (필요시)
```

---

## 요구사항
- Java 17 이상
- Gradle 또는 Maven 빌드 시스템
- ArchUnit 라이브러리
  ```groovy
  dependencies {
      testImplementation 'com.tngtech.archunit:archunit-junit5:1.1.0'
      testImplementation 'org.hibernate:hibernate-envers:6.2.0.Final' // `@AuditTable` 검사용
  }
  ```

---

## 참고 자료
- ArchUnit 공식 문서: [https://www.archunit.org/](https://www.archunit.org/)
- Swagger `@Schema` 어노테이션 문서: [https://swagger.io/specification/](https://swagger.io/specification/)

---

## 결론
이 프로젝트는 아키텍처 규칙을 자동으로 검증하기 위한 테스트를 포함하고 있습니다.
- `@AuditTable` 어노테이션이 적용된 테이블의 네이밍 규칙을 강제할 수 있습니다.
- DTO 클래스의 모든 필드에 `@Schema` 어노테이션이 적용되었는지 확인할 수 있습니다.

테스트를 실행하면 Swagger 문서화를 강제하고, 테이블 네이밍 규칙을 자동으로 검증할 수 있습니다.

