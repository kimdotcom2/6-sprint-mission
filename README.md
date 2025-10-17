## 요구사항

### 기본 요구사항
- [x] 데이터베이스
  - [x]  아래와 같이 데이터베이스 환경을 설정하세요.
      - 데이터베이스: discodeit
      - 유저: discodeit_user 
      - 패스워드: discodeit1234
  - [x] ERD를 참고하여 DDL을 작성하고, 테이블을 생성하세요. 
- [x] Spring Data JPA 적용하기
  - [x] Spring Data JPA와 PostgreSQL을 위한 의존성을 추가하세요.
  - [x] 앞서 구성한 데이터베이스에 연결하기 위한 설정값을 application.yaml 파일에 작성하세요.
  - [x] 디버깅을 위해 SQL 로그와 관련된 설정값을 application.yaml 파일에 작성하세요.
  - [x] 엔티티 정의하기
    - [x] 클래스 다이어그램을 참고해 도메인 모델의 공통 속성을 추상 클래스로 정의하고 상속 관계를 구현하세요.
    - [x] JPA의 어노테이션을 활용해 createdAt, updatedAt 속성이 자동으로 설정되도록 구현하세요.
    - [x] 클래스 다이어그램을 참고해 클래스 참조 관계를 수정하세요. 필요한 경우 생성자, update 메소드를 수정할 수 있습니다. 단, 아직 JPA Entity와 관련된 어노테이션은 작성하지 마세요.
    - [x] ERD와 클래스 다이어그램을 토대로 연관관계 매핑 정보를 표로 정리해보세요.(이 내용은 PR에 첨부해주세요.)
  
    |엔티티 관계|다중성| 방향성 |부모-자식 관계|연관관계의 주인|
    |---|---|-----|---|---|
    |User - UserStatus|1:1| 양방향 |부모: User, 자식: UserStatus|UserStatus|
    |User - ReadStatus|1:N| 단방향 |부모: User, 자식: ReadStatus|ReadStatus|
    |User - Message|1:N| 단방향 |부모: User, 자식: Message|Message|
    |Channel - Message|1:N| 단방향 |부모: Channel, 자식: Message|Message|
    |Channel - ReadStatus|1:N| 단방향 |부모: Channel, 자식: ReadStatus|ReadStatus|
    |User - BinaryContent|1:1| 양방향 |부모: User, 자식: BinaryContent|User|
    |Message - BinaryContent|1:N| 단방향 |부모: Message, 자식: BinaryContent|Message|
    - [x] JPA 주요 어노테이션을 활용해 ERD, 연관관계 매핑 정보를 도메인 모델에 반영해보세요. 
    - [x] ERD의 외래키 제약 조건과 연관관계 매핑 정보의 부모-자식 관계를 고려해 영속성 전이와 고아 객체를 정의하세요. 
- [x] 레포지토리와 서비스에 JPA 도입하기
  - [x] 기존의 Repository 인터페이스를 JPARepository로 정의하고 쿼리메소드로 대체하세요. 
  - [x] 영속성 컨텍스트의 특징에 맞추어 서비스 레이어를 수정해보세요. 
- [x] DTO 적극 도입하기
  - [x] Entity를 Controller 까지 그대로 노출했을 때 발생할 수 있는 문제점에 대해 정리해보세요. DTO를 적극 도입했을 때 보일러플레이트 코드가 많아지지만, 그럼에도 불구하고 어떤 이점이 있는지 알 수 있을거에요.(이 내용은 PR에 첨부해주세요.)
  - Entity를 Controller까지 그대로 노출했을 때 발생할 수 있는 문제점:
    - 민감한 정보까지 포함하여 response로 반환하므로 보안 문제가 발생한다.
    - Entity 구조가 변경되면 API 응답 형식도 변경되어 클라이언트에 영향을 미친다.
    - 불필요한 데이터까지 포함되어 response가 무거워지고 네트워크 트래픽이 증가한다.
    - Entity와 API 응답 형식이 결합되어 있어 코드의 유지보수가 어려워진다.
  - DTO를 적극 도입했을 때의 이점:
    - 필요한 데이터만 포함된 DTO를 사용함으로써 민감한 정보를 감출 수 있다.
    - DTO를 통해 API 응답 형식을 독립적으로 관리할 수 있어, Entity 구조 변경 시에도 API에 영향을 최소화할 수 있다.
    - 필요한 데이터만 포함된 DTO를 반환함으로써 네트워크 트래픽을 줄일 수 있다.
    - 유지보수 용이: Entity와 API 응답 형식이 분리되어 있어, 코드의 유지보수가 용이하다.
  - [x] 다음의 클래스 다이어그램을 참고하여 DTO를 정의하세요.
  - [x] Entity를 DTO로 매핑하는 로직을 책임지는 Mapper 컴포넌트를 정의해 반복되는 코드를 줄여보세요.
