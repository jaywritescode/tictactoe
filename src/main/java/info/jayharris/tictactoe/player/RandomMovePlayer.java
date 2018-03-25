package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import org.apache.commons.lang3.RandomUtils;

import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A player that makes randomly-chosen moves.
 */
public class RandomMovePlayer extends Player {

    public RandomMovePlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
//        Board board = game.copyBoard();
//        Move[] legalMoves = IntStream.range(0, board.numSquares())
//                .filter(isOccupied(board).negate())
//                .mapToObj(Move::at)
//                .collect(Collectors.toList())
//                .toArray(new Move[] {});
//
//        return legalMoves[RandomUtils.nextInt(0, legalMoves.length)];
        return null;
    }

    private IntPredicate isOccupied(Board board) {
        return board::isOccupied;
    }
}
