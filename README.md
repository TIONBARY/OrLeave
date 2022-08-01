# GIT & JIRA Convention

## Git Convention

|태그|내용|
|------|---|
|Feat|새로운 기능을 추가할 경우|
|Fix|버그를 고친 경우|
|Design|CSS 등 사용자 UI 디자인 변경|
|!BREAKING CHANGE|커다란 API 변경의 경우|
|!HOTFIX|급하게 치명적인 버그를 고쳐야 하는 경우|
|Style|코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우|
|Refactor|프로덕션 코드 리팩토링|
|Comment|필요한 주석 추가 및 변경|
|Docs|문서를 수정한 경우|
|Test|테스트 추가, 테스트 리팩토링(프로덕션 코드 변경X)|
|Chore|빌드 테스트 업데이트, 패키지 매니저를 설정하는 경우(프로덕션 코드 변경X)|
|Rename|파일 혹은 폴더명을 수정하거나 옮기는 작업만 수행한 경우|
|Remove|파일을 삭제하는 작업만 수행한 경우|
|Modify|파일 내용 수정|

## Git Message Rule

"Xxx: Xxx xxx"(한글만, 태그와 제목 첫글자 대문자)

# Jira Convention

|Issue Type|설명|예시|
|---|---|---|
|Epic|최상위 수준의 기능/작업 단위(프로젝트 전반 또는 여러 Sprint에 걸쳐 진행할 정도의 범위|회원 관리, 로그인 관리|
|Story|Epic에 대한 하위 Level 수준의 기능/작업 단위|회원 가입, 회원 정보 수정, 로그인..|
|Bug|프로젝트 개발/검증 중 발견된 버그|상품 검색 시 특정 상품이 조회되지 않는 문제|
|Task|개발에 직접 해당되지는 않으나 Sprint안에 포함하는 일|ERD작성, 테스트케이스 작성|
|Sub-task|위 Issue들과 관련하여 세부 단위 작업 등이 필요할 때||

## Jira Naming Rule

- Epic: [BE] 회원관리
- Story: 회원가입
- Bug: 회원가입 중복문제
- Task: README 개발자소개 추가할

## Sprint

- 예외가 없다면 Sub PJT 1, 2, 3을 Sprint로 생성(6주)
- 1인 하루 8포인트씩 5일 = 40 point

## Priority

- Highest: 이미 늦음
- High: 기한 안에 꼭 해야 함
- Medium: 해야 함
- Low: 괜찮
- Lowest: 안해도 괜찮
