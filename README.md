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

JavaApplication처럼 바닐라 자바로 개발된 애플리케이션은 개발자가 직접 의존할 객체를 new 키워드를 사용해 생성해주어야 한다.
따라서 애플리케이션의 모듈과 의존할 모듈이 강하게 결합되어 유지보수에 악영향을 끼치고, 테스트 시 Mock 객체를 사용한 단위 테스트가 어렵다.

반면 스프링 프레임워크를 사용하는 DiscodeitApplication에서는 객체가 Bean으로 등록되어 스프링 컨테이너에서 라이프싸이클을 관리하는 제어의 역전(IoC)가 발생한다.
Bean 생성 시 컨테이너가 자동으로 의존성을 주입(DI)해주기 때문에, 모듈 간 결합도가 낮아진다. 거기에 더해 테스트 코드에서 Bean 대신 Mock을 주입해주면 쉽게
단위 테스트를 할 수 있다.

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
    