package com.harsh.JournallingApplication.repository;
import com.harsh.JournallingApplication.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for JournalEntry entities.
 *
 * Extends MongoRepository to provide CRUD operations and
 * pagination/sorting capabilities for JournalEntry documents
 * in MongoDB.
 *
 * Generic parameters:
 * - JournalEntry: the domain type the repository manages.
 * - ObjectId: the type of the unique identifier of the domain type.
 *
 * By extending MongoRepository, Spring Data automatically
 * generates implementation code for common database operations.
 */
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}

//controller -----> service -----> repository.