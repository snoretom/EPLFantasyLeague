package com.eplfl_backend.entity;

public enum PlayerStatus {
    AVAILABLE("선택 가능"),
    INJURED("부상"),
    SUSPENDED("출전 정지"), 
    TRANSFERRED("이적"),
    UNAVAILABLE("선택 불가");
    
    private final String description;
    
    PlayerStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}