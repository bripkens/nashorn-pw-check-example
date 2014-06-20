package de.codecentric;

/**
 * @author Ben Ripkens <ben.ripkens@codecentric.de>
 */
public class Strength {
  private final int entropy;

  private final int score;

  private final int crackTimeSeconds;

  public Strength(int entropy, int score, int crackTimeSeconds) {
    this.entropy = entropy;
    this.score = score;
    this.crackTimeSeconds = crackTimeSeconds;
  }

  public int getEntropy() {
    return entropy;
  }

  public int getScore() {
    return score;
  }

  public int getCrackTimeSeconds() {
    return crackTimeSeconds;
  }
}
