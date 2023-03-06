jpa 시작.  

학습 목표  
Spring Data JPA가 무엇인지 이해할 수 있다.  
Spring Data JPA를 이용해서 데이터의 저장, 수정, 조회, 삭제 작업을 할 수 있다.  
JPA의 JPQL을 Spring Data JPA에서 사용할 수 있다.  

Spring Data JDBC와 Spring Data JPA는 Spring Data라는 패밀리 그룹에 포함.  
( Spring Data JDBC와 Spring Data JPA라는 기술은 사용하는 방식이 거의 유사)  

정리하자면, Spring Data JPA는 Spring Data 패밀리 기술 중 하나로써, JPA 기반의 데이터 액세스 기술을 좀 더 쉽게 사용할 수 있게 해주기 때문에  
데이터 액세스 계층의 구현에 있어 여러분의 개발 시간을 단축 시켜줄 것  

JPA의 경우 엔터프라이즈 Java 애플리케이션에서 관계형 데이터베이스를 사용하기 위해 정해 놓은 표준 스펙.  
(’이 기술은 무엇이고, 이 기술은 이렇게 이렇게 구현해서 사용하면 돼’ 라고 적어 놓은 기술 명세라고 생각)  
  
  
  
  
  
할 일들 표현.  
이전엔 Jdbc에서 db연결과 컨트롤러, 서비스, 리파지토리 등의 코드를 구현했다면  
  
엔티티 클래스를 Spring Data JPA에 맞게 수정하는 작업을 거칠것.  
사실 이 부분이 이번 챕터에서 가장 많이 변경되는 부분이기도 합니다.  
  
리포지토리(Repository) 인터페이스 구현  
놀라지 마세요. 코드 상으로 거의 바뀌는 부분이 없습니다.  
  
서비스 클래스 구현  
거의 바뀌지 않습니다.  

기타 기능 추가로 인해 수정 및 추가된 코드  
우리가 무얼 해야될지 계획이 세워졌으니 이제 Spring Data JPA 기술을 커피 주문 샘플 애플리케이션에 적용  
  
  
할 일들 순서 작성.  
1.엔티티 클래스 정의  
Spring Data JDBC에서 사용하는 엔티티 매핑 애너테이션은 JPA의 엔티티 매핑 애너테이션과 유사하지만 모듈 자체가 다름.
따라서 우리는 Spring Data JDBC에서 사용한 애너테이션을 제거하고 JPA에 맞는 애너테이션을 새로 추가해야 함.(엔티티 클래스에서.)  
(jdbc에서 사용했던 알고 있는 애너테이션은 설명 생략했음.)  
  
2.리포지토리(Repository) 인터페이스 구현  
(Spring Data JDBC에서의 리포지토리와 어떤 부분이 다른지에 중점을 두고 확인, JpaRepository 를 상속)  
(쿼리문의 많은 변경 SQL → JPQL, but 이전 방식으로 SQL도 사용가능한데 JPQL이 더 편해서 굳이 그럴 이유는 없지)  
  
3.Spring Data JPA를 적용한 여러분의 느낌은?  
이번 챕터의 코드는 서비스 계층과 엔티티 클래스, 리포지토리 인터페이스의 전체 코드를 포함 시키다보니  
상당히 길어보이지만 데이터 액세스 기술을 Spring Data JDBC에서 Spring Data JPA로 바꿨다고 해서 실제로 코드 자체가 대폭 변경된 부분은 없음.  
  
4.컨트롤러에도 추가해준내용 존재. (in 핸들러매서드)  
  
  
커피 정보에 대한 부분은 포함이 되지 않았습니다.  
이 부분은 여러분이 실습 과제에서 완성 해보는 시간을 가진다는 것 다시 한번 참고  


핵심 포인트  
Spring Data JPA는 Spring Data 패밀리 기술 중 하나로써, JPA 기반의 데이터 액세스 기술을 좀 더 쉽게 사용할 수 있게 해준다.  
JPA는 엔터프라이즈 Java 애플리케이션에서 관계형 데이터베이스를 사용하기 위해 정해 놓은 표준 스펙(사양 또는 명세, Specification)이다.  
Hibernate ORM은 JPA라는 표준 스펙을 구현한 구현체이다.  
Spring Data JPA는 JPA 스펙을 구현한 구현체의 API(일반적으로 Hibernate ORM)를 조금 더 쉽게 사용할 수 있도록 해주는 모듈이다.  
Spring에서는 애플리케이션이 특정 기술에 강하게 결합되지 않도록 Spring이 추구하는 PSA(일관된 서비스 추상화)를 통해   
개발자는 일관된 코드 구현 방식을 유지하도록 하고, 기술의 변경이 필요할 때 최소한의 변경만을 하도록 지원한다.  
JpaRepository를 상속하면 CrudRepository 기능을 포함한 JPA에 특화된 확장 기능들을 사용할 수 있다.  
JPQL은 JPA에서 지원하는 객체 지향 쿼리로써 데이터베이스의 테이블을 대상으로 조회 작업을 진행하는 것이 아니라 엔티티 클래스의 객체를 대상으로 객체를 조회한다.  
JPQL의 문법을 사용해서 객체를 조회하면 JPA가 내부적으로 JPQL을 분석해서 적절한 SQL을 만든 후에 데이터베이스를 조회하고, 조회한 결과를 엔티티 객체로 매핑한 뒤에 반환한다.  

https://coding-factory.tistory.com/523 this 설명.  
  
과제 후 느낀점.  
@Id 어노테이션 두개 있는데, persist쪽 사용해야함. 에러 해결.  