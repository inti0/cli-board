# CLI 자바 서비스 만들기

---
## 🎯 개요

이 과제는 **Java 콘솔 프로그램**으로 간단한 텍스트 게시판을 구현하는 프로젝트입니다.

Java 기본 문법, 클래스 및 객체지향 설계, 사용자 입력 처리, 그리고 데이터 저장 구조(ArrayList 등)를 연습합니다.

---

## 🧩 전체 기능

| 기능       | 설명                                          |
|----------|---------------------------------------------|
| 게시글 작성   | `write` 명령어 입력 시 제목/내용을 받아 새 게시글 생성         |
| 게시글 목록   | `list` 명령어 입력 시 모든 게시글을 정렬하여 출력(기본정렬 : 최신순) |
| 게시글 상세보기 | `detail [id]` 명령어로 특정 게시글 내용을 전체 확인         |
| 게시글 수정   | `update [id]` 명령어로 제목/내용을 수정                |
| 게시글 삭제   | `delete [id]` 명령어로 해당 글 삭제                  |
| 게시글 검색   | `search` 명령어로 제목 + 내용에 해당 keyword가 있는지 검색   |
| 종료       | `exit` 명령어로 프로그램 종료                         |

---

## ✅ 게시글 데이터 구조

게시글(Article)에 setter는 없으며 viewCount를 제외한 모든 필드는 불변이다.
- viewCount는 `increaseViewCount`로만 변경된다.

```java
import java.time.LocalDateTime;

class Article {
    private final int id;
    private final String title;
    private final String content;
    private final LocalDateTime regDate;
    private int viewCount = 0;
}
```

---

## ⚙️ 주요 클래스 및 파일 구조

```
src/
├─ Main.java         ← 진입점
├─ App.java          ← 프로그램 실행 로직
├─ article
│   ├─ controller
│   │   └─ ArticleController.java    ← 게시글 컨트롤러
│   ├─ domain
│   │   └─ Article.java              ← 게시글 데이터 클래스
│   ├─ repository
│   │   └─ ArticleRepository.java    ← 게시글 데이터 저장/관리 (메모리)
│   ├─ service
│   │   └─ ArticleService.java       ← 게시글 서비스
│   └─ view
│       ├─ ArticleInputView.java     ← 게시글 입력
│       └─ ArticleOutputView.java    ← 게시글 출력
```

DTO, enum과 같은 부수적인 클래스는 해당 객체를 **생성하는** 계층에 같이 두었음.

---

## 🧠 메서드 설계 예시

| 메서드명 | 설명 |
| --- | --- |
| `run()` | 앱 실행 루프 (입력 대기 및 명령어 처리) |
| `writeArticle()` | 게시글 작성 처리 |
| `listArticles()` | 게시글 목록 출력 |
| `showDetail(int id)` | 특정 글 상세 내용 출력 |
| `updateArticle(int id)` | 게시글 수정 처리 |
| `deleteArticle(int id)` | 게시글 삭제 처리 |

---

## 💬 실행 예시

```
명령어: write
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
=> 게시글이 등록되었습니다.

명령어: list
기본 정렬(최신순)으로 나타냅니까? (Y/N)
Y
시간순 번호순 중 하나를 입력하세요.
시간순
오름차순 내림차순 중 하나를 입력하세요.
오름차순

번호 | 제목       | 등록일
-----------------------------
1    | 자바 공부  | 2025-08-03

명령어: detail 1
번호: 1
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
등록일: 2025-08-03

명령어: update 1
제목 (현재: 자바 공부): Java 게시판
내용 (현재: 자바 텍스트 게시판 만들기): 콘솔 기반으로 구현
=> 게시글이 수정되었습니다.

명령어: delete 1
=> 게시글이 삭제되었습니다.

명령어: exit
프로그램을 종료합니다.
```

---

## 🎯 개발 포인트 요약

| 기술 요소     | 적용                                     |
|-----------|----------------------------------------|
| 입력 처리     | `Scanner` 활용하여 명령어/데이터 입력 받기           |
| 게시글 관리    | `ArrayList<Article>`로 게시글 목록 관리        |
| 날짜 처리     | `LocalDate.now()`,  `DateFormatter` 활용 |
| 정렬        | 최신글(기본정렬)이 위로 오도록 `list()` 역순 출력       |
| 객체지향 설계   | 게시글 클래스 분리, 메서드 역할 분리                  |
| 단위 테스트    | 클래스별로 단위 테스트 작성                        |
| App 통합테스트 | 어플리케이션 통합 테스트                          |

---

## ✅ 추가 기능 구현 예시

- [ ]  게시글 조회수 기능 (`viewCount` 필드 추가)
- [ ]  게시글 검색 기능 (`search`)
- [ ]  게시글 정렬 옵션 (날짜순, 번호순, 오름차순, 내림차순)

---

## 🔍 부족한 부분 & 고민한 부분

- 시간순 정렬 기능을 만들었지만, 현재 article 객체는 등록일 필드만 있어서 게시글 작성 이후로 시간변경은 이루어지지 않음.
  - 따라서, 번호순(ID순) 정렬과 완전히 동일한 기능을 함.
  - modifyDate필드와 setter, update시 변경하도록 하여 기능 구현 가능
- 컨트롤러 단위테스트 코드가 부족함 -> AI에게 시켰음
- 통합테스트 코드가 부족함
- DTO record 와 SortStrategy enum을 어느 패키지에 두어야 할지 아직도 고민이 됨
- outputView 비즈니스로직에 의해 출력되는 부분만 테스트할까 고민 했는데 정적인 출력도 테스트 해야할지 싶음 (우선순위는 밀리는 듯)


---

## ✅ 과제 제출 방법

- GitHub 저장소에 프로젝트 업로드(개인 레포)
- `README.md`에 기능 설명, 실행 예시, 명령어 요약 포함
- 폴더 구조 정리 및 주석 또는 문서화 권장

코드를 기능별로 잘 나누고, 가독성을 고려하여 작성해주세요.