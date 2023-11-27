# MillieTest

**기술 요건**

- kotlin
- Clean Architecture, MVVM, MVP, MVI 중에 하나 사용
- RX, Coroutine 중에 하나 이상 사용
- Retrofit2 사용 추천
- Dagger Hilt 사용 추천

**요구 조건**

1. [News API – Search News and Blog Articles on the Web](https://newsapi.org/) 통해 api키를 발급 받습니다.
2. https://newsapi.org/v2/top-headlines?country=kr&apiKey={api-key} 이 api를 통하여 raw데이터를 가져옵니다.
3. 메인 화면에는 일반 폰에서는 1개의 row를 사용하여 아래로 스크롤 되는 UI를 작성합니다.
4. 가로가 600dp 이상 일때는 3개의 row를 사용하여 아래로 스크롤 되는 UI를 작성합니다.
5. api를 통해 받은 데이터는 로컬에 저장하여 api call이 실패하거나 오프라인 상태일때 저장된 데이터를 사용하여 UI를 표시합니다.
6. `urlToImage` 의 이미지 데이터 역시 로컬에 저장하여 오프라인 상태일때 저장된 데이터를 사용하여 이미지를 표시합니다. 또는 온라인 상태 일때도 저장된 데이터가 있는 경우 사용합니다.
7. 한개의 셀에 표시할 데이터는 `title`, `urlToImage`, `publishedAt` 입니다. 그외의 추가 ui는 자유롭게 표시해주세요.
8. 셀을 터치 시에 webView 가 전체를 덮는 Activity 를 표시합니다.
9. 물리 백버튼 이벤트에서 webView activity를 닫습니다.
10. 한번 진입했던 cell에는 `title` 의 text 컬러를 red 로 설정합니다.
- 이외의 ui, 인터렉션 등은 자유롭게 표시해 주세요.

## 선택한 기술 스펙

- **kotlin**
- **Clean Architecture**  *By Robert C. Martin (Uncle Bob)*
- **Coroutine**
- **Retrofit2**
- **Dagger Hilt**
- **Room**

![data flow](https://github.com/taiyoungkim/CalorieTracker/assets/38140436/1a8372d1-2311-407c-9f35-bcd9b76deefc)

![Architecture](https://github.com/taiyoungkim/CalorieTracker/assets/38140436/340a6283-58e5-4a8b-8b53-79d1a7d59cb4)

적용된 클린 아키텍처 의존성 방향과 흐름을 간단하게 이미지화 한 그림입니다.
해당 프로젝트는 마틴의 클린 아키텍쳐를 따라 만들어 졌습니다.
- Domain
- Data
- Presentation
크게 셋으로 나눠지며 각 역할에 맞게 network와 local(database) 외 필요한 데이터를 모듈화 했습니다.
