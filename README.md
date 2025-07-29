# SOLUX-DoranDoran-Server
솔룩스 도란도란 서버 레포지토리입니다.

## Main 기능
- 도서 리뷰 작성 및 답글 기능
- 책 주제별 토론 기능
- 인상 깊은 문구 공유

## 역할 분담

| 이름  | 역할분담 |
|-----| ------ |
| <a href="https://github.com/Eunbin618">장은빈</a> |홈·리뷰·감성 글귀|
| <a href="https://github.com/dmseong">김성희</a> |로그인·리뷰·마이페이지|

## 프로젝트 구조
```
├─java
│  └─com
│      └─app
│          └─dorandoran_backend
│              ├─auth
│              │  ├─controller
│              │  ├─dto
│              │  ├─jwt
│              │  ├─oauth
│              │  └─service
│              ├─books
│              │  ├─controller
│              │  ├─entity
│              │  └─repository
│              ├─config
│              ├─controller
│              ├─debate
│              │  ├─controller
│              │  ├─dto
│              │  ├─entity
│              │  ├─repository
│              │  └─service
│              ├─exception
│              ├─mypage
│              │  ├─controller
│              │  ├─dto
│              │  ├─entity
│              │  ├─repository
│              │  └─service
│              ├─quotes
│              │  ├─controller
│              │  ├─dto
│              │  ├─entity
│              │  ├─repository
│              │  └─service
│              └─reviews
│                 ├─controller
│                 ├─dto
│                 ├─entity
│                 ├─repository
│                 └─service
└─resources
    ├─static 
    ├─templates
    └─application.yml
    └─application-local.yml
    └─application-test.yml
``` 
