package com.skillsprint.controller;

import com.skillsprint.model.ChallengeRequest;
import com.skillsprint.model.ChallengeResponse;
import com.skillsprint.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @Autowired
    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping("/getChallenge")
    public ChallengeResponse getChallenge(@RequestBody ChallengeRequest req){
        return challengeService.getChallenge(req);
    }

    @PostMapping("/getChallengeWithMetaData")
    public ChallengeResponse getChallengeWithMetadata(@RequestBody ChallengeRequest req){
        return challengeService.getChallengeWithMetaData(req);
    }
}
