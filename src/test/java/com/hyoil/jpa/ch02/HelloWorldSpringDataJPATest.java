package com.hyoil.jpa.ch02;


import com.hyoil.jpa.ch02.configuration.SpringDataConfiguration;
import com.hyoil.jpa.ch02.repositories.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * HelloWorldSpringDataJPATest 클래스
 *
 * 특징:
 * - Spring Data JPA를 사용하여 데이터베이스와 상호 작용하는 테스트 클래스
 * - Spring Data JPA의 CrudRepository를 사용하여 데이터베이스 작업을 수행
 * - 스프링의 의존성 주입을 사용하여 리포지토리를 주입받음
 *
 * 차이점:
 * - Spring Data JPA를 사용하여 데이터베이스 작업을 간소화
 * - 리포지토리 인터페이스를 사용하여 CRUD 작업을 수행
 * - 스프링의 의존성 주입 및 설정을 사용하여 설정과 구성을 간소화
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringDataConfiguration.class})
public class HelloWorldSpringDataJPATest {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void storeLoadMessage() {
        Message message = new Message();
        message.setText("Hello World from Spring Data JPA!");

        messageRepository.save(message);

        List<Message> messages = (List<Message>) messageRepository.findAll();

        assertAll(
                () -> assertEquals(1, messages.size()),
                () -> assertEquals("Hello World from Spring Data JPA!", messages.get(0).getText())
        );

    }
}