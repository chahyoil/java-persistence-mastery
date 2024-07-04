package com.hyoil.jpa.ch02;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * HelloWorldHibernateTest 클래스
 *
 * 특징:
 * - Hibernate를 직접 사용하여 데이터베이스와 상호 작용하는 테스트 클래스
 * - Hibernate의 SessionFactory를 사용하여 세션을 생성하고 트랜잭션을 관리
 * - CriteriaQuery를 사용하여 데이터를 조회
 *
 * 차이점:
 * - Hibernate 전용 API를 사용하여 데이터베이스 작업을 수행
 * - Hibernate의 세션 관리와 트랜잭션 처리를 직접 다룸
 * - Hibernate의 Criteria API를 사용하여 쿼리를 수행
 */
public class HelloWorldHibernateTest {

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    public void storeLoadMessage() {

        try (SessionFactory sessionFactory = createSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Message message = new Message();
            message.setText("Hello World from Hibernate!");

            session.persist(message);

            session.getTransaction().commit();
            // INSERT into MESSAGE (ID, TEXT)
            // values (1, 'Hello World from Hibernate!')
            session.beginTransaction();

            CriteriaQuery<Message> criteriaQuery = session.getCriteriaBuilder().createQuery(Message.class);
            criteriaQuery.from(Message.class);

            List<Message> messages = session.createQuery(criteriaQuery).getResultList();
            // SELECT * from MESSAGE

            session.getTransaction().commit();

            assertAll(
                    () -> assertEquals(1, messages.size()),
                    () -> assertEquals("Hello World from Hibernate!", messages.get(0).getText())
            );
        }
    }
}