package edu.carroll.cs389.jpa.repo;

import edu.carroll.cs389.jpa.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    // Custom queries (if needed) go here
}