package info.jayharris.tictactoe;

import com.google.common.collect.ImmutableList;
import info.jayharris.tictactoe.player.Player;
import info.jayharris.tictactoe.player.RandomMovePlayer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

public class TictactoeBuilder {

    Player x, o;

    int size;

    Iterator<Move> movesIterator;

    public TictactoeBuilder() {
        this.size = 3;

        x = new RandomMovePlayer(Piece.X);
        o = new RandomMovePlayer(Piece.O);
    }

    public TictactoeBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public TictactoeBuilder setPlayer(Player player) {
        if (player.getPiece() == Piece.X) {
            x = player;
        }
        else if (player.getPiece() == Piece.O) {
            o = player;
        }
        return this;
    }

    public TictactoeBuilder setAllMoves(Move... moves) {
        this.movesIterator = Arrays.asList(moves).iterator();
        return this;
    }

    public Tictactoe build() {
        Board board = Board.empty(size);

        for (int i = 0; movesIterator.hasNext(); ++i) {
            board.setPiece(movesIterator.next(), i % 2 == 0 ? Piece.X : Piece.O);
        }

        return new Tictactoe(x, o, board);
    }
}
