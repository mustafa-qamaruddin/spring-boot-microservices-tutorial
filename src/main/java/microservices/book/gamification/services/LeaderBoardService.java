package microservices.book.gamification.services;

import microservices.book.gamification.domain.LeaderBoardRow;
import java.util.List;

public interface LeaderBoardService {

    List<LeaderBoardRow> getCurrentLeaderBoard();
    
}