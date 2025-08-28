// POJO (Plain Old Java Object) representing a Journal Entry record stored in MongoDBpackage com.harsh.JournallingApplication.entity;
package com.harsh.JournallingApplication.entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Entity class that maps to the "journal_entries" collection in MongoDB.
 *
 * Each document in this collection represents a journal entry written by a user.
 *
 * Lombok is used to reduce boilerplate code:
 * - @Data        → Generates getters, setters, toString, equals, and hashCode
 * - @NoArgsConstructor → Adds a default no-arg constructor
 *
 * MongoDB-specific annotations:
 * - @Document    → Maps this class as a MongoDB collection
 * - @Id          → Marks the primary key field (ObjectId in MongoDB)
 */
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {

    /**
     * The unique identifier for this journal entry.
     * - Stored as an ObjectId in MongoDB (_id field)
     * - Automatically generated if not provided
     */
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;

}
