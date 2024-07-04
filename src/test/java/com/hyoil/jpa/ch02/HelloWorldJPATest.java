package com.hyoil.jpa.ch02;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;



/**
 * HelloWorldJPATest 클래스
 *
 * 특징:
 * - JPA를 직접 사용하여 데이터베이스와 상호 작용하는 테스트 클래스
 * - JPA의 EntityManagerFactory를 사용하여 EntityManager를 생성하고 트랜잭션을 관리
 * - JPQL(JPA Query Language)을 사용하여 데이터를 조회하고 수정
 *
 * 차이점:
 * - 순수 JPA API를 사용하여 데이터베이스 작업을 수행
 * - JPA의 EntityManager와 JPQL을 사용하여 쿼리를 수행
 * - JPA의 트랜잭션 관리와 생명주기 관리를 다룸
 */
public class HelloWorldJPATest {

    @Test
    public void storeLoadMessage() {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("ch02");

        try {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Message message = new Message();
            message.setText("Hello World!");

            em.persist(message);

            em.getTransaction().commit();
            //INSERT into MESSAGE (ID, TEXT) values (1, 'Hello World!')

            em.getTransaction().begin();

            List<Message> messages =
                    em.createQuery("select m from Message m", Message.class).getResultList();
            //SELECT * from MESSAGE

            messages.get(messages.size() - 1).setText("Hello World from JPA!");

            em.getTransaction().commit();
            //UPDATE MESSAGE set TEXT = 'Hello World from JPA!' where ID = 1

            assertAll(
                    () -> assertEquals(1, messages.size()),
                    () -> assertEquals("Hello World from JPA!", messages.get(0).getText())
            );

            em.close();

        } finally {
            emf.close();
        }
    }
}

