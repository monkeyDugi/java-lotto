package lotto;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Rank {

    MISS(0, 0),
    FIFTH(3, 5_000),
    FOURTH(4, 50_000),
    THIRD(5, 1_500_000),
    SECOND(5, 30_000_000),
    FIRST(6, 2_000_000_000);

    private final int countOfMatch;
    private final int winningMoney;

    Rank(int countOfMatch, int winningMoney) {
        this.countOfMatch = countOfMatch;
        this.winningMoney = winningMoney;
    }

    public static Rank valueOf(int countOfMatch, boolean matchBonus) {
        validate(countOfMatch);

        if (matchBonus) {
            return SECOND;
        }

        Stream<Rank> stream = Arrays.stream(values());
        return stream.filter(rank -> countOfMatch == rank.getCountOfMatch())
                .findFirst()
                .orElse(MISS);
    }

    private static void validate(int countOfMatch) {
        if (countOfMatch < 0) {
            throw new IllegalArgumentException("맞춘 개수는 0보다 작을 수 없습니다.");
        }

        if (countOfMatch > 6) {
            throw new IllegalArgumentException("맞춘 개수는 6보다 클 수 없습니다.");
        }
    }

    public boolean equalsMatchCount(int countOfMatch) {
        return this.countOfMatch == countOfMatch;
    }

    public int getCountOfMatch() {
        return countOfMatch;
    }

    public int getWinningMoney() {
        return winningMoney;
    }

}