package com.harsh.JournallingApplication.repository;
import com.harsh.JournallingApplication.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for User entities.
 *
 * Extends MongoRepository to provide built-in CRUD operations,
 * pagination, and sorting capabilities for User documents
 * stored in MongoDB.
 *
 * Generic parameters:
 * - User: the type of domain object this repository manages.
 * - ObjectId: the type of the unique identifier field of User.
 *
 * Custom query method:
 * - findByUsername(String username): Retrieves a User document
 *   by its unique username.
 *
 * Spring Data automatically generates the implementation of this method
 * based on its name following the query derivation mechanism.
 */
public interface UserRepository extends MongoRepository<User, ObjectId> {
    /**
     * Find a user by their unique username.
     *
     * @param username The username to search for.
     * @return The User entity matching the username, or null if none found.
     */
    User findByUsername(String username);
}

/*
 Architectural flow reminder:
 Requests are handled by Controller -> processed in Service -> data interactions via Repository.
*/
