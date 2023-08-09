package com.sangkhim.spring_boot3_cryptography.controller;

import com.sangkhim.spring_boot3_cryptography.model.entity.Author;
import com.sangkhim.spring_boot3_cryptography.service.AuthorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthorController {

  private final AuthorService service;

  @GetMapping("/v1/authors")
  public ResponseEntity<List<Author>> getAllAuthors() {
    List<Author> list = service.getAllAuthors();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/v1/authors/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable("id") Long id) {
    Author entity = service.getById(id);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @PostMapping("/v1/authors")
  public ResponseEntity<Author> createOrUpdate(@RequestBody Author Author) {
    Author updated = service.createOrUpdate(Author);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/v1/authors/{id}")
  public void deleteById(@PathVariable("id") Long id) {
    service.deleteById(id);
  }
}
