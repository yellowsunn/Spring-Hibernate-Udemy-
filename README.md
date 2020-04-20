# Hibernate Configuration with Annotations
## 1. Hibernate 설명
### Hibernate는 객체 모델링(Object Oriented Modeling)과 관계형 데이터 모델링(Relational Data Modeling) 사이의 불일치를 해결해 주는 ORM(Object Relation Mapping) 도구다.

## 2. Hibernate 설정파일 생성하기
* 기본 설정파일 이름은 _Hibernate.cfg.xml_ 이다
> **hibernate.cfg.xml**
``` xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <!-- JDBC Database connection settings-->
        <property name="connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="connection.url">jdbc:mysql://127.0.0.1:3306/hb_student_tracker?useSSL=false</property>
        <property name="connection.username">hbstudent</property>
        <property name="connection.password">hbstudent</property>

        <!-- JDBC connection pool settings ... using built-in test pool -->
        <property name="connection.pool_size">1</property>

        <!-- Select our SQL dialect -->
        <property name="dialect">org.hibernate.dialect.MySQLDialect</property>

        <!-- Echo the SQL to stdout-->
        <property name="show_sql">true</property>

        <!-- Set the current session context -->
        <property name="current_session_context_class">thread</property>
    </session-factory>
</hibernate-configuration>
```

## 3. Entity 클래스 생성
* 데이터 베이스 테이블과 1:1 맵핑할 클래스를 @Entity 로 생성한다
> **Student.java**
> * `@Entity` : 테이블과 링크될 클래스임을 나타낸다
>   * 기본값으로 클래스의 이름은 다음과 같은 _ 형태로 테이블 이름을 매칭한다   
>     ex) SalesManger.java -> sales_manager 테이블
> * `@Table` : @Entity 만 있어도 테이블을 맵핑하지만 특정 이름을 가진 테이블과 맵핑할 때 사용한다
> * `@Column` : 테이블의 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다.
    * 기본값 외에 추가로 변경이 필요한 옵션이 있으면 사용한다
> * `@Id` : primary key 값을 지정할 수 있다
``` java
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Builder
    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
```

