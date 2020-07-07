package microservices.book.gamification.services;

import microservices.book.gamification.domain.GameStats;

public interface GameService {
    
    GameStats newAttemptForUser(
        Long userId, Long attemptId, boolean correct
    );

    GameStats retrieveStatsForUser(
        Long userId
    );

}