# 📍 도란도란 ( DoranDoran )

**🗓 프로젝트 기간: 2025.3 ~ 2025.8**

책 읽는 문화를 함께 만들어가는 독서 커뮤니티 Android 애플리케이션입니다.
독서에 대한 흥미를 유도하고, 책을 좋아하는 사람들이 소통하며 함께 성장할 수 있는 공간을 제공합니다.

## 주요 기능
- **도서 리뷰 작성 및 소통**: 읽은 책에 대한 리뷰를 작성하고, 다른 독자들과 댓글을 통해 자유롭게 의견을 나눌 수 있습니다.
- **주제별 토론 기능**: 서책의 주제나 메시지를 중심으로 다양한 시각을 공유하며 깊이 있는 토론을 할 수 있습니다.
- **인상 깊은 문구 공유**: 마음을 울린 책 속 문장을 기록하고 공유함으로써 공감과 영감을 나눌 수 있습니다.

## 역할 분담

| 이름  | 역할분담 |
|-----| ------ |
| <a href="https://github.com/Eunbin618">장은빈</a> |홈·리뷰·감성 글귀|
| <a href="https://github.com/dmseong">김성희</a> |회원가입/로그인·마이페이지·aws 서버|

## 파이프라인 구조
<img width="1900" height="800" alt="제목을-입력해주세요_-001" src="https://github.com/user-attachments/assets/db2d81f6-f581-4fc7-a361-928732861884" />

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
    ├─application.yml
    ├─application-local.yml
    └─application-test.yml
``` 
