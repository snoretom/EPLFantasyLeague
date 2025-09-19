package com.eplfl_backend.repository;

import com.eplfl_backend.entity.Player;
import com.eplfl_backend.entity.Position;
import com.eplfl_backend.entity.PlayerStatus;
import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>{

    //포지션별 플레이어
    List<Player> findByPosition(Position position);

    //상태별 플레이어 
    List<Player> findByStatus(PlayerStatus status);

    //팀별 플레이어 검색
    List<Player> findByTeam(String team);

    //가격 범위 안 선수 찾기 
    List<Player> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    //오름차순 가격으로 선수 찾기
    List<Player> findAllByOrderByPriceAsc();

    //내림차순 가격으로 선수 찾기
    List<Player> findAllByOrderByPriceDesc();
    
    //총점수 순위로 선수 찾기
    List<Player> findAllByOrderByTotalPointsDesc();

    //선수 이름 포함(영어대소문자 구분없이) 로 선수 찾기
    List<Player> findByNameContainingIgnoreCase(String name);
    List<Player> findByKoreanNameContainingIgnoreCase(String koreanName);

}