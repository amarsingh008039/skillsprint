package com.skillsprint.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponse {
    private String title;
    private String description;
    private String difficulty;

    private String hint;
    private String codeTemplate;
    private List<String> testCases;

    public ChallengeResponse(String title, String description, String difficulty) {
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
    }
}
