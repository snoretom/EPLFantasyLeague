import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../css/PlayerList.css';

const PlayerList = () => {
    const [players, setPlayers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedPosition, setSelectedPosition] = useState('ALL');
    const [selectedStatus, setSelectedStatus] = useState('ALL');

    // API 기본 URL
    const API_BASE_URL = 'http://localhost:8081/api/players';

    // 컴포넌트 마운트 시 전체 선수 데이터 로드
    useEffect(() => {
        fetchAllPlayers();
    }, []);

    // 전체 선수 데이터 가져오기
    const fetchAllPlayers = async () => {
        try {
            setLoading(true);
            const response = await axios.get(API_BASE_URL);
            setPlayers(response.data);
            setError(null);
        } catch (err) {
            setError('선수 데이터를 불러오는데 실패했습니다.');
            console.error('Error fetching players:', err);
        } finally {
            setLoading(false);
        }
    };

    // 포지션별 필터링
    const fetchPlayersByPosition = async (position) => {
        try {
            setLoading(true);
            const response = await axios.get(`${API_BASE_URL}/position/${position}`);
            setPlayers(response.data);
        } catch (err) {
            setError('포지션별 데이터를 불러오는데 실패했습니다.');
        } finally {
            setLoading(false);
        }
    };

    // 상태별 필터링
    const fetchPlayersByStatus = async (status) => {
        try {
            setLoading(true);
            const response = await axios.get(`${API_BASE_URL}/status/${status}`);
            setPlayers(response.data);
        } catch (err) {
            setError('상태별 데이터를 불러오는데 실패했습니다.');
        } finally {
            setLoading(false);
        }
    };

    // 선수 검색
    const searchPlayers = async (name) => {
        if (!name.trim()) {
            fetchAllPlayers();
            return;
        }
        
        try {
            setLoading(true);
            const response = await axios.get(`${API_BASE_URL}/search?name=${encodeURIComponent(name)}`);
            setPlayers(response.data);
        } catch (err) {
            setError('검색에 실패했습니다.');
        } finally {
            setLoading(false);
        }
    };

    // 포지션 변경 핸들러
    const handlePositionChange = (position) => {
        setSelectedPosition(position);
        if (position === 'ALL') {
            fetchAllPlayers();
        } else {
            fetchPlayersByPosition(position);
        }
    };

    // 상태 변경 핸들러
    const handleStatusChange = (status) => {
        setSelectedStatus(status);
        if (status === 'ALL') {
            fetchAllPlayers();
        } else {
            fetchPlayersByStatus(status);
        }
    };

    // 검색 핸들러
    const handleSearch = (e) => {
        const term = e.target.value;
        setSearchTerm(term);
        searchPlayers(term);
    };

    // 선수 상태에 따른 스타일 클래스
    const getStatusClass = (status) => {
        switch (status) {
            case 'AVAILABLE': return 'status-available';
            case 'INJURED': return 'status-injured';
            case 'SUSPENDED': return 'status-suspended';
            case 'TRANSFERRED': return 'status-transferred';
            default: return 'status-unavailable';
        }
    };

    // 선수 상태 한글 변환
    const getStatusText = (status) => {
        switch (status) {
            case 'AVAILABLE': return '선택 가능';
            case 'INJURED': return '부상';
            case 'SUSPENDED': return '출전 정지';
            case 'TRANSFERRED': return '이적';
            default: return '선택 불가';
        }
    };

    if (loading) return <div className="loading">선수 데이터를 불러오는 중...</div>;
    if (error) return <div className="error">{error}</div>;

    return (
        <div className="player-list-container">
            <h1>EPL 판타지 리그 - 선수 목록</h1>
            
            {/* 필터 및 검색 */}
            <div className="filters">
                <input
                    type="text"
                    placeholder="선수 이름 검색..."
                    value={searchTerm}
                    onChange={handleSearch}
                    className="search-input"
                />
                
                <select 
                    value={selectedPosition} 
                    onChange={(e) => handlePositionChange(e.target.value)}
                    className="filter-select"
                >
                    <option value="ALL">모든 포지션</option>
                    <option value="GK">골키퍼</option>
                    <option value="DEF">수비수</option>
                    <option value="MID">미드필더</option>
                    <option value="FWD">공격수</option>
                </select>
                
                <select 
                    value={selectedStatus} 
                    onChange={(e) => handleStatusChange(e.target.value)}
                    className="filter-select"
                >
                    <option value="ALL">모든 상태</option>
                    <option value="AVAILABLE">선택 가능</option>
                    <option value="INJURED">부상</option>
                    <option value="SUSPENDED">출전 정지</option>
                    <option value="TRANSFERRED">이적</option>
                </select>
            </div>

            {/* 선수 목록 */}
            <div className="players-grid">
                {players.map((player) => (
                    <div key={player.id} className={`player-card ${getStatusClass(player.status)}`}>
                        <div className="player-header">
                            <h3 className="player-name">{player.koreanName}</h3>
                            <span className="player-english-name">{player.name}</span>
                        </div>
                        
                        <div className="player-info">
                            <div className="info-row">
                                <span className="label">포지션:</span>
                                <span className="value">{player.position}</span>
                            </div>
                            <div className="info-row">
                                <span className="label">팀:</span>
                                <span className="value">{player.team}</span>
                            </div>
                            <div className="info-row">
                                <span className="label">가격:</span>
                                <span className="value">£{player.price}M</span>
                            </div>
                            <div className="info-row">
                                <span className="label">총 점수:</span>
                                <span className="value">{player.totalPoints}점</span>
                            </div>
                            <div className="info-row">
                                <span className="label">평균 점수:</span>
                                <span className="value">{player.averagePoints?.toFixed(1)}점</span>
                            </div>
                            <div className="info-row">
                                <span className="label">상태:</span>
                                <span className={`value status-badge ${getStatusClass(player.status)}`}>
                                    {getStatusText(player.status)}
                                </span>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {players.length === 0 && (
                <div className="no-players">검색 조건에 맞는 선수가 없습니다.</div>
            )}
        </div>
    );
};

export default PlayerList;