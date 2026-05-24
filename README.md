# class-enrollment

## 프로젝트 개요
라이브클래스 서비스의 수강 신청 시스템 백엔드 API입니다.

크리에이터(강사)가 강의를 개설하고 클래스메이트(수강생)가 수강 신청 및 취소, 결제 확정을 할 수 있는 서비스로, 아래 핵심 비즈니스 규칙을 구현했습니다.

- 강의 상태: `DRAFT` → `OPEN` → `CLOSED` 흐름으로 관리
- 수강 신청 상태: `PENDING` → `CONFIRMED` → `CANCELLED` 흐름으로 관리
- 정원 초과 신청 거부 및 동시성 처리
- 결제 확정 후 7일 이내 수강 취소 가능

**선택 구현 사항**
- 수강 취소 시 취소 가능 기간 제한 (결제 후 7일 이내) ✅
- 강의별 수강생 목록 조회 (크리에이터 전용) ✅
- 신청 내역 페이지네이션 ✅
## 기술 스택
- Java 21
- Spring Boot 4.0.6
- Spring Data JPA
- MySQL
- Docker / Docker Compose
- Gradle
## 실행 방법
```bash
# 1. MySQL 컨테이너 실행
docker-compose up -d

# 2. 애플리케이션 실행
./gradlew bootRun
```
## 요구사항 해석 및 가정
- 인증 / 인가 없이 `userId`, `creatorId`를 RequestParam으로 전달하는 방식으로 구현
- 강의 등록 시 기본 상태는 `DRAFT`로 설정
- 결제 확정은 외부 결제 시스템 연동 없이 단순 상태 변경(`PENDING` → `CONFIRMED`)으로 대체
- 취소 가능 기간은 결제 확정 후 7일 이내로 설정
- 강의별 수강생 목록 조회는 해당 강의의 크리에이터만 가능
## 설계 결정과 이유
- **PATCH 사용**: 강의 수정 시 부분 업데이트가 많을 것으로 예상해 PUT 대신 PATCH 선택
- **비관적 락(Pessimistic Lock)**: 수강 신청 서비스 특성상 인기 강의의 경우 짧은 시간에 많은 트래픽이 몰릴 가능성이 높기에 동시성 문제가 발생할 것으로 예상했습니다. 이에 충돌이 발생하기 전에 미리 락을 거는 비관적 락 방식을 선택해 정원 초과를 방지했습니다.
- **정적 팩토리 메서드(from)**: Response DTO에 변환 로직을 캡슐화해 서비스 코드 간결화 및 응집도 향상
- **연관관계 객체 참조**: Enrollment 도메인에서 User, Lecture를 객체로 참조해 JPA 연관관계 활용
  - User 1 : N Enrollment
  - User 1 : N Lecture (creator_id 기준, 크리에이터인 경우에만 해당)
  - Lecture 1 : N Enrollment
## 미구현 / 제약사항
- 인증/인가 미구현 (userId, creatorId를 파라미터로 전달하는 방식으로 대체)
- 대기열(waitlist) 기능 미구현
- 서비스 단위 핵심 로직 테스트만 구현했으며, 일부 비즈니스 로직 테스트는 미구현
- 비관적 락 트레이드오프: 모든 수강 신청 요청마다 락이 걸려 처리량이 낮아질 수 있으므로 실제 서비스라면 낙관적 락이나 대기열 방식을 고려할 것
- 수강 신청 및 강의 등록 후 생성된 ID를 응답으로 반환하면 UX 측면에서 편리하나 미구현
## AI 활용 범위
- ERD, API 명세서 설계 방향성 피드백을 AI에게 받았습니다.
- 코드 구현은 직접 했으나, 코드 리뷰 및 개선 방향 피드백은 AI를 활용했습니다.
- 동시성 처리(비관적 락) 구현 방향을 AI에게 도움 받았습니다.
- 테스트 코드 작성 방향 및 구조에 대해 AI에게 도움 받았습니다.
- 유저 생성 시 이름은 실 서비스에서 중복이 있을 수 있어 이메일을 유니크 식별자로 활용했으며, 이메일 중복 검증 로직만 추가했습니다.
- 공통 코드(예외 처리, 공통 응답 형식 등)는 부트캠프 강의에서 제공받은 코드를 기반으로 활용했습니다.
## API 목록 및 예시
<img width="965" height="649" alt="API 명세서" src="https://github.com/user-attachments/assets/8cff08ae-9f42-4724-88fc-6dbc85924a83" />


### 강의 목록 조회 API 명세서 예시 (Request, Response)
<img width="786" height="206" alt="API 명세서 Request DTO" src="https://github.com/user-attachments/assets/5e984f0e-c14a-4243-95f1-8c7425f1f338" />
<img width="765" height="718" alt="API 명세서 Response DTO" src="https://github.com/user-attachments/assets/9ca23460-11fa-496c-885e-2562767d19c2" />


## 데이터 모델 설명
<img width="1016" height="547" alt="ERD" src="https://github.com/user-attachments/assets/01293f1c-686a-4384-b115-447ec0c72c2a" />

## 테이블 설명
- **User**: 크리에이터(CREATOR) / 수강생(STUDENT) 역할 구분
- **Lecture**: 강의 정보 및 상태(DRAFT/OPEN/CLOSED) 관리, 크리에이터 참조
- **Enrollment**: 수강 신청 정보 및 상태(PENDING/CONFIRMED/CANCELLED) 관리, User와 Lecture 참조
## 테스트 실행 방법
```bash
# Docker Compose로 MySQL 먼저 실행 후
./gradlew test
```
