// Assignment 02: Some (Relatively) Easy Things For You To Do In Java
// Due: Mon Jan 29, 2024 11:59pm
//
// Author: Kaylee Lewis
//
// Question 1: A Rather Simple First Problem in Java
// Build in Java: the Bowling Game kata.
//
// Write a class Game that has two methods
//    void roll(int) is called each time the player rolls a ball.
//      The argument is the number of pins knocked down.
//    int score() returns the total score for that game.
//
//  “Engineer Notebook: An Extreme Programming Episode” by Robert C. Martin
//  http://butunclebob.com/ArticleS.UncleBob.TheBowlingGameKata
// http://butunclebob.com/files/downloads/Bowling%20Game%20Kata.ppt

// Import statement for Arrays class
import java.util.Arrays;

public class BowlingGame
{

    // 21 is the max number of rolls
    private final int[] rolls = new int[21];

    // Keeps track of the current roll
    private int currentRoll = 0;

    // Rolls the ball
    public void roll(int pins)
    {

        // Adds the number of pins knocked down to the current roll
        rolls[currentRoll++] = pins;
    }

    // Calculates the score
    public int score()
    {
        int score = 0;
        int frameIndex = 0;

        // Loops through 10 is the max number of frames
        for (int frame = 0; frame < 10; frame++)
        {
            // If the current roll is a strike
            if (isStrike(frameIndex))
            {
                // Add 10 + the next two rolls to the score
                score += 10 + strikeBonus(frameIndex);
                // Increment the frame index by 1
                frameIndex++;
            }
            // If the current roll is a spare
            else if (isSpare(frameIndex))
            {
                // Add 10 + the next roll to the score
                score += 10 + spareBonus(frameIndex);
                // Increment the frame index by 2
                frameIndex += 2;
            }
            // If the current roll is not a strike or a spare
            else
            {
                // Add the sum of the current roll and the next roll to the score
                score += sumOfBallsInFrame(frameIndex);
                // Increment the frame index by 2
                frameIndex += 2;
            }
        }
        // Return the score
        return score;
    }

    // Checks if the current roll is a strike
    private boolean isStrike(int frameIndex)
    {
        return rolls[frameIndex] == 10;
    }

    // Calculates the sum of the current roll and the next roll
    private int sumOfBallsInFrame(int frameIndex)
    {
        return rolls[frameIndex] + rolls[frameIndex + 1];
    }

    // Calculates the bonus for a spare
    private int spareBonus(int frameIndex)
    {
        return rolls[frameIndex + 2];
    }

    // Calculates the bonus for a strike
    private int strikeBonus(int frameIndex)
    {
        return rolls[frameIndex + 1] + rolls[frameIndex + 2];
    }

    // Checks if the current roll is a spare
    private boolean isSpare(int frameIndex)
    {
        return rolls[frameIndex] + rolls[frameIndex + 1] == 10;
    }

    // Returns the rolls
    public int[] getRolls()
    {
        return rolls;
    }

    public static void main(String[] args)
    {
        // Create an instance of the BowlingGame
        BowlingGame game = new BowlingGame();

        // Simulate a game of bowling
        game.roll(1);
        game.roll(4);
        game.roll(4);
        game.roll(5);
        game.roll(6);
        game.roll(4);
        game.roll(5);
        game.roll(5);
        game.roll(10);
        game.roll(0);
        game.roll(1);
        game.roll(7);
        game.roll(3);
        game.roll(6);
        game.roll(4);
        game.roll(10);
        game.roll(2);
        game.roll(8);
        game.roll(6);

        // Print the rolls
        System.out.println("Rolls: " + Arrays.toString(game.getRolls()));

        // Print the final score
        System.out.println("Final Score: " + game.score());
    }
}
