package com.darianbeluzzo.mongodbsimpleblogapi.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document
public class Post {

    @Id
    private String id;

    @Length(min = 5)
    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private LocalDateTime createDate;

    private List<String> tags;

    @NotNull
    @DBRef
    private User author;

    private Set<Comment> comments;

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new HashSet<>();
        }
        comments.add(comment);
    }
}