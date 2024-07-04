package com.hyoil.jpa.ch02;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * HelloWorldJPAToHibernateTest 클래스
 *
 * 특징:
 * - JPA EntityManagerFactory를 사용하여 Hibernate SessionFactory를 생성하는 테스트 클래스
 * - JPA와 Hibernate 간의 호환성을 보여줌
 * - Hibernate의 Session을 사용하여 데이터베이스 작업을 수행
 *
 * 차이점:
 * - JPA EntityManagerFactory를 기반으로 Hibernate SessionFactory를 생성
 * - JPA와 Hibernate 간의 상호 운용성을 보여줌
 * - Hibernate의 Session을 사용하여 데이터베이스 작업을 수행하지만 JPA 설정을 이용
 */
public class HelloWorldJPAToHibernateTest {

    private static SessionFactory getSessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Test
    public void storeLoadMessage() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("ch02");

        try (SessionFactory sessionFactory = getSessionFactory(emf)) {
            Session session = sessionFactory.openSession();

            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello World from JPA to Hibernate!");

            session.persist(message);

            session.getTransaction().commit();
            // INSERT into MESSAGE (ID, TEXT)
            // values (1, 'Hello World from JPA to Hibernate!')
            session.beginTransaction();

            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);
            criteriaQuery.from(Message.class);

            List<Message> messages = session.createQuery(criteriaQuery).getResultList();
            // SELECT * from MESSAGE

            session.getTransaction().commit();

            assertAll(
                    () -> assertEquals(1, messages.size()),
                    () -> assertEquals("Hello World from JPA to Hibernate!", messages.get(0).getText())
            );

            session.close();

        }
    }

}