package com.sangkhim.spring_boot3_cryptography.controller;

import com.sangkhim.spring_boot3_cryptography.model.entity.Tag;
import com.sangkhim.spring_boot3_cryptography.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class PostTagController {

  private final PostService service;

  @GetMapping("/v1/posts/{id}/tags")
  public ResponseEntity<List<Tag>> getAllTagsByPostId(@PathVariable(value = "id") Long id) {
    List<Tag> tagList = service.getAllTagsByPostId(id);
    return new ResponseEntity<>(tagList, HttpStatus.OK);
  }

  @PostMapping("/v1/posts/{id}/tags")
  public ResponseEntity<Tag> addTag(@PathVariable("id") Long id, @RequestBody Tag tagRequest) {
    Tag updated = service.addTag(id, tagRequest);
    return new ResponseEntity<>(updated, HttpStatus.OK);
  }

  @DeleteMapping("/v1/posts/{id}/tags/{tagId}")
  public void deleteTagFromPost(@PathVariable("id") Long id, @PathVariable("tagId") Long tagId) {
    service.deleteTagFromPost(id, tagId);
  }
}
