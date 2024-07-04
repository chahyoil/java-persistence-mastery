package com.hyoil.jpa.ch02;

import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * HelloWorldHibernateToJPATest 클래스
 *
 * 특징:
 * - Hibernate 설정을 사용하여 JPA EntityManagerFactory를 생성하는 테스트 클래스
 * - Hibernate의 Configuration을 사용하여 EntityManagerFactory를 생성
 * - JPA API를 사용하여 데이터베이스 작업을 수행
 *
 * 차이점:
 * - Hibernate 설정을 기반으로 JPA EntityManagerFactory를 생성
 * - Hibernate와 JPA 간의 호환성을 보여줌
 * - JPA API를 사용하여 데이터베이스 작업을 수행하지만 Hibernate 설정을 이용
 */
public class HelloWorldHibernateToJPATest {

    private static EntityManagerFactory createEntityManagerFactory() {
        Configuration configuration = new Configuration();
        configuration.configure().addAnnotatedClass(Message.class);

        Map<String, String> properties = new HashMap<>();
        Enumeration<?> propertyNames = configuration.getProperties().propertyNames();
        while (propertyNames.hasMoreElements()) {
            String element = (String) propertyNames.nextElement();
            properties.put(element, configuration.getProperties().getProperty(element));
        }

        return Persistence.createEntityManagerFactory("ch02", properties);
    }

    @Test
    public void storeLoadMessage() {

        EntityManagerFactory emf = createEntityManagerFactory();

        try {

            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Message message = new Message();
            message.setText("Hello World from Hibernate to JPA!");

            em.persist(message);

            em.getTransaction().commit();
            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World from Hibernate to JPA!')

            List<Message> messages =
                    em.createQuery("select m from Message m", Message.class).getResultList();
            //SELECT * from MESSAGE

            assertAll(
                    () -> assertEquals(1, messages.size()),
                    () -> assertEquals("Hello World from Hibernate to JPA!", messages.get(0).getText())
            );

            em.close();

        } finally {
            emf.close();
        }
    }

}