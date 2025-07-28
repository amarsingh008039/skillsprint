package com.skillsprint.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "challenge_metadata",schema="skillsprint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String difficulty;

    @Column(length = 2000)
    private String hint;

    @Column(name = "code_template", length = 5000)
    private String codeTemplate;

    @ElementCollection
    @CollectionTable(name = "challenge_test_cases", joinColumns = @JoinColumn(name = "metadata_id"))
    @Column(name = "test_case")
    private List<String> testCases;
}
