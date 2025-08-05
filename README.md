# CLI 자바 서비스 만들기

---

# ✅ CLI 자바 서비스 만들기 (콘솔 기반)

## 🎯 개요

이 과제는 **Java 콘솔 프로그램**으로 간단한 텍스트 게시판을 구현하는 프로젝트입니다.

Java 기본 문법, 클래스 및 객체지향 설계, 사용자 입력 처리, 그리고 데이터 저장 구조(ArrayList 등)를 연습합니다.

---

## 🧩 전체 기능 예시

| 기능 | 설명 |
| --- | --- |
| 게시글 작성 | `write` 명령어 입력 시 제목/내용을 받아 새 게시글 생성 |
| 게시글 목록 | `list` 명령어 입력 시 모든 게시글을 번호순으로 출력 |
| 게시글 상세보기 | `detail [id]` 명령어로 특정 게시글 내용을 전체 확인 |
| 게시글 수정 | `update [id]` 명령어로 제목/내용을 수정 |
| 게시글 삭제 | `delete [id]` 명령어로 해당 글 삭제 |
| 종료 | `exit` 명령어로 프로그램 종료 |

---

## ✅ 게시글 데이터 구조 예시

```java
import java.time.LocalDateTime;

class Article {
    int id;
    String title;
    String content;
    LocalDateTime regDate;
}
```

---

## ⚙️ 주요 클래스 및 파일 구조 예시

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
│   │   └─ ArticleRepository.java    ← 게시글 데이터 저장/관리
│   ├─ service
│   │   └─ ArticleService.java       ← 게시글 서비스
│   └─ view
│       ├─ ArticleInputView.java     ← 게시글 입력
│       └─ ArticleOutputView.java    ← 게시글 출력
└─ util
    └─ Rq.java        ← 요청 유효성 검사
```

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
| `getCurrentDate()` | 현재 날짜 리턴 (yyyy-MM-dd 형식) |

---

## 💬 실행 예시

```
명령어: write
제목: 자바 공부
내용: 자바 텍스트 게시판 만들기
=> 게시글이 등록되었습니다.

명령어: list
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

| 기술 요소 | 적용 |
| --- | --- |
| 입력 처리 | `Scanner` 활용하여 명령어/데이터 입력 받기 |
| 리스트 관리 | `ArrayList<Article>`로 게시글 목록 관리 |
| 날짜 처리 | `LocalDate.now()` 또는 `SimpleDateFormat` 활용 |
| 정렬 | 최신글이 위로 오도록 `list()` 역순 출력 |
| 객체지향 설계 | 게시글 클래스 분리, 메서드 역할 분리 |

---

## ✅ 추가 기능 구현 예시

- [ ]  게시글 조회수 기능 (`count` 필드 추가)
- [ ]  게시글 검색 기능 (`search [keyword]`)
- [ ]  파일 저장/불러오기 (`BufferedWriter`, `BufferedReader` 등 활용)
- [ ]  게시글 정렬 옵션 (날짜순, 번호순 등)

---

## ✅ 과제 제출 방법

- GitHub 저장소에 프로젝트 업로드(개인 레포)
- `README.md`에 기능 설명, 실행 예시, 명령어 요약 포함
- 폴더 구조 정리 및 주석 또는 문서화 권장

코드를 기능별로 잘 나누고, 가독성을 고려하여 작성해주세요.