# README

## 1. 개요

---

```markdown
오픈 API를 이용한 "블로그 검색 서비스"
```

### 다운로드 링크 정보

- [https://github.com/idelen/surfing/blob/main/surfing-0.0.1-SNAPSHOT.jar](https://github.com/idelen/surfing/blob/main/surfing-0.0.1-SNAPSHOT.jar)

### 개발환경

- Java11
- Spring Boot 2.7.9
- h2 DB
- Swagger (API 문서 자동화)
- Junit5
- Mockito

## 2. 구현기능

---

### API

| Method | Path | Description |
| --- | --- | --- |
| Post | /api/v1/blogs/search/paging | 블로그 검색 결과를 Pagination 형태로 반환합니다. |
| Get | /api/v1/blogs/popular-search | 인기 검색어를 최대 10개까지 검색횟수와 함께 반환합니다. |

### 1. 블로그 검색

- 카카오 open api 로 먼저 검색을 시도하고 5xx 에러 발생 시, 네이버로 검색을 시도합니다.
- 검색 사이트의 추가를 고려하여, Service 와 Client를 각각 상속을 통해 추가하도록 하였습니다.
- 또한, 서비스의 추가 및 변경 시 Controller까지의 수정을 방지하기 위해 Controller 레이어와 Service 레이어 사이에 `BlogSearchApplication` 이라는 Application 레이어를 추가하였습니다.
- 추가로, 인기 검색어를 위해 검색 시도 시, 해당 검색어의 카운트를 올립니다.

`path` 

/api/v1/blogs/search/paging

`parameter`

query, sort, page, size 를 `BlogSearchQueryCondition` 객체로 받습니다.

또한, Custom Validation을 통해 각각의 값이 유효한지 검증합니다.

> query : isBlank()
sort : [”ACCURACY”, “RECENCY”] enum 타입으로 정의하여 해당 타입에 포함되는지 확인 (default : ACCURACY)
page : 1 ~ 50 사이의 수인지 확인 (default : 1)
size : 1 ~ 50 사이의 수인지 확인 (default : 10)
> 

`response`

기본 Page 객체를 ResponseEntity로 감싸 아래와 같이 반환합니다.

```json
{
    "content": [
        {
            "title": "<b>헤븐</b> 번즈 레드 - 소개",
            "contents": "이번 소개할 게임은 <b>헤븐</b> 번즈 레드 입니다. 한국에서의 공식 약칭은 &#39;헤번레드&#39; 라고 합니다. <b>헤븐</b> 번즈 레드 플랫폼 : 모바일, Steam * 현재 플레이 기간은 약 3주 입니다 * * 프롤로그 중의 플레이 내용을 일부 담고 있습니다. * <b>헤븐</b> 번즈 레드 인트로 화면 헤번레드 수집형 턴제 전투 RPG 입니다. 캐릭터를 모아서...",
            "url": "http://dyunace.tistory.com/7",
            "blogName": null,
            "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/A9JUjU9vhQZ",
            "datetime": "2023-02-27T15:05:38.000+09:00"
        }
    ],
    "pageable": {
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 1,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalPages": 800,
    "totalElements": 800,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "size": 1,
    "numberOfElements": 1,
    "empty": false
}
```

### 2. 인기 검색어 목록

- 검색횟수의 내림차순으로 검색어와 그 검색회수를 반환합니다.
- 요청 시 size를 입력받으며, 최대 10개까지 반환합니다.
- 동시성 이슈를 방지하기 위해, Transactional 및 비관적 락을 적용하였습ㄴ

`path` 

/api/v1/blogs/popular-search

`parameter`

size를 파리미터로 입력받으며, Custom Validation을 통해 값이 유효한지 검증합니다.

> size : 1 ~ 10 사이의 수인지 확인 (default : 10)
> 

`response`

`BlogSearchKeywordDto` 의 리스트 객체를 ResponseEntity로 감싸 아래와 같이 반환합니다.

```json
[
    {
        "keyword": "헤븐",
        "count": 4
    }
]
```