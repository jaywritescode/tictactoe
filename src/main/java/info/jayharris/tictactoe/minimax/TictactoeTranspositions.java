package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.transposition.Transpositions;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.BoardSymmetry;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

public class TictactoeTranspositions implements Transpositions<MinimaxState, MinimaxAction> {

    Map<BoardSymmetry.Wrapper<Board>, Double> map = new HashMap<>();

    @Override
    public OptionalDouble get(MinimaxState state) {
        BoardSymmetry.Wrapper<Board> board = BoardSymmetry.create(state.getBoard());
        return map.containsKey(board) ? OptionalDouble.of(map.get(board)) : OptionalDouble.empty();
    }

    @Override
    public void put(MinimaxState state, double utility) {
        BoardSymmetry.Wrapper<Board> board = BoardSymmetry.create(state.getBoard());

        if (map.containsKey(board)) {
            if (map.get(board) != utility) {
                throw new RuntimeException("Somehow you calculated two different utilities for the same state.");
            }
        }
        else {
            map.put(board, utility);
        }
    }

    public static TictactoeTranspositions create() {
        return new TictactoeTranspositions();
    }
}
