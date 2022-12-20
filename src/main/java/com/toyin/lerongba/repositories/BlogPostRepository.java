package com.toyin.lerongba.repositories;

import com.toyin.lerongba.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {

    @Override
    Optional<BlogPost> findById(Integer integer);

    BlogPost findBlogPostByTitle(String title);

    boolean existsByTitle(String title);
}