- [x] BinaryContent 저장 로직 고도화
  - [x] BinaryContent 엔티티는 파일의 메타 정보(fileName, size, contentType)만 표현하도록 bytes 속성을 제거하세요.
  - [x] BinaryContent의 byte[] 데이터 저장을 담당하는 인터페이스를 설계하세요.
  - [x] 서비스 레이어에서 기존에 BinaryContent를 저장하던 로직을 BinaryContentStorage를 활용하도록 리팩토링하세요.
  - [x] BinaryContentController에 파일을 다운로드하는 API를 추가하고, BinaryContentStorage에 로직을 위임하세요.
  - [x] 로컬 디스크 저장 방식으로 BinaryContentStorage 구현체를 구현하세요.
  - [x] discodeit.storage.type 값이 local 인 경우에만 Bean으로 등록되어야 합니다.
- [x] 페이징과 정렬
  - [x] 메시지 목록을 조회할 때 다음의 조건에 따라 페이지네이션 처리를 해보세요. 
  - [x] 일관된 페이지네이션 응답을 위해 제네릭을 활용해 DTO로 구현하세요. 
  - [x] Slice 또는 Page 객체로부터 DTO를 생성하는 Mapper를 구현하세요. 

### 심화 요구사항
- [x] N+1 문제
  - [x] N+1 문제가 발생하는 쿼리를 찾고 해결해보세요.
- [x] 읽기전용 트랜잭션 활용
  - [x] 프로덕션 환경에서는 OSIV를 비활성화하는 경우가 많습니다. 이때 서비스 레이어의 조회 메소드에서 발생할 수 있는 문제를 식별하고, 읽기 전용 트랜잭션을 활용해 문제를 해결해보세요. 
- [x] 오프셋 페이지네이션과 커서 페이지네이션 방식의 차이에 대해 정리해보세요.
- 오프셋 페이지네이션과 커서 페이지네이션의 차이:
  - 오프셋 페이지네이션:
      - 특정 페이지 번호와 페이지 크기를 기반으로 데이터를 조회한다.
      - SQL 쿼리에서 OFFSET과 LIMIT을 사용하여 데이터를 가져온다.
      - 페이지 점프를 위해 앞의 데이터를 스캔해야 하므로, 대용량 데이터 환경에서 성능 저하가 발생할 수 있다.
      - 데이터 변경 시 일관성이 깨질 수 있다.
      - 구현이 비교적 간단하다.
  - 커서 페이지네이션:
      - 마지막으로 조회한 항목의 고유 식별자(커서)를 기반으로 다음 데이터를 조회한다.
      - SQL 쿼리에서 WHERE 절을 사용하여 커서 이후의 데이터를 가져온다.
      - 대용량 데이터 환경에서 더 나은 성능을 제공하며, 데이터 변경에도 일관성을 유지할 수 있다.
      - 구현이 다소 복잡하다.
      - 페이지 점프가 불가능하다.
  - [x]  기존에 구현한 오프셋 페이지네이션을 커서 페이지네이션으로 리팩토링하세요. 
- [x] MapStruct 적용
  - [x] Entity와 DTO를 매핑하는 보일러플레이트 코드를 MapStruct 라이브러리를 활용해 간소화해보세요.
