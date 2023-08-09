package com.sangkhim.spring_boot3_cryptography.repository;

import com.sangkhim.spring_boot3_cryptography.model.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByTitleContaining(String title);

  @Modifying
  @Query(value = "SELECT * FROM posts WHERE title LIKE %:title%", nativeQuery = true)
  List<Post> selectPosts(@Param("title") String title);

  @Modifying
  @Query("DELETE FROM Post p WHERE p.title = :title OR p.body = :body")
  int deletePosts(@Param("title") String title, @Param("body") String body);
}
