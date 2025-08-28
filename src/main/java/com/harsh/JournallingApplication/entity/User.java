package com.harsh.JournallingApplication.entity;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity class representing application users stored in MongoDB.
 *
 * Mapped to the "users" collection. Each user has a unique username,
 * a password, and references to their journal entries.
 *
 * Lombok annotations:
 * - @Data      → Generates getters, setters, equals, hashCode, toString.
 * - @NonNull   → Enforces non-null constraints at runtime for required fields.
 *
 * MongoDB-specific annotations:
 * - @Document  → Marks this class as a MongoDB collection mapping
 * - @Id        → Identifies the primary key (_id in MongoDB)
 * - @Indexed   → Ensures database-level constraints (like unique username)
 * - @DBRef     → Creates a reference to another MongoDB collection (JournalEntries)
 */
@Document(collection = "users")
@Data
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;
}
