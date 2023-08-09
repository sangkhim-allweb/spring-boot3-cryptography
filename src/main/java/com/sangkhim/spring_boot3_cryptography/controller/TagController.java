package com.sangkhim.spring_boot3_cryptography.controller;

import com.sangkhim.spring_boot3_cryptography.model.entity.Tag;
import com.sangkhim.spring_boot3_cryptography.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TagController {

  private final TagService service;

  @GetMapping("/v1/tags")
  public ResponseEntity<List<Tag>> getAllTags() {
    List<Tag> list = service.getAllTags();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/v1/tags/{id}")
  public ResponseEntity<Tag> getTagById(@PathVariable("id") Long id) {
    Tag entity = service.getById(id);
    return new ResponseEntity<>(entity, HttpStatus.OK);
  }

  @PostMapping("/v1/tags")
  public ResponseEntity<Tag> createOrUpdate(@RequestBody Tag Tag) {
    Tag updated = service.createOrUpdate(Tag);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/v1/tags/{id}")
  public void deleteById(@PathVariable("id") Long id) {
    service.deleteById(id);
  }
}
