package info.jayharris.tictactoe.minimax.assertions;

import info.jayharris.tictactoe.minimax.MinimaxAction;
import org.assertj.core.api.Assertions;

public class ProjectAssertions extends Assertions {

    public static MinimaxActionAssert assertThat(MinimaxAction actual) {
        return new MinimaxActionAssert(actual);
    }
}
