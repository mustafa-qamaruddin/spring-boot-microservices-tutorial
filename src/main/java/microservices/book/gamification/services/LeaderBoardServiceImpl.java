package microservices.book.gamification.services;

import org.springframework.stereotype.Service;
import java.util.List;
import microservices.book.gamification.repository.ScoreCardRepository;
import microservices.book.gamification.domain.LeaderBoardRow;

@Service
public class LeaderBoardServiceImpl implements LeaderBoardService {

    private ScoreCardRepository scoreCardRepository;

    LeaderBoardServiceImpl(
        ScoreCardRepository scoreCardRepository
    ) {
        this.scoreCardRepository = scoreCardRepository;
    }

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {
        return scoreCardRepository.findFirst10();
    }
    
}