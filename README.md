# Hibernate CRUD Features
## 1. Create
* Hibernate 에서 제공해주는 Session 객체를 이용해서 DB 테이블에 데이터를 추가할 수 있다.
  * Session.save()
* SQL 쿼리문 INSERT를 수행해준다

> **CreateStudentDemo.**
> * `SessionFactory` : Session 인스턴스를 생성하기 위해 사용된다. 일반적으로 애플리케이션에는 단일 SessionFactory 인스턴스를 사용한다
>   * SessionFactory.`getCurrentSession()` : "현재" Session 의 인스턴스를 생성할 수 있다.
>     * 변경사항이 있을 경우 다시 getCurrentSession() 을 호출해야 한다.
> * `Configuration` : 모든 설정과 매핑소스를 하나의 API로 정의한 후 SessionFactory 를 생성하는데 사용한다
>   * Configuration.`configure()` : Hibernate 설정파일을 가져와서 매핑한다
>   * Configuration.`addAnnotatedClass()` : DB 테이블과 연결할 Entity 객체를 설정한다
>   * Configuration.`buildSessionFactory()` :  전달받은 정보를 바탕으로 SessionFactory 인스턴스를 생성해준다.
> * `Session` : 트랜잭션을 관리하거나 CRUD 기능을 사용할 수 있다.
>   * Session.`beginTransaction()` : 트랜잭션을 시작한다
>   * Session.`getTransaction()` : 세션과 관련된 트랜잭션 인스턴스을 불러온다.
>     * Transaction.`commit()` : 트랙잭션이 시작되고 나서 실패한 경우가 있으면 오류와 함께 DB가 시작지점으로 다시 롤백되고 아니면 정상 진행된다.
>   * Session.`save()` : Create
>   * Session.`get()` : Read
>   * Session.`update()` : Update
>     * update() 를 이용하지 않아도 setter 로 수행할 수 있다 
>   * Session.`delete()` : Delete
> * try (Object) : try 에서 객체를 선언하면 자동으로 `close()` 함수를 호출해준다.
>   * try-with-resources 문장
>   * AutoCloseable 를 구현한 객체만 올 수 있다.
>   * 여기서는 SessionFactory.close() 를 자동으로 호출해준다
```java
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDemo {
    public static void main(String[] args) {
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        // create session

        try (factory) {
            Session session = factory.getCurrentSession();

            // create 3 student objects
            System.out.println("Creating new student object...");
            Student tempStudent1 = Student.builder().firstName("John").lastName("Doe").email("paul@luv2code.com").build();
            Student tempStudent2 = Student.builder().firstName("Mary").lastName("Public").email("mary@luv2code.com").build();
            Student tempStudent3 = Student.builder().firstName("Bonita").lastName("Applebum").email("bonita@luv2code.com").build();

            // start a transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the students...");
            session.save(tempStudent1);
            session.save(tempStudent2);
            session.save(tempStudent3);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("Done!");
        }
    }
}
```

-----------------------------------------------------------

## 2. Read
* Hibernate 에서 제공해주는 Session 객체를 이용해서 DB 테이블에 데이터를 읽어올 수 있다.
  * Session.get()
* SQL 쿼리문 SELECT를 수행해준다

### Session.get() 을 이용하는 방법
> **ReadStudentDemo.java**
> * `Session.get()` : 매개변수로 전달한 Id(Primary key) 로 Entity 클래스의 인스턴스를 찾아서 반환해 준다.
>   * 첫번째 매개변수는 Entity의 타입이자 반환형을 결정한다
>   * 두번째 매개변수로 Id(Primary key)를 전달해준다
```java
  Student myStudent = session.get(Student.class, tempStudent.getId());
```

### HQL(Hibernate Query Language) 을 이용하는 방법
* 특정한 조건을 붙일때 사용한다
> **QueryStudentDemo.java**
> * Session.`createQuery` : 주어진 HQL 문장으로 Query 인스턴스를 생성한다
>   * Query.`getResultList()` : SELECT 쿼리를 실행하고 결과를 List 타입으로 반환해준다.
>     * 쿼리문을 잘보면 SELECT 가 생략된 쿼리문임을 알 수 있다.
>     * 쿼리문의 column 은 Entity 의 필드 이름을 써야한다
```java
  List<Student> theStudents = session.createQuery("from Student").getResultList();
  theStudents = session.createQuery("from Student s where s.lastName='Doe'").getResultList();
  theStudents = session.createQuery("from Student s where s.lastName='Doe' or s.firstName='Daffy'").getResultList();
  theStudents = session.createQuery("from Student s where s.email LIKE '%gmail.com'").getResultList();
```

-----------------------------------------------
