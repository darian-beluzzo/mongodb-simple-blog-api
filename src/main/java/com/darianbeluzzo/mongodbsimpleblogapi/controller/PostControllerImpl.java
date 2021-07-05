package com.darianbeluzzo.mongodbsimpleblogapi.controller;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import com.darianbeluzzo.mongodbsimpleblogapi.model.Comment;
import com.darianbeluzzo.mongodbsimpleblogapi.model.Post;
import com.darianbeluzzo.mongodbsimpleblogapi.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostControllerImpl implements PostController {

    private final Logger logger = LoggerFactory.getLogger(PostControllerImpl.class);

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<?> findAll(final Pageable page) {
        var posts = postService.findAll(page);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping(value = "/{title}")
    public ResponseEntity<?> findPostsByTitle(@PathVariable String title, final Pageable page) {
        logger.info("Post title -> {} ", title);
        var posts = postService.findPostsByTitle(title, page);
        return ResponseEntity.ok().body(posts);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid Post post) throws ResourceNotFoundException {
        var savedPost = postService.save(post);
        return ResponseEntity.ok().body(savedPost);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) throws ResourceNotFoundException {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody @Valid Post post) throws ResourceNotFoundException {
        var savedPost = postService.update(post);
        return ResponseEntity.ok().body(savedPost);
    }

    @PostMapping(value = "/{postId}/comment")
    public ResponseEntity<?> createComment(@PathVariable String postId, @RequestBody @Valid Comment comment) throws ResourceNotFoundException {
        var savedPost = postService.saveComment(postId, comment);
        return ResponseEntity.ok().body(savedPost);
    }
}
