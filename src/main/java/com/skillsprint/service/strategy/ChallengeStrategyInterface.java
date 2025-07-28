package com.skillsprint.service.strategy;

import com.skillsprint.model.ChallengeResponse;

public interface ChallengeStrategyInterface {
    ChallengeResponse generateChallenge(String difficulty);
    String getTopicName();
    void enrichChallengeDetails(ChallengeResponse response, String difficulty);
}
