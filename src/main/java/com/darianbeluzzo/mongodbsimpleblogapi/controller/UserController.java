package com.darianbeluzzo.mongodbsimpleblogapi.controller;

import com.darianbeluzzo.mongodbsimpleblogapi.exception.ResourceNotFoundException;
import com.darianbeluzzo.mongodbsimpleblogapi.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "Endpoint for user data")
public interface UserController {

    @Operation(summary = "Find all Users", description = "Find all Users", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found", content = @Content(schema = @Schema(implementation = User.class)))
    })
    ResponseEntity<?> findAll();

    @Operation(summary = "Find User", description = "Get user by username", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<?> findByUsername(@PathVariable String username);

    @Operation(summary = "Create new User", description = "Store user on database", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User saved", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "A validation error occurred"),
            @ApiResponse(responseCode = "409", description = "User exists")
    })
    ResponseEntity<?> saveUser(@RequestBody User user);

    @Operation(summary = "Delete User", description = "Remove user from database base on ID", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<?> deleteUser(@PathVariable String id) throws ResourceNotFoundException;

    @Operation(summary = "Update User", description = "Update all fields user on database", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "User not updated")
    })
    ResponseEntity<?> updateUser(@RequestBody User user) throws ResourceNotFoundException;
}
