package com.customs.MeetingApp3.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.customs.MeetingApp3.model.TodoDTO;
import com.customs.MeetingApp3.repository.TodoRepository;


@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;

    // read semua data
    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0) {
            return new ResponseEntity<List<TodoDTO>>(todos, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }
    }


    // create data
    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todo.setCreateAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // read data by ID metode PathVariable  contoh : localhost/todos/15224
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if(todoOptional.isPresent()) {
            return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Todo not found with id "+id, HttpStatus.NOT_FOUND);
        }
    }




     // read data by ID metode PathVariable  contoh : localhost/todos/15224
     @GetMapping("/todos2/")
     public ResponseEntity<?> getSingleTodo2(@RequestParam("id") String id) {
         Optional<TodoDTO> todoOptional = todoRepo.findById(id);
         if(todoOptional.isPresent()) {
             return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
         }else {
             return new ResponseEntity<>("Todo not found with id "+id, HttpStatus.NOT_FOUND);
         }
     }
 


    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateSingleTodo(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if(todoOptional.isPresent()) {
            TodoDTO todoToSave = todoOptional.get();
            todoToSave.setCompleted(todo.getCompleted() != null ? todo.getCompleted() : todoToSave.getCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdateAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Todo not found with id "+id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            todoRepo.deleteById(id);
            return new ResponseEntity<>("Succesfully delete with id "+id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


}
