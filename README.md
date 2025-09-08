## 요구사항

### 기본

- [x] Spring 프로젝트 초기화
  - [x] Spring Initializr를 통해 zip 파일을 다운로드하세요. 
    - [x] 빌드 시스템은 Gradle - Groovy를 사용합니다.
    - [x] 언어는 Java 17를 사용합니다.
    - [x] Spring Boot의 버전은 3.4.0입니다.
    - [x] GroupId는 com.sprint.mission입니다.
    - [x] ArtifactId와 Name은 discodeit입니다.
    - [x] packaging 형식은 Jar입니다
    - [x] Dependency를 추가합니다. 
      - [x] Lombok
      - [x] Spring Web
  - [x] zip 파일을 압축해제하고 원래 진행 중이던 프로젝트에 붙여넣기하세요. 일부 파일은 덮어쓰기할 수 있습니다.
  - [x] application.properties 파일을 yaml 형식으로 변경하세요.
  - [x] DiscodeitApplication의 main 메서드를 실행하고 로그를 확인해보세요.
- [x] Bean 선언 및 테스트
  - [x] File*Repository 구현체를 Repository 인터페이스의 Bean으로 등록하세요.
  - [x] Basic*Service 구현체를 Service 인터페이스의 Bean으로 등록하세요.
  - [x] JavaApplication에서 테스트했던 코드를 DiscodeitApplication에서 테스트해보세요. 
    - [x] JavaApplication 의 main 메소드를 제외한 모든 메소드를 DiscodeitApplication클래스로 복사하세요.
    - [x] JavaApplication의 main 메소드에서 Service를 초기화하는 코드를 Spring Context를 활용하여 대체하세요.
    - [x] JavaApplication의 main 메소드의 셋업, 테스트 부분의 코드를 DiscodeitApplication클래스로 복사하세요.
- [x] Spring 핵심 개념 이해하기
  - [x] JavaApplication과 DiscodeitApplication에서 Service를 초기화하는 방식의 차이에 대해 다음의 키워드를 중심으로 정리해보세요. 


- [x] Lombok 적용
  - [x] 도메인 모델의 getter 메소드를 @Getter로 대체해보세요.
  - [x] Basic*Service의 생성자를 @RequiredArgsConstructor로 대체해보세요.

### 추가

- [x] 시간 타입 변경하기
- [x] 새로운 도메인 추가하기
- [x] UserService 고도화
- [x] AuthService 구현
- [x] ChannelService 고도화
- [x] MessageService 고도화
- [x] ReadStatusService 구현
- [x] UserStatusService 고도화
- [x] BinaryContentService 구현
- [x] 새로운 도메인 Repository 구현체 구현

### 심화

- [x] Bean 다루기
  - [x] Repository 구현체 중에 어떤 구현체를 Bean으로 등록할지 Java 코드의 변경 없이 application.yaml 설정 값을 통해 제어해보세요.
    - [x] discodeit.repository.type 설정값에 따라 Repository 구현체가 정해집니다. 
      - [x] 값이 jcf 이거나 없으면 JCF*Repository 구현체가 Bean으로 등록되어야 합니다.
      - [x] 값이 file 이면 File*Repository 구현체가 Bean으로 등록되어야 합니다.
  - [x] File*Repository 구현체의 파일을 저장할 디렉토리 경로를 application.yaml 설정 값을 통해 제어해보세요.
    