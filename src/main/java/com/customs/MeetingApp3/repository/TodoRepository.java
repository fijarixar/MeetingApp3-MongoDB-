package com.customs.MeetingApp3.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.customs.MeetingApp3.model.TodoDTO;


public interface TodoRepository extends MongoRepository<TodoDTO, String> {
    
}
