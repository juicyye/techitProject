## 테킷 미니프로젝트 보드만들기
### 사용기술
- 스프링 JDBC
 -  MYSQL
 -  Spring Security
 -  Spring AOP
### 1. 요구사항
- 글을 올릴수 있는 게시판 만들기
- 글을 쓸때 이름, 제목, 내용, 비밀번호를 치고
- 비밀번호를 맞아야 수정과 삭제가 가능하다

### 2. 구현기능
- 검색: 제목 + 내용 또는 작성자 이름으로 검색이 가능하다
- BindingResult를 메시지를 통해 한꺼번에 관리할수 있게 만들었다
- Spring Security를 활용해 로그인이 된 사용자는 ADMIN이고 전체 보드를 삭제할 수 있다 개인 Post는 지우지 못
- 메세지를 통해 추후 국제화도 가능할수 있다
- 수정이나 삭제가 되면 수정이 완료되었습니다 창이 뜨고 2초 뒤에 사라진다
- 수정된 글은 수정됐다고 뜨고 날짜가 바뀐다
- 요청 Log와 시간을 책정

### 3. 하고싶었던 기능들
- 매핑: user와 매핑해서 좋아요나 댓글을 하고 싶었지만 JDBC는 매핑이 어려워서 시간 문제로 패스
- 스프링 시큐리티 인가: 위와 같이 매핑이 안돼서 의미가 없을거라 생각해서 패스
