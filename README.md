#  WeCare Server
> Server repository for WeCare service

### ⚙️ Project Structure
<details>
<summary>Code Structure</summary>
<div>

```
.
├── Dockerfile
├── README.md
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── ollie
    │   │       └── wecare
    │   │           ├── WecareApplication.java
    │   │           ├── challenge
    │   │           │   ├── controller
    │   │           │   │   └── ChallengeController.java
    │   │           │   ├── dto
    │   │           │   │   ├── AttendChallengeReq.java
    │   │           │   │   ├── GetAttendanceRes.java
    │   │           │   │   ├── GetChallengeAdsRes.java
    │   │           │   │   ├── GetChallengesRes.java
    │   │           │   │   ├── PostChallengeReq.java
    │   │           │   │   └── PostMyChallengeReq.java
    │   │           │   ├── entity
    │   │           │   │   ├── Challenge.java
    │   │           │   │   └── ChallengeAttendance.java
    │   │           │   ├── repository
    │   │           │   │   ├── ChallengeAttendanceRepository.java
    │   │           │   │   └── ChallengeRepository.java
    │   │           │   └── service
    │   │           │       └── ChallengeService.java
    │   │           ├── common
    │   │           │   ├── base
    │   │           │   │   ├── BaseEntity.java
    │   │           │   │   ├── BaseException.java
    │   │           │   │   ├── BaseResponse.java
    │   │           │   │   └── BaseResponseStatus.java
    │   │           │   ├── configuration
    │   │           │   │   ├── AppConfiguration.java
    │   │           │   │   ├── RedisConfiguration.java
    │   │           │   │   ├── WebConfiguration.java
    │   │           │   │   └── WebSecurityConfiguration.java
    │   │           │   ├── constants
    │   │           │   │   ├── Constants.java
    │   │           │   │   └── RequestURI.java
    │   │           │   ├── enums
    │   │           │   │   └── Role.java
    │   │           │   ├── exception
    │   │           │   │   ├── ErrorResponse.java
    │   │           │   │   └── GlobalExceptionHandler.java
    │   │           │   └── jwt
    │   │           │       ├── JwtAuthenticationFilter.java
    │   │           │       └── JwtExceptionFilter.java
    │   │           ├── program
    │   │           │   ├── controller
    │   │           │   │   └── ProgramController.java
    │   │           │   ├── dto
    │   │           │   │   ├── DateDto.java
    │   │           │   │   ├── GetProgramRes.java
    │   │           │   │   └── PostProgramReq.java
    │   │           │   ├── entity
    │   │           │   │   ├── Program.java
    │   │           │   │   └── Tag.java
    │   │           │   ├── repository
    │   │           │   │   └── ProgramRepository.java
    │   │           │   └── service
    │   │           │       └── ProgramService.java
    │   │           └── user
    │   │               ├── controller
    │   │               │   └── UserController.java
    │   │               ├── dto
    │   │               │   ├── CenterListDto.java
    │   │               │   ├── EditNicknameRequest.java
    │   │               │   ├── EditPasswordRequest.java
    │   │               │   ├── JwtDto.java
    │   │               │   ├── LoginIdRequest.java
    │   │               │   ├── LoginRequest.java
    │   │               │   ├── LoginResponse.java
    │   │               │   ├── MyPageResponse.java
    │   │               │   ├── NicknameRequest.java
    │   │               │   ├── ReissueTokenRequest.java
    │   │               │   ├── SignOutRequest.java
    │   │               │   ├── SignupRequest.java
    │   │               │   ├── SignupViewResponse.java
    │   │               │   └── TokenResponse.java
    │   │               ├── entity
    │   │               │   ├── Center.java
    │   │               │   └── User.java
    │   │               ├── repository
    │   │               │   ├── CenterRepository.java
    │   │               │   └── UserRepository.java
    │   │               └── service
    │   │                   ├── AuthService.java
    │   │                   ├── RedisService.java
    │   │                   └── UserService.java
    │   └── resources
    │       └── application.yml
    └── test
        └── java
            └── ollie
                └── wecare
                    ├── WecareApplicationTests.java
                    └── common
                        └── jwt
```

</div>
</details>

<br>

### 🛠️ Tech stacks
#### Backend
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=spring security&logoColor=white"> <img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)  <img src="https://img.shields.io/badge/hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> 

#### Database
<img src="https://img.shields.io/badge/aws rds-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">

#### Cloud
<img src="https://img.shields.io/badge/AWS ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">

#### CD
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/github actions-2088FF?style=for-the-badge&logo=github actions&logoColor=white">

#### Develop tools
<img src="https://img.shields.io/badge/intelliJ-000000?style=for-the-badge&logo=intellij idea&logoColor=white"> <img src="https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<br>
<br>


### 🎯 Contributors
|Joonghyun Kim|Haeun Yoon|
|:---:|:---:|
|<img src="https://github.com/JoongHyun-Kim.png" width="180" height="180" >|<img src="https://github.com/Haeun-Y.png" width="180" height="180" >|
| **Backend Developer** | **Backend Developer** |
