package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.CutoffTest;
import info.jayharris.minimax.Node;

public class TictactoeCutoffTest implements CutoffTest<MinimaxState, MinimaxAction> {

    private static TictactoeCutoffTest instance;

    private TictactoeCutoffTest() { }

    public static TictactoeCutoffTest instance() {
        if (instance == null) {
            instance = new TictactoeCutoffTest();
        }

        return instance;
    }

    @Override
    public boolean apply(Node<MinimaxState, MinimaxAction> node) {
        return false;
    }

    public static TictactoeCutoffTest create() {
        return TictactoeCutoffTest.instance();
    }
}
