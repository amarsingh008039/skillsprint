package com.skillsprint.exception;

public class ChallengeNotFoundException extends RuntimeException{
    public ChallengeNotFoundException(String message){
        super(message);
    }
}
