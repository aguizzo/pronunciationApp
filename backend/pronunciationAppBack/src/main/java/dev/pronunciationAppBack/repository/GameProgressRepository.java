package dev.pronunciationAppBack.repository;

import dev.pronunciationAppBack.model.GameProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface GameProgressRepository extends JpaRepository<GameProgress, String> {
    List<GameProgress> findByCurrentStage(GameProgress.Stage stage);

    List<GameProgress> findByLastPlayedDateAfter(Date date);

    List<GameProgress> findByWordsLearnedGreaterThan(int count);

    GameProgress findByAppUserId(String userId);

    List<GameProgress> findByCurrentScoreGreaterThan(int score);
}
