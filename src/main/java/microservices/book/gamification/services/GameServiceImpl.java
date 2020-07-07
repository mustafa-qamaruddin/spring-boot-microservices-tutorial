package microservices.book.gamification.services;


import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.repository.BadgeCardRepository;
import microservices.book.gamification.repository.ScoreCardRepository;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.services.GameService;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Optional;


@Service
@Slf4j
public class GameServiceImpl implements GameService {

    private ScoreCardRepository scoreCardRepository;
    private BadgeCardRepository badgeCardRepository;

    GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {

        this.scoreCardRepository = scoreCardRepository;
        this.badgeCardRepository = badgeCardRepository;
    }

    @Override
    public GameStats newAttemptForUser(
        final Long userId,
        final Long attemptId,
        final boolean correct
    ) {
        if ( correct ) {
            ScoreCard scoreCard = new ScoreCard(userId, attemptId);
            scoreCardRepository.save(scoreCard);
            log.info(
                "User with id {} scored {} points for attempt id {}",
                userId, scoreCard.getScore(), attemptId
            );
            List<BadgeCard> badgeCards = processForBadges(userId, attemptId);
            return new GameStats(
                userId,
                scoreCard.getScore(),
                badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList())
            );
        }
        return GameStats.emptyStats(userId);
    }

    private List<BadgeCard> processForBadges(
        final Long userId,
        final Long attemptId
    ) {
        List<BadgeCard> badgeCards = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalScoreFromUser(userId);
        log.info("New Score for user {} is {}", userId, totalScore);
        List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
        List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore, 100, userId).ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore, 500, userId).ifPresent(badgeCards::add);
        checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.GOLD_MULTIPLICATOR, totalScore, 999, userId).ifPresent(badgeCards::add);

        if ( scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.First_WON)){
            BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
            badgeCards.add(firstWonBadge);
        }

        return badgeCards;
    }

    @Override
    public GameStats retrieveStatsForUser(final Long userId) {
        int score = scoreCardRepository.getTotalScoreFromUser(userId);
        List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
        return new GameStats(userId, score, badgeCards.stream().map(BadgeCard::getBadge).collect(Collectors.toList());
    }

    private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(final List<BadgeCard> badgeCards, final Badge badge, final int score,
    final int scoreThreshold, final Long userId) {

        if ( score >= scoreThreshold && !containsBadge(badgeCards, badge)) {
            return Optional.of(giveBadgeToUser(badge, userId));
        }

        return Optional.empty();
    }

    private boolean containsBadge(final List<BadgeCard> badgeCards, final Badge badge) {
        return badgeCards.stream().anyMatch(b-> b.getBadge().equals(badge));
    }

    private BadgeCard giveBadgeToUser(final Badge badge, final Long userId) {
        BadgeCard badgeCard = new BadgeCard(userId, badge);
        badgeCardRepository.save(badgeCard);
        log.info("User with id {} won a new badge: {}", userId, badge);
        return badgeCard;
    }
}