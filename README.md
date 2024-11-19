# backend

<hr style="border: 1px solid gray">

# github flow

---

## 깃 기능 추가 규칙

---

### 1. git checkout main + git pull origin main

- 원격 main 브랜치의 변경사항을 로컬 main 브랜치에 반영합니다.
- `주의` : 반영 안 할 시 작업중인 코드를 다시 reset 해야하니, 반드시 반영합니다.

---

### 2. git checkout -b feature/{추가할 기능}

- 추가 할 기능을 만드는 브랜치를 main 브랜치에서 추가합니다.

>refactor/{리팩터링 할 기능}

>fix/{버그를 고칠 기능}

- 위의 내용과 같은 브랜치를 추가할 수 있습니다.

---

### 3. git checkout feature/{추가할 기능}

- 2번으로 만들어진 브랜치로 이동합니다.

---

### 4. 작업하기

- (**https://icanchangeworld.tistory.com/68**) 의 커밋 컨벤션을 규칙으로 소기능 단위로 커밋합니다.

---

### 5. git push origin feature/{추가할 기능}

- 원격 브랜치에 push 합니다.
- 이때부터, 원격 리포지토리에서 내가 만든 브랜치를 볼 수 있게 됩니다.

---

### 6. 깃허브에서 Pull Request 보내기

1. 깃허브 상단 `Code | Issues | Pull request | Actions | Projects ...` 와 같은 요소들이 있는 메뉴바에서 `Pull request` 클릭
2. 초록색 버튼 `New pull request` 클릭
3. `base:main <- compare:main`이라 되어있는 버튼을 클릭해 `base:main <- compare:feature/{추가할 기능}` 으로 변경
4. `Create pull request` 클릭
5. 카카오톡 등으로 pr 검사를 요청합니다. `주의` : 'merge' 과정 회의가 필요하니 말 없이 올리는 행위는 금지합니다.
6. merge 완료

---

### 7. 원격, 로컬 저장소 삭제

- 정상적으로 merge 되었다면, 저장소를 삭제해야 합니다.
1. git checkout main : 메인 이동
2. git branch -d feature/{추가할 기능} : 로컬 브랜치 삭제
3. git push --delete origin feature/{추가할 기능} : 원격 브랜치 삭제

---

### 8. 완료 후 혹시 모를 pull 안함 이슈를 위해 pull

- 다음에 기능 개발 시 원격 main 브랜치의 반영 요소를 로컬 main 브랜치에 반영하지 않을 가능성이 높습니다.(내가 그럽디다.)
- git checkout main + git pull origin main

---

### 9. 이제 다시 뺑이치러 갑니다.

- `주의` : 가장 중요합니다.
- 다소 기분 나쁜 문장이 있을 수 있으나, 원래 성격이 이런가보다 하고 넘어갑니다.

<hr style="border: 1px solid gray">