package net.datasa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("library_pu");


    // 영속성 컨텍스트에 저장
    public void persist() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Member member1 = new Member();
//        member1.setId(1L);
        member1.setName("홍길동");
        member1.setEmail("hong@gmail.com");

        entityManager.getTransaction().begin();

        // 영속성 컨텍스트에 저장
        System.out.println("member1 persist 전");
        entityManager.persist(member1);
        System.out.println("member1 persist 후");

        // 1차 캐시에서 조회
        Member findMember1 = entityManager.find(Member.class, 1L);
        Member findMember2 = entityManager.find(Member.class, 1L);

        // 동일성 보장
        System.out.println("findMember1 == findMember2: " + (findMember1 == findMember2));

        // flush 강제 실행
//        entityManager.flush();

        // 쓰기 지연
        Member member2 = new Member();
//        member2.setId(2L);
        member2.setName("김철수");
        member2.setEmail("철수@gmail.com");
        System.out.println("member2 persist 전");
        entityManager.persist(member2);
        System.out.println("member2 persist 후");
        // 여기까지 insert 쿼리가 실행되지 않음

        // 커밋하는 순간 insert 쿼리가 실행됨
        entityManager.getTransaction().commit();
    }

    // 변경 감지(Dirty Checking)
    public void update() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Member member = entityManager.find(Member.class, 1L);
        member.setName("이영희");

        // 변경 감지(Dirty Checking)
        // commit하는 순간 update 쿼리가 실행됨
        entityManager.getTransaction().commit();
    }

    // 엔티티 조회
    public void find() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Member member = entityManager.find(Member.class, 1L);
        System.out.println("member = " + member);

        entityManager.getTransaction().commit();
    }

    // 엔티티 삭제
    public void remove() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Member member = entityManager.find(Member.class, 1L);
        entityManager.remove(member);

        entityManager.getTransaction().commit();
    }

    // 책 등록
    public void bookPersist() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book1 = new Book();
        book1.setTitle("JPA 프로그래밍");
        book1.setIsbn("1234");
        book1.setStock(10);

        Book book2 = new Book();
        book2.setTitle("Spring 프로그래밍");
        book2.setIsbn("5678");
        book2.setStock(20);

        entityManager.getTransaction().begin();

        entityManager.persist(book1);
        entityManager.persist(book2);

        entityManager.getTransaction().commit();
    }

    // 대출 정보 등록
    public void loanPersist() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Book book1 = entityManager.find(Book.class, 1L);
        Book book2 = entityManager.find(Book.class, 2L);
        Member member = entityManager.find(Member.class, 1L);

        entityManager.getTransaction().begin();

        // 대출 정보 생성
        Loan loan1 = new Loan();

        // 대출 정보 설정
        loan1.setBook(book1);
        loan1.setMember(member);
        loan1.setLoanDate(LocalDate.now());
        loan1.setStatus(LoanStatus.LOAN);
        loan1.setReturnDate(LocalDate.now().plusDays(14));
        entityManager.persist(loan1);

        Loan loan2 = new Loan();
        loan2.setBook(book2);
        loan2.setMember(member);
        loan2.setLoanDate(LocalDate.now());
        loan2.setStatus(LoanStatus.LOAN);
        loan2.setReturnDate(LocalDate.now().plusDays(7));
        entityManager.persist(loan2);

        entityManager.getTransaction().commit();
    }

    // 대출 정보 조회
    public void loanFind() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Loan loan = entityManager.find(Loan.class, 1L);
        System.out.println("loan = " + loan);

        entityManager.getTransaction().commit();
    }

    // 회원이 빌린 책 조회
    public void memberLoanFind() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        // 단방향 매핑 시
//        Loan loan = entityManager.find(Loan.class, 1L);
//        System.out.println("loan = " + loan);

        // 양방향 매핑 시
        Member member = entityManager.find(Member.class, 1L);
        System.out.println("member = " + member);
        System.out.println("loans = " + member.getLoans());

        entityManager.getTransaction().commit();
    }

    public static void main(String[] args) {
        Main main = new Main();
        // CRUD
        main.persist();
//        main.find();
//        main.update();
//        main.remove();

        // 책 등록
        main.bookPersist();

        // 대출 정보 등록
        main.loanPersist();

        // 대출 정보 조회
        main.loanFind();

        // 회원이 빌린 책 조회
        main.memberLoanFind();

    }

}