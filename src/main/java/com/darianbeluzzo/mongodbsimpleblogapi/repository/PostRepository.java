package com.darianbeluzzo.mongodbsimpleblogapi.repository;

import com.darianbeluzzo.mongodbsimpleblogapi.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findPostsByTitleLike(String postTitle, Pageable page);
}
