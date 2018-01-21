package info.jayharris.tictactoe;

import java.util.Optional;

public class Outcome {

    final Optional<Piece> outcome;

    public Outcome(Piece outcome) {
        this.outcome = Optional.ofNullable(outcome);
    }

    public static Outcome tie() {
        return new Outcome(null);
    }
}
