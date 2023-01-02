package com.toyin.lerongba.repositories;

import com.toyin.lerongba.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {

    @Override
    Optional<BlogPost> findById(Integer integer);

    Optional<BlogPost> findByTitle(String title);

    boolean existsByTitle(String title);

    List<BlogPost> findTop5ByApprovedTrueOrderByCreatedTimeDesc();

    List<BlogPost> findTop2ByApprovedTrueOrderByCreatedTimeDesc();

    List<BlogPost> findTop4ByApprovedTrueOrderByCreatedTimeDesc();

    List<BlogPost> findAllByApprovedTrueOrderByCreatedTimeDesc();

    List<BlogPost> findAllByApprovedFalseOrderByCreatedTimeDesc();
}
