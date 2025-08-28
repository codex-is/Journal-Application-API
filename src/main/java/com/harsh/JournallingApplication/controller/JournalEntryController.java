package com.harsh.JournallingApplication.controller;
import com.harsh.JournallingApplication.entity.JournalEntry;
import com.harsh.JournallingApplication.entity.User;
import com.harsh.JournallingApplication.service.JournalEntryService;
import com.harsh.JournallingApplication.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for handling journal entry operations.
 *
 * Base URL: /journal
 *
 * Features:
 * - Create, read, update, and delete journal entries (CRUD).
 * - All entries are tied to a specific user (identified by username).
 * - Responses use ResponseEntity for consistent HTTP status handling.
 */
@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    // Service responsible for journal entry persistence logic
    @Autowired
    private JournalEntryService journalEntryService;

    // Service responsible for user lookup and management
    @Autowired
    private UserService userService;

    /**
     * Get all journal entries of a specific user.
     *
     * @param userName Username whose entries we want to fetch
     * @return 200 OK + List of entries if user has entries
     *         404 NOT_FOUND if no entries are present
     */
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        // Fetch user from DB
        User user = userService.findByUsername(userName);
        List<JournalEntry> all = user.getJournalEntries();
        // Return list if not empty, otherwise 404
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create a new journal entry for a given user.
     *
     * @param myEntry   Journal entry data from request body
     * @param userName  Username for whom the entry is created
     * @return 201 CREATED with the entry if successful
     *         400 BAD_REQUEST if saving fails
     */
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try{
            // Automatically set current timestamp
            myEntry.setDate(LocalDateTime.now());

            // Delegate persistence to service (ties entry to user)
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED );
        }catch (Exception e){
            return new ResponseEntity<>(myEntry,HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Find a journal entry by its database ID.
     *
     * @param myId ObjectId of the journal entry
     * @return 200 OK + entry if found
     *         404 NOT_FOUND if no entry exists with provided ID
     */
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    /**
     * Delete a journal entry by ID for a given user.
     *
     * @param myId     ObjectId of the entry
     * @param userName Username owner of the entry
     * @return 204 NO_CONTENT if deletion is successful
     */
    @DeleteMapping("id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        // Delegate delete logic to service to ensure proper ownership check
        journalEntryService.deleteById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update an existing journal entry by ID.
     *
     * Only non-null and non-empty fields from the request body will override existing values.
     * Useful for partial updates (title or content).
     *
     * @param myId     ObjectId of the entry to update
     * @param newEntry Request body containing new data (title/content)
     * @param userName Username owner of the entry
     * @return 200 OK + updated entry if successful
     *         404 NOT_FOUND if entry does not exist
     */
    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(
            @PathVariable ObjectId myId,
            @RequestBody JournalEntry newEntry,
            @PathVariable String userName){
        // Fetch current entry
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old != null) {
            // Update only valid fields (ignoring empty/null updates)
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());

            // Save updated entry
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
}