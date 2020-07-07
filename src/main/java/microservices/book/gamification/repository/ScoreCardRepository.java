package microservices.book.gamification.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import microservices.book.gamification.domain.ScoreCard;
import java.util.List;
import microservices.book.gamification.domain.LeaderBoardRow;


public interface ScoreCardRepository extends CrudRepository<ScoreCard, Long> {

    @Query("SELECT SUM(s.score) FROM microservices.book.gamification.domain.ScoreCard s WHERE s.userId= :userId GROUP BY s.userId")
    int getTotalScoreFromUser(@Param("userId") final Long userId);

    @Query(
        "SELECT NEW microservice.book.gamification.domain.LeaderBoardRow(s.userId, SUM(s.score))"+
        "FROM microservices.book.gamification.domain.ScoreCard s"+
        "GROUP BY s.userId ORDER BY SUM(s.score) DESC"
    )
    List<LeaderBoardRow> findFirst10();
    
    List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final Long userId);
}