
# KAILink

## Overview

> **KAIST가 처음**인 **새내기**와 **학점 교류생**들을 위해
**교내 건물 번호와 건물 이름, 재학생들이 부르는 별칭 및 연락처**를 
알려주는 어플리케이션입니다. <br>
**넙죽이봇**에게 질문해 학사 정보도 알아볼 수 있어요!
> 

## 🛠 Tech Stack

**Programming Language**

- Kotlin
- Python: `data crawling`

**Framework**

- Android Studio w/ Kotlin

**Database**

- Room DB(local)

**External API**

- Google Static Maps API
- ChatGPT API: `GPT-4o mini`

**VCS & Cooperation Tool**

- Git
- Github: `version control` `issue tracking`
</aside>

## Members

- [윤서진](https://www.notion.so/124c2f561ef843078f18fc8ab88c7a1c?pvs=21)
    - 숙명여자대학교 컴퓨터과학전공
    - Tab 2, Tab 4 구현

- [최준명](https://www.notion.so/370d7aa435814e0cb5c986488b725aff?pvs=21)
    - KAIST 전기및전자공학부 22학번
    - Tab 1, Tab 3 및 Splash Screen 구현

<br>
<br>


# Details

## 0. 초기 화면(Splash Screen)
`Interpolator`를 활용해 Animation이 있는 Splash Screen 구현


## 1. 마이페이지

`Room db`를 이용한 사용자 정보 관리

### 프로필 이미지 변경
갤러리에서 프로필 이미지 선택, 저장

### 사용자 정보 변경
`Dialog`를 통해 사용자 이름, 이메일 변경 및 저장

### 즐겨찾기 열람 및 관리
즐겨찾기한 연락처를 마이페이지에서 편하게 열람, 삭제 가능

## 2. 연락처

### 연락처 탭 전환
`RecyclerView`를 이용해 다양한 학과 사무실 연락처 표시

### 연락처 검색
`SearchView`를 통해 학과 이름, 번호, 주소를 통한 검색 가능

### 정보 확인/전화
`Dialog`를 통해 연락처 정보 확인, 버튼을 통해 전화 앱 연결

### 즐겨찾기 기능
즐겨찾는 연락처 추가, `Room db`에 저장 기능 지원

## 3. 갤러리

### 갤러리 탭 전환
`Glide`
drawable에 저장된 70여 개 이미지를 효율적으로 불러오기 위해 이용

`GridLayoutManager` 통해 2열 그리드 UI

### 갤러리 검색
`isIconified` 속성 설정을 통해 검색바 어느 곳을 눌러도 기능 수행할 수 있도록 강제

### 상세 정보 확인
`GoogleStaticMap API` 통한 지도 열람

`setBackgroundDrawable` 속성을 투명으로 설정해 버튼이 걸쳐진 UI 구현
`setCancelable`를 false로 지정해 X 버튼 클릭 시에만 닫히도록 강제

### 텍스트 공유
`Intent` 를 통해 텍스트 공유 기능 구현

## 4. 넙죽이봇

### 시작 화면
- `RelativeLayout`
메세지가 쌓여감에 따라
대화 내용이 위로 올라갈 수 있도록 xml 구성
대화창은 `RecyclerView`로 정의
- fragment / 말풍선
xml 별도 정의

### 학사 정보 질문
- `okhttp3`
GPT API가 정의하는 포맷에 맞춰 json 요청을 구성해 전송
응답을 받아오는 동안 “Typing…” 메세지 송출
응답을 받아오면 대기 메세지를 지우고 응답 출력
- Adapter 클래스에서 발신자 체크 후 왼쪽(Bot) / 오른쪽(User)에 배치

### 프롬프트 해킹
- `Prompt Engineering`
학사정보와 무관한 질문을 하거나 
정해진 프롬프트를 무시하게 하려는 시도를 차단

# Others

### 이미지/건물 좌표 정보 크롤링 및 json 파일 작성 자동화를 위해 Python Script 작성
