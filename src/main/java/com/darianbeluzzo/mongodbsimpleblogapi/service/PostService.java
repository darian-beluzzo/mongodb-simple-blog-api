package com.darianbeluzzo.mongodbsimpleblogapi.service;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import com.darianbeluzzo.mongodbsimpleblogapi.model.Comment;
import com.darianbeluzzo.mongodbsimpleblogapi.model.Post;
import com.darianbeluzzo.mongodbsimpleblogapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public Page<Post> findPostsByTitle(String postname, Pageable page) {
        return postRepository.findPostsByTitleLike(postname, page);
    }

    public Post save(final Post post) throws ResourceNotFoundException {
        userService.checkIfUserExists(post.getAuthor().getId());
        post.setId(UUID.randomUUID().toString());
        post.setCreateDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deleteById(final String id) throws ResourceNotFoundException {
        var postExists = postRepository.existsById(id);
        if (!postExists) {
            throw new ResourceNotFoundException("The given post id does not exist");
        }
        postRepository.deleteById(id);
    }

    public Post update(final Post post) throws ResourceNotFoundException {
        var postExists = postRepository.existsById(post.getId());
        if (!postExists) {
            throw new ResourceNotFoundException("The given post id does not exist");
        }
        return postRepository.save(post);
    }

    public Page<Post> findAll(Pageable page) {
        return postRepository.findAll(page);
    }

    public Post saveComment(String postId, Comment comment) throws ResourceNotFoundException {
        var postOpt = postRepository.findById(postId);
        if(postOpt.isEmpty()){
            throw new ResourceNotFoundException("The given post id does not exist");
        }
        Post post = postOpt.get();
        post.addComment(comment);

        return postRepository.save(post);
    }
}
