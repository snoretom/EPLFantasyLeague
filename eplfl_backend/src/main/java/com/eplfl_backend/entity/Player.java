package com.eplfl_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "players")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;
    
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String team;
    
    @NotNull
    @DecimalMin(value = "4.0")
    @DecimalMax(value = "15.0")
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal price;
    
    @Column(columnDefinition = "int default 0")
    private Integer totalPoints = 0;
    
    @Column(columnDefinition = "int default 0") 
    private Integer gamesPlayed = 0;
    
    @Column(columnDefinition = "boolean default true")
    private Boolean available = true;
    
    // 기본 생성자
    public Player() {}
    
    // 생성자
    public Player(String name, Position position, String team, BigDecimal price) {
        this.name = name;
        this.position = position;
        this.team = team;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    
    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    
    public Integer getGamesPlayed() { return gamesPlayed; }
    public void setGamesPlayed(Integer gamesPlayed) { this.gamesPlayed = gamesPlayed; }
    
    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }
    
    // 평균 점수 계산
    public Double getAveragePoints() {
        if (gamesPlayed == 0) return 0.0;
        return (double) totalPoints / gamesPlayed;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", team='" + team + '\'' +
                ", price=" + price +
                '}';
    }
}