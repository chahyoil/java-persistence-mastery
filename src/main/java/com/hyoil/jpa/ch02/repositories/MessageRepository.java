package com.hyoil.jpa.ch02.repositories;

import com.hyoil.jpa.ch02.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}