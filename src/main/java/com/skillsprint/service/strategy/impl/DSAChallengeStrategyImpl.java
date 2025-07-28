package com.skillsprint.service.strategy.impl;

import com.skillsprint.entity.ChallengeMetadata;
import com.skillsprint.exception.ChallengeNotFoundException;
import com.skillsprint.model.ChallengeResponse;
import com.skillsprint.repository.ChallengeMetadataRepository;
import com.skillsprint.repository.ChallengeRepository;
import com.skillsprint.service.strategy.ChallengeStrategyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component("DSA")
public class DSAChallengeStrategyImpl implements ChallengeStrategyInterface {

    //private static final Map<String,ChallengeResponse> challenges = new HashMap<>();
    private final ChallengeRepository challengeRepository;
    private final ChallengeMetadataRepository metadataRepository;

    @Autowired
    public DSAChallengeStrategyImpl(ChallengeRepository challengeRepository,ChallengeMetadataRepository challengeMetadataRepository) {
        this.challengeRepository = challengeRepository;
        this.metadataRepository = challengeMetadataRepository;
    }

    @Override
    public String getTopicName() {
        return "DSA";
    }

    /**
     * @param response
     * @param difficulty
     */
    @Override
    public void enrichChallengeDetails(ChallengeResponse response, String difficulty) {
        Optional<ChallengeMetadata> meta = metadataRepository
                .findByTopicAndDifficulty("DSA", difficulty.toUpperCase());
        meta.ifPresent(data -> {
            response.setHint(data.getHint());
            response.setCodeTemplate(data.getCodeTemplate());
            response.setTestCases(data.getTestCases());
        });
    }

    @Override
    public ChallengeResponse generateChallenge(String difficulty) {
       return challengeRepository.findByTopicIgnoreCaseAndDifficultyIgnoreCase("DSA",difficulty)
               .stream()
               .findAny()
               .map(ch -> new ChallengeResponse(ch.getTitle(),ch.getDescription(),ch.getDifficulty()))
               .orElseThrow(()->new ChallengeNotFoundException("No DSA challenge found for difficulty: "+difficulty));
    }


//    static{
//        challenges.put("EASY", new ChallengeResponse("Two Sum", "Find two numbers that add up to a target.", "EASY"));
//        challenges.put("MEDIUM", new ChallengeResponse("Longest Substring", "Find length of longest substring without repeating characters.", "MEDIUM"));
//        challenges.put("HARD", new ChallengeResponse("Median of Two Sorted Arrays", "Find median in O(log n) time.", "HARD"));
//    }

//    /**
//     * @param difficulty
//     * @return
//     */

}
