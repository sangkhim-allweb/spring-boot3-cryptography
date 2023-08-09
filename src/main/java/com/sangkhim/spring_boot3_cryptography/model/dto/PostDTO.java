package com.sangkhim.spring_boot3_cryptography.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

  private long id;

  @Column(name = "title", nullable = false)
  @NotBlank(message = "Title is mandatory")
  private String title;

  @Column(name = "body")
  @NotBlank(message = "Body is mandatory")
  private String body;

  private long authorId;
}
