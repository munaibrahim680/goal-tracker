package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for Score class
class ScoreTest {
    private Score testScore;
    private Score testScore2;

    @BeforeEach
    public void runBefore() {
        testScore = new Score(5);
    }

    @Test
    void testMainConstructor() {
        assertEquals(5, testScore.getScoreVal());

        LocalDate expectedDate = LocalDate.now();
        assertEquals(expectedDate, testScore.getScoreDate());
    }

    @Test
    void testConstructorWithDate() {
        testScore2 = new Score(3, LocalDate.of(2023, 5, 20));
        assertEquals(3, testScore2.getScoreVal());
        assertEquals(LocalDate.of(2023, 5, 20), testScore2.getScoreDate());
    }

}