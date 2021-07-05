package com.darianbeluzzo.mongodbsimpleblogapi.controller;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import com.darianbeluzzo.mongodbsimpleblogapi.model.Comment;
import com.darianbeluzzo.mongodbsimpleblogapi.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Posts", description = "Endpoint for Posts data")
public interface PostController {

    @Operation(summary = "Find all Posts", description = "Find all Posts", tags = {"Post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts found", content = @Content(schema = @Schema(implementation = Post.class)))
    })
    ResponseEntity<?> findAll(Pageable page);

    @Operation(summary = "Find Posts by title", description = "Find Posts by title", tags = {"Post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts found by title", content = @Content(schema = @Schema(implementation = Post.class)))
    })
    ResponseEntity<?> findPostsByTitle(@PathVariable String title, final Pageable page);

    @Operation(summary = "Create new Post", description = "Store new Post on database", tags = {"Post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post created successfully", content = @Content(schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "A validation error occurred")
    })
    ResponseEntity<?> createPost(@RequestBody @Valid Post post) throws ResourceNotFoundException;

    @Operation(summary = "Delete Post", description = "Delete Post from database based on ID", tags = {"Post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post deleted"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    ResponseEntity<?> deletePost(@PathVariable String id) throws ResourceNotFoundException;

    @Operation(summary = "Update Post", description = "Update Post from database based on ID", tags = {"Post"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Post"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    ResponseEntity<?> updatePost(@RequestBody @Valid Post post) throws ResourceNotFoundException;

    @Operation(summary = "Create new Post comment", description = "Store new Comment on database", tags = {"Post", "Comment"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created successfully", content = @Content(schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "400", description = "A validation error occurred")
    })
    ResponseEntity<?> createComment(@PathVariable String postId, @RequestBody @Valid Comment comment) throws ResourceNotFoundException;
}
