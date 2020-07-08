package microservices.book.gamification.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import microservices.book.gamification.services.LeaderBoardService;
import microservices.book.gamification.domain.LeaderBoardRow;
import java.util.List;

@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {
    
    private final LeaderBoardService leaderBoardService;

    public LeaderBoardController(final LeaderBoardService leaderBoardService) {
        this.leaderBoardService = leaderBoardService;
    }

    @GetMapping
    public List<LeaderBoardRow> getLeaderBoard() {
        return leaderBoardService.getCurrentLeaderBoard();
    }

}