## 요구사항

### 기본
- [x] 기본 요구사항 1
    - [x] 프로젝트 초기화
        - [x] IntelliJ를 통해 다음의 조건으로 Java 프로젝트를 생성합니다.
        - [x] 프로젝트의 경로는 스프린트 미션 리포지토리의 경로와 같게 설정합니다.
        - [x] Create Git Repository 옵션은 체크하지 않습니다.
        - [x] Build system은 Gradle을 사용합니다. Gradle DSL은 Groovy를 사용합니다.
        - [x] JDK 17을 선택합니다.
        - [x] GroupId는 com.sprint.mission로 설정합니다.
        - [x] ArtifactId는 수정하지 않습니다.
        - [x] .gitignore에 IntelliJ와 관련된 파일이 형상관리 되지 않도록 .idea디렉토리를 추가합니다.
    - [x] 도메인 모델링
        - [x] 디스코드 서비스를 활용해보면서 각 도메인 모델에 필요한 정보를 도출하고, Java Class로 구현하세요.
            - [x] 패키지명: com.sprint.mission.discodeit.entity
            - [x] 도메인 모델 정리
            - [x] 생성자
            - [x] 메소드
    - [x] 서비스 설계 및 구현
      - [x] 도메인 모델 별 CRUD(생성, 읽기, 모두 읽기, 수정, 삭제) 기능을 인터페이스로 선언하세요.
        - [x] 인터페이스 패키지명: com.sprint.mission.discodeit.service
        - [x] 인터페이스 네이밍 규칙: 도메인 모델 이름 + Service
      - [x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
        - [x] 클래스 패키지명: com.sprint.mission.discodeit.service.jcf
        - [x] 클래스 네이밍 규칙: JCF[인터페이스 이름]
        - [x] Java Collections Framework를 활용하여 데이터를 저장할 수 있는 필드(data)를 final로 선언하고 생성자에서 초기화하세요.
        - [x] data 필드를 활용해 생성, 조회, 수정, 삭제하는 메소드를 구현하세요.
    - [x] 메인 클래스 구현
        - [x] 메인 메소드가 선언된 JavaApplication 클래스를 선언하고, 도메인 별 서비스 구현체를 테스트해보세요.
          - [x] 등록
          - [x] 조회(단건, 다건)
          - [x] 수정
          - [x] 수정된 데이터 조회
          - [x] 삭제
          - [x] 조회를 통해 삭제되었는지 확인
- [x] 기본 요구사항 2
  - [x] File IO를 통한 데이터 영속화
    - [x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
      - [x] 클래스 패키지명: com.sprint.mission.discodeit.service.file
      - [x] 클래스 네이밍 규칙: File[인터페이스 이름]
      - [x] JCF 대신 FileIO와 객체 직렬화를 활용해 메소드를 구현하세요.
    - [x] Application에서 서비스 구현체를 File*Service로 바꾸어 테스트해보세요.
  - [x] 서비스 구현체 분석
    - [x] JCF*Service 구현체와 File*Service 구현체를 비교하여 공통점과 차이점을 발견해보세요.
      - [x] "비즈니스 로직"과 관련된 코드를 식별해보세요.
      - [x]  "저장 로직"과 관련된 코드를 식별해보세요.
  - [x] 레포지토리 설계 및 구현
    - [x] "저장 로직"과 관련된 기능을 도메인 모델 별 인터페이스로 선언하세요.
      - [x] 인터페이스 패키지명: com.sprint.mission.discodeit.repository
      - [x] 인터페이스 네이밍 규칙: [도메인 모델 이름]Repository
    - [x] 다음의 조건을 만족하는 레포지토리 인터페이스의 구현체를 작성하세요.
      - [x] 클래스 패키지명: com.sprint.mission.discodeit.repository.jcf
      - [x] 클래스 네이밍 규칙: JCF[인터페이스 이름]
      - [x]  기존에 구현한 JCF*Service 구현체의 "저장 로직"과 관련된 코드를 참고하여 구현하세요.
    - [x] 다음의 조건을 만족하는 레포지토리 인터페이스의 구현체를 작성하세요.
      - [x] 클래스 패키지명: com.sprint.mission.discodeit.repository.file
      - [x] 클래스 네이밍 규칙: File[인터페이스 이름]
      - [x] 기존에 구현한 File*Service 구현체의 "저장 로직"과 관련된 코드를 참고하여 구현하세요.

### 심화
- [x] 심화 요구사항 1
  - [x] 서비스 간 의존성 주입
    - [x] 도메인 모델 간 관계를 고려해서 검증하는 로직을 추가하고, 테스트해보세요.
- [ ] 심화 요구사항 2
  - [ ] 관심사 분리를 통한 레이어 간 의존성 주입
    - [x] 다음의 조건을 만족하는 서비스 인터페이스의 구현체를 작성하세요.
      - [x] 클래스 패키지명: com.sprint.mission.discodeit.service.basic
      - [x] 클래스 네이밍 규칙: Basic[인터페이스 이름]
      - [x] 기존에 구현한 서비스 구현체의 "비즈니스 로직"과 관련된 코드를 참고하여 구현하세요.
      - [x] 필요한 Repository 인터페이스를 필드로 선언하고 생성자를 통해 초기화하세요.
      - [x] "저장 로직"은 Repository 인터페이스 필드를 활용하세요. (직접 구현하지 마세요.)
    - [x] Basic*Service 구현체를 활용하여 테스트해보세요.
      - [x] JCF*Repository  구현체를 활용하여 테스트해보세요.
      - [x] File*Repository 구현체를 활용하여 테스트해보세요.
    - [ ] 이전에 작성했던 코드(JCF*Service 또는 File*Service)와 비교해 어떤 차이가 있는지 정리해보세요.

## 주요 변경사항
- Git Repository init
- User, Channel, Message 도메인 엔티티 클래스 생성
- CrudService 인터페이스 생성
- CrudService 인터페이스에 crud 메소드 선언
- 각 도메인 별로 CrudService의 JCF 구현체 클래스 implement
- - 각 도메인 별로 CrudService의 File 구현체 클래스 implement
- JavaApplication 클래스 생성
- main 메소드에 테스트 코드 추가
- MessageService에 ChannelService, UserService를 필드로 사용하도록 컴포지션 패턴 추가
- 저장 기능을 담당하는 Repository 인터페이스 추가
- 각 도메인 별로 Repository의 JCF 구현체 클래스 implement
- 각 도메인 별로 Repository의 File 구현체 클래스 implement
- 비밀번호 해싱을 위한 SecurityUtils 클래스 생성
- 엔티티에 빌더 패턴 적용

## TodoList
- service에 파라미터로 사용할 request DTO 생성
- 예외처리 점검
- FileService 테스트 코드에 전체 폴더 clear 하는 코드 추가

## 스크린샷
<img width="1446" height="464" alt="유저서비스" src="https://github.com/user-attachments/assets/53600849-0200-4265-8455-2f1ec4ef4f31" />
<img width="1446" height="477" alt="채널서비스" src="https://github.com/user-attachments/assets/c9dab0b7-4876-4caa-9833-34ee3be53892" />
<img width="1755" height="523" alt="메시지서비스" src="https://github.com/user-attachments/assets/42cf590a-f0c0-4827-93b5-5587510f82e0" />


## 멘토에게
- 셀프 코드 리뷰를 통해 질문 이어가겠습니다.
- 

