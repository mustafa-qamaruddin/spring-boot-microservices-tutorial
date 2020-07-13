package microservices.book.gamification.services;

import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;

public interface GameService {
    
    GameStats newAttemptForUser(
        Long userId, Long attemptId, boolean correct
    );

    GameStats retrieveStatsForUser(
        Long userId
    );

    /**
     * Gets the score for a given attempt
     * @param attemptId the attempt unique id
     * @return a {@link ScoreCard} with the details of the score for that attempt
     */
    ScoreCard getScoreForAttempt(Long attemptId);
}