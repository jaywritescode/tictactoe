package info.jayharris.tictactoe;

import java.util.Objects;

public class Outcome {

    private final Piece outcome;

    Outcome(Piece outcome) {
        this.outcome = outcome;
    }

    static Outcome x() {
        return new Outcome(Piece.X);
    }

    static Outcome o() {
        return new Outcome(Piece.O);
    }

    static Outcome tie() {
        return new Outcome(null);
    }

    boolean isTie() {
        return Objects.isNull(outcome);
    }

    Piece winner() {
        return outcome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outcome outcome1 = (Outcome) o;
        return outcome == outcome1.outcome;
    }

    @Override
    public int hashCode() {

        return Objects.hash(outcome);
    }
}
