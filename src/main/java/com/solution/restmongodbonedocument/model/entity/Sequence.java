package com.solution.restmongodbonedocument.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sequence")
public class Sequence {
    @Id
    private String id;
}
