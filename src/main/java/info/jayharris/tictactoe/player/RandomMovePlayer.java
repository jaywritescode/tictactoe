package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A player that makes randomly-chosen moves.
 */
public class RandomMovePlayer extends Player {

    public RandomMovePlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
        Collection<Move> legalMoves = game.getLegalMoves();

        return new ArrayList<>(legalMoves).get(RandomUtils.nextInt(0, legalMoves.size()));
    }
}
