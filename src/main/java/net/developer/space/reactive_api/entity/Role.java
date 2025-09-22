package net.developer.space.reactive_api.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "roles")
public class Role {
    @Id
    private String id;
    private ERole role;
}
