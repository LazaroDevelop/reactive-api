package net.developer.space.reactive_api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    boolean mfaEnabled;
    private String secretKey;
    private String firstName;
    private String lastName;
    private boolean active;
    private boolean accountVerified;
    @DBRef
    private Set<Role> roles = new HashSet<>();
}
