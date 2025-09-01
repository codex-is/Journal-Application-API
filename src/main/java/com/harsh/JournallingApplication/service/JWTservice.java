package com.harsh.JournallingApplication.service;

import org.springframework.stereotype.Service;

@Service
public class JWTservice {
    public String generateToken() {
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6ImhhcnNoIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.96vBZX7XT834xOVK4y0scl-UBtXQUDdfF0R3TSvGbZY";
    }
}
