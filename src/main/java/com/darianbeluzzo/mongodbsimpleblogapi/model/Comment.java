package com.darianbeluzzo.mongodbsimpleblogapi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class Comment {

    private String text;

    @NotNull
    @DBRef
    private User author;
}
