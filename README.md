# 🧠 SignWave - 수화 번역 백엔드

Spring Boot, PostgreSQL, Docker 기반의 수화 번역 서버입니다.  
Android 앱과 연동되어 번역 결과 저장, 즐겨찾기, 회원 기능 등을 제공합니다.

---

## 🚀 실행 방법 (로컬)

### 1. signwave 프로젝트, signwave-ai 한 폴더ex)Spring_Project안에 넣기

### 2. Spring_Project폴더에 signwave-compose 폴더 생성
![image](https://github.com/user-attachments/assets/420516e5-103e-41cb-8303-8b4f0cef2a7b)

### 3.  signwave-compose 폴더에 docker-compose-yml파일 생성
![image](https://github.com/user-attachments/assets/941c333d-2b1f-49b6-aff8-897429982b68)

### 4. docker-compose-yml파일에 코드 넣기 코드는 signwave 노션에 자료->docker-compose-yml 에 있음
![image](https://github.com/user-attachments/assets/3c5ee658-f1ac-4b20-b45b-69bb770a0c5c)

### 5.signwave 프로젝트에 .env파일 추가 코드는 노션에 자료->singwave->.env 파일에 있음
![image](https://github.com/user-attachments/assets/f6a23de9-9865-4010-8b59-82d05b27b37d)

### 6.singnwave-ai 프로젝트에 .env파일 추가 코드는 노션에 자료->singwave-ai->.env 파일에 있음
![image](https://github.com/user-attachments/assets/aebbdb26-6c0d-47a7-ba6c-104b5b254bd3)

### 7.D:\Spring_Project\signwave-compose 경로로 cmd실행
![image](https://github.com/user-attachments/assets/f2539cf1-3800-4c55-bc67-bf1aa5c0228d)

### 8.docker-compose up --build실행
![image](https://github.com/user-attachments/assets/2db3a328-7eef-4dfe-9f36-de80a167593d)

### 9.최초실행이 아닌 경우 docker-compose down 해준 후에 docker-compose up --build실행
![image](https://github.com/user-attachments/assets/7adf52fc-718d-40a6-84c3-5732da9cb3e4)

### 10.http://localhost:8080/swagger-ui/index.html여기에 들어가면 스웨거 테스트 가능
![image](https://github.com/user-attachments/assets/d4d28db1-b4fe-47dc-a74e-854e06d52e77)

### 11.spring서버에서 fast api에 있는 api 다 가져와서 필요는 없겠지만 fast api 테스트 해보고싶으면 http://localhost:8000/docs여기에서 가능
![image](https://github.com/user-attachments/assets/da31936d-40ec-4089-af35-02df3fafbad5)


## 테스트 방법
### 1.회원가입

### 2.로그인 하면 토큰이 나오는데 그걸 복사하기
![image](https://github.com/user-attachments/assets/35e84e33-d332-4691-899a-ce3ac641d07e)

### 3.토큰 복사한거 /translate에서 오른쪽에 있는 자물쇠 모양 클릭
![image](https://github.com/user-attachments/assets/6012bbd6-976b-4830-b12e-b3cb51dfe5f8)
### 4. value에 토큰 넣기
![image](https://github.com/user-attachments/assets/55172b02-7083-42a8-88b7-504d39c15a17)

### 5.즐겨찾기 등록, 번역기록 전체 조회하고 싶으면 /translate 먼저 테스트해야함
![image](https://github.com/user-attachments/assets/70ef5a69-4276-4d77-a865-80f303f3664e)


