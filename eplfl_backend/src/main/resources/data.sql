-- 선택 가능한 선수들
INSERT INTO PLAYERS (name, korean_name, position, team, price, total_points, games_played, status) 
VALUES ('Erling Haaland', '엘링 홀란드', 'FWD', 'Manchester City', 15.0, 45, 5, 'AVAILABLE');

INSERT INTO PLAYERS (name, korean_name, position, team, price, total_points, games_played, status) 
VALUES ('Kevin De Bruyne', '케빈 데 브뤼너', 'MID', 'Manchester City', 12.5, 32, 4, 'AVAILABLE');

-- 부상 선수
INSERT INTO PLAYERS (name, korean_name, position, team, price, total_points, games_played, status) 
VALUES ('Mohamed Salah', '모하메드 살라', 'FWD', 'Liverpool', 13.0, 28, 3, 'INJURED');

-- 출전 정지 선수
INSERT INTO PLAYERS (name, korean_name, position, team, price, total_points, games_played, status) 
VALUES ('Bruno Fernandes', '브루누 페르난데스', 'MID', 'Manchester United', 11.5, 20, 4, 'SUSPENDED');

-- 이적한 선수
INSERT INTO PLAYERS (name, korean_name, position, team, price, total_points, games_played, status) 
VALUES ('Harry Kane', '해리 케인', 'FWD', 'Bayern Munich', 12.0, 35, 4, 'TRANSFERRED');