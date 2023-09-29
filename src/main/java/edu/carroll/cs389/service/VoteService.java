package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.Vote;
import edu.carroll.cs389.jpa.repo.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    public void saveVote(Vote vote) {
        voteRepository.save(vote);
    }

}
