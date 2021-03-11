package com.adaptionsoft.games.trivia.runner;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class GameRunnerTest {

    @Test
    public void approvalTest() {
        GameRunner gameRunner = new GameRunner();

        Approvals.verify(gameRunner.runGame());
    }
}
