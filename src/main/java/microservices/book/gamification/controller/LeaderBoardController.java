package microservices.book.gamification.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import microservices.book.gamification.services.LeaderBoardService;
import microservices.book.gamification.services.LeaderBoardService;


@RestController
@RequestMapping("/leaders")
public class LeaderBoardController {
    
    private final LeaderBoardService leaderBoardService;

    public LeaderBoardController(final LeaderBoardService leaderBoardService) {

    }

}