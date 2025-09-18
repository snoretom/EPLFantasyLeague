package com.eplfl_backend.entity;

public enum Position {
    GK("Goalkeeper"),
    DEF("Defender"),
    MID("Midfielder"),
    FWD("Forward");
    
    private final String displayName;
    
    Position(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}