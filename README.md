# 💟 Spring-Advanced Refactoring

- 파일 구조: DDD (사용 이유: 의존성 학습)

---

## ⭐ 코드 구성 소개
1. dto: record class, 변환 과정은 service계층에 둠
2. entity -> dto: 생성자 이용한 mapper 클래스 사용
3. dto -> entity: entity에서 정적 팩토리 메서드 사용
4. errorcode exception 종류별 분리
5. dip에 초점(mapper, repository, tokenprovider)
6. validation: service계층에 클래스 만들어 비즈니스 로직 진입 전 수행하도록 함

---

## 🌼 과정(블로그 링크)
1. Lv.6 의존성에 주목하여 리팩토링 하기: DIP https://roqkfchqh.tistory.com/119
2. Lv.3-4 & Lv.6 전체적인 리팩토링 일기 https://roqkfchqh.tistory.com/120
3. Lv.5 테스트코드 수정하기 https://roqkfchqh.tistory.com/121
4. Lv.4 N+1 문제 개선하기 https://roqkfchqh.tistory.com/122
5. Lv.6 리팩토링을 리팩토링하기 https://roqkfchqh.tistory.com/124
6. Lv.6 DIPDIPDIPDIPDIPDIP https://roqkfchqh.tistory.com/127


---