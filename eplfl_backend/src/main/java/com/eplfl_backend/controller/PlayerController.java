package com.eplfl_backend.controller;

import com.eplfl_backend.entity.Player;
import com.eplfl_backend.entity.PlayerStatus;
import com.eplfl_backend.entity.Position;
import com.eplfl_backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "http://localhost:3000")
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    //모든선수 
    @GetMapping
    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }
    //선수id로 찾기
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 포지션별 선수
    @GetMapping("/position/{position}")
    public List<Player> getPlayersByPosition(@PathVariable String position) {
        try {
            Position pos = Position.valueOf(position.toUpperCase());
            return playerRepository.findByPosition(pos);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    // 팀별 선수 조회
    @GetMapping("/team/{teamName}")
    public List<Player> getPlayersByTeam(@PathVariable String teamName) {
        return playerRepository.findByTeam(teamName);
    }

    // 상태별 선수 
    @GetMapping("/status/{status}")
    public List<Player> getPlayersByStatus(@PathVariable String status) {
        try {
            PlayerStatus playerStatus = PlayerStatus.valueOf(status.toUpperCase());
            return playerRepository.findByStatus(playerStatus);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    // 사용 가능한 선수
    @GetMapping("/available")
    public List<Player> getAvailablePlayers() {
        return playerRepository.findByStatus(PlayerStatus.AVAILABLE);
    }

    //가격 범위 선수
    @GetMapping("/price")
    public List<Player> getPlayersByPriceRange(@RequestParam BigDecimal min,@RequestParam BigDecimal max) {
        return playerRepository.findByPriceBetween(min, max);
    }

    // 가격 순으로 정렬된 선수 조회
    @GetMapping("/by-price")
    public List<Player> getPlayersByPrice(
            @RequestParam(defaultValue = "asc") String sort) {
        
        if ("desc".equalsIgnoreCase(sort)) {
            return playerRepository.findAllByOrderByPriceDesc();
        } else {
            return playerRepository.findAllByOrderByPriceAsc();
        }
    }

    // 점수 순으로 정렬된 선수 조회
    @GetMapping("/by-points")
    public List<Player> getPlayersByPoints() {
        return playerRepository.findAllByOrderByTotalPointsDesc();
    }

    // 선수 이름 검색 (영문명 + 한글명)
    @GetMapping("/search")
    public List<Player> searchPlayers(@RequestParam String name) {
        // 한글 문자 포함 여부 검사
        boolean containsKorean = name.matches(".*[가-힣].*");
        
        if (containsKorean) {
            // 한글이 포함된 경우 한글명에서만 검색
            return playerRepository.findByKoreanNameContainingIgnoreCase(name);
        } else {
            // 영문인 경우 영문명에서만 검색
            return playerRepository.findByNameContainingIgnoreCase(name);
        }
    }
}
