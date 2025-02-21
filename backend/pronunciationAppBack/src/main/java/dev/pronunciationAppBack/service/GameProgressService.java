package dev.pronunciationAppBack.service;

import dev.pronunciationAppBack.model.GameProgress;
import dev.pronunciationAppBack.repository.GameProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Date;

@Service
public class GameProgressService {

    @Autowired
    private GameProgressRepository gameProgressRepository;

    public List<GameProgress> getAllGameProgresses() {
        return gameProgressRepository.findAll();
    }

    public Optional<GameProgress> getGameProgressById(String id) {
        return gameProgressRepository.findById(id);
    }

    public GameProgress createGameProgress(GameProgress gameProgress) {
        return gameProgressRepository.save(gameProgress);
    }

    public GameProgress updateGameProgress(GameProgress gameProgress) {
        return gameProgressRepository.save(gameProgress);
    }

    public void deleteGameProgress(String id) {
        gameProgressRepository.deleteById(id);
    }

    public void deleteAllGameProgresses() {
        gameProgressRepository.deleteAll();
    }

    public boolean existsById(String id) {
        return gameProgressRepository.existsById(id);
    }

    public long getGameProgressCount() {
        return gameProgressRepository.count();
    }

    // Additional business logic methods
    public List<GameProgress> getGameProgressesByCurrentStage(GameProgress.Stage stage) {
        return gameProgressRepository.findByCurrentStage(stage);
    }

    public List<GameProgress> getGameProgressesByLastPlayedDateAfter(Date date) {
        return gameProgressRepository.findByLastPlayedDateAfter(date);
    }

    public List<GameProgress> getGameProgressesByWordsLearnedGreaterThan(int count) {
        return gameProgressRepository.findByWordsLearnedGreaterThan(count);
    }

    public GameProgress getGameProgressByAppUserId(String userId) {
        return gameProgressRepository.findByAppUserId(userId);
    }

    public List<GameProgress> getGameProgressesByCurrentScoreGreaterThan(int score) {
        return gameProgressRepository.findByCurrentScoreGreaterThan(score);
    }
}
