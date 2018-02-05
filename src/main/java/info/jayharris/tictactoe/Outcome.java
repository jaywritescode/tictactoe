package info.jayharris.tictactoe;

import java.util.Objects;
import java.util.Optional;

public class Outcome {

    final Optional<Piece> outcome;

    protected Outcome(Piece outcome) {
        this.outcome = Optional.ofNullable(outcome);
    }

    public static Outcome X() {
        return new Outcome(Piece.X);
    }

    public static Outcome O() {
        return new Outcome(Piece.O);
    }

    public static Outcome tie() {
        return new Outcome(null);
    }

    public boolean isTie() {
        return !outcome.isPresent();
    }

    public Piece winner() {
        return outcome.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outcome outcome1 = (Outcome) o;
        return Objects.equals(outcome, outcome1.outcome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(outcome);
    }
}
