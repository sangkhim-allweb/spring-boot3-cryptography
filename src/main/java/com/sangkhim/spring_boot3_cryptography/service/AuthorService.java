package com.sangkhim.spring_boot3_cryptography.service;

import com.sangkhim.spring_boot3_cryptography.exception.BadRequestException;
import com.sangkhim.spring_boot3_cryptography.exception.DataNotFoundException;
import com.sangkhim.spring_boot3_cryptography.model.entity.Author;
import com.sangkhim.spring_boot3_cryptography.repository.AuthorRepository;
import com.sangkhim.spring_boot3_cryptography.utils.Translator;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

  private final AuthorRepository authorRepository;

  public List<Author> getAllAuthors() {
    List<Author> authorList = authorRepository.findAll();
    return authorList;
  }

  public Author getById(Long id) {
    return authorRepository
        .findById(id)
        .orElseThrow(
            () ->
                new DataNotFoundException(
                    MessageFormat.format("Author id {0} not found", String.valueOf(id))));
  }

  public Author createOrUpdate(Author authorRequest) {
    Optional<Author> existingAuthor = authorRepository.findById(authorRequest.getId());

    if (existingAuthor.isPresent()) {
      Author authorUpdate = existingAuthor.get();

      authorUpdate.setName(authorRequest.getName());

      return authorRepository.save(authorUpdate);
    } else {
      return authorRepository.save(authorRequest);
    }
  }

  public void deleteById(Long id) {
    Optional<Author> author = authorRepository.findById(id);
    if (author.isPresent()) {
      authorRepository.deleteById(id);
    } else {
      throw new BadRequestException(Translator.toLocale("DELETE_ERROR_PLEASE_CHECK_ID"));
    }
  }
}
