
# $\color{005caa}\colorbox{5ebfed}{KAILink}$

---

## Overview

<aside>
❓

W6가 어디지?

</aside>

<aside>
❓

인사동으로 오라고? 거긴 서울 아냐?

</aside>

> **KAIST가 처음**인 **새내기**와 **학점 교류생**들을 위해
**교내 건물 번호와 건물 이름, 재학생들이 부르는 별칭 및 연락처**를 
알려주는 어플리케이션입니다.
**넙죽이봇**에게 질문해 학사 정보도 알아볼 수 있어요!
> 

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/06d6719e-cc24-451d-a2fc-d1b216954a11/image.png)

<aside>
🛠

## Tech Stack

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

# $\color{005caa}\colorbox{5ebfed}{Details}$

---

## 0. 초기 화면(Splash Screen)

![splash_screen.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/898e6e94-bdf8-4559-ba46-6887427c6f21/splash_screen.gif)

`Interpolator`를 활용해 Animation이 있는 Splash Screen 구현

## 1. 마이페이지

:`Room db`를 이용한 사용자 정보 관리

### 프로필 이미지 변경

![profile_image.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/940b916b-3fcc-42c2-8665-e024cbe0c21f/profile_image.gif)

갤러리에서 프로필 이미지 선택, 저장

### 사용자 정보 변경

![name_update.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/b9a8c8c9-3921-4b0a-a7df-aa4cf995a7c0/name_update.gif)

`Dialog`를 통해 사용자 이름, 이메일 변경 및 저장

### 즐겨찾기 열람 및 관리

![bookmarks.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/6d27614b-5dce-422d-991a-e6adb9c1ab86/bookmarks.gif)

즐겨찾기한 연락처를 마이페이지에서 편하게 열람, 삭제 가능

## 2. 연락처

### 연락처 탭 전환

![2_스크롤.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/8e490d26-7875-4bf6-8d01-b6d4f89b8886/2_%EC%8A%A4%ED%81%AC%EB%A1%A4.gif)

`RecyclerView`를 이용해 다양한 학과 사무실 연락처 표시

### 연락처 검색

![search_contact.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/90cf8f8f-4353-4809-acde-6de0a2de7c43/search_contact.gif)

`SearchView`를 통해 학과 이름, 번호, 주소를 통한 검색 가능

### 정보 확인/전화

![2_다이얼로그.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/2fdcaea7-dabd-42bf-9090-abf4792b31d7/2_%EB%8B%A4%EC%9D%B4%EC%96%BC%EB%A1%9C%EA%B7%B8.gif)

`Dialog`를 통해 연락처 정보 확인, 버튼을 통해 전화 앱 연결

### 즐겨찾기 기능

![bookmark_contact.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/e36e1f67-106c-422b-ad26-a101ccb47a2c/bookmark_contact.gif)

즐겨찾는 연락처 추가, `Room db`에 저장 기능 지원

## 3. 갤러리

### 갤러리 탭 전환

![3_스크롤.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/a320be1d-5fed-490b-8c07-dba89ef086be/3_%EC%8A%A4%ED%81%AC%EB%A1%A4.gif)

`Glide`
drawable에 저장된 70여 개 이미지를 효율적으로 불러오기 위해 이용

`GridLayoutManager` 통해 2열 그리드 UI

### 갤러리 검색

![2_검색.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/c871cffb-2cd9-40ef-88b7-c2951d0b2818/2_%EA%B2%80%EC%83%89.gif)

`isIconified` 속성 설정을 통해 검색바 어느 곳을 눌러도 기능 수행할 수 있도록 강제

### 상세 정보 확인

![3_다이얼로그.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/d57bcf0e-36fc-4b02-ad2b-3d0387a71562/3_%EB%8B%A4%EC%9D%B4%EC%96%BC%EB%A1%9C%EA%B7%B8.gif)

`GoogleStaticMap API` 통한 지도 열람

`setBackgroundDrawable` 속성을 투명으로 설정해 버튼이 걸쳐진 UI 구현
`setCancelable`를 false로 지정해 X 버튼 클릭 시에만 닫히도록 강제

### 텍스트 공유

![3_공유.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/53d6bb3a-1f8c-42e1-bbea-714b844e7367/3_%EA%B3%B5%EC%9C%A0.gif)

`Intent` 를 통해 텍스트 공유 기능 구현

## 4. 넙죽이봇

### 시작 화면

![4_초기.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/159400e4-28db-4d63-a618-47c849fb891d/4_%EC%B4%88%EA%B8%B0.gif)

- `RelativeLayout`
메세지가 쌓여감에 따라
대화 내용이 위로 올라갈 수 있도록 xml 구성
대화창은 `RecyclerView`로 정의
- fragment / 말풍선
xml 별도 정의

### 학사 정보 질문

![4_질문.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/614a4f5d-051b-4667-b060-5b7f8423d592/4_%EC%A7%88%EB%AC%B8.gif)

- `okhttp3`
GPT API가 정의하는 포맷에 맞춰 json 요청을 구성해 전송
응답을 받아오는 동안 “Typing…” 메세지 송출
응답을 받아오면 대기 메세지를 지우고 응답 출력
- Adapter 클래스에서 발신자 체크 후 왼쪽(Bot) / 오른쪽(User)에 배치

### 프롬프트 해킹

![4_해킹.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/b33994da-a688-40aa-b8da-5fb49e0fe342/4_%ED%95%B4%ED%82%B9.gif)

- `Prompt Engineering`
학사정보와 무관한 질문을 하거나 
정해진 프롬프트를 무시하게 하려는 시도를 차단

# Others

### 이미지/건물 좌표 정보 크롤링 및 json 파일 작성 자동화를 위해 Python Script 작성

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/e704ce99-d356-4ff3-a16b-c8d098df24e0/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/4645a87c-6d36-4692-81a8-4e46ebc7b68e/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/cb2ad303-ae3a-4017-9ba1-676c879fc6fd/image.png)
