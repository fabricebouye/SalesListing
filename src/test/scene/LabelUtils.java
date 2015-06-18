package test.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.css.PseudoClass;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * @author Fabrice Bouyé
 */
public enum LabelUtils {

    INSTANCE;

    private static final int GOLD_VALUE = 10000;
    private static final int SILVER_VALUE = 100;

    private static final PseudoClass GOLD_PSEUDO_CLASS = PseudoClass.getPseudoClass("gold");
    private static final PseudoClass SILVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("silver");
    private static final PseudoClass COPPER_PSEUDO_CLASS = PseudoClass.getPseudoClass("copper");

    public static List<Node> labelsForCoins(final int value) {
        final List<Node> result = new ArrayList<>();
        final int gold = value / GOLD_VALUE;
        final int silver = (value - gold * GOLD_VALUE) / SILVER_VALUE;
        final int copper = (value - gold * GOLD_VALUE - silver * SILVER_VALUE);
        if (gold > 0) {
            final Region goldCoin = new Region();
            goldCoin.getStyleClass().add("coin");
            goldCoin.pseudoClassStateChanged(GOLD_PSEUDO_CLASS, true);
            final Text goldText = new Text(String.valueOf(gold));
            goldText.getStyleClass().add("coin-label");
            goldText.pseudoClassStateChanged(GOLD_PSEUDO_CLASS, true);
            result.add(goldText);
            result.add(new Text(" "));
            result.add(new Group(goldCoin));
            result.add(new Text(" "));
        }
        if (silver > 0 || gold > 0) {
            final Region silverCoin = new Region();
            silverCoin.getStyleClass().add("coin");
            silverCoin.pseudoClassStateChanged(SILVER_PSEUDO_CLASS, true);
            final Text silverText = new Text(String.valueOf(silver));
            silverText.getStyleClass().add("coin-label");
            silverText.pseudoClassStateChanged(SILVER_PSEUDO_CLASS, true);
            result.add(silverText);
            result.add(new Text(" "));
            result.add(new Group(silverCoin));
            result.add(new Text(" "));
        }
        final Region copperCoin = new Region();
        copperCoin.getStyleClass().add("coin");
        copperCoin.pseudoClassStateChanged(COPPER_PSEUDO_CLASS, true);
        final Text copperText = new Text(String.valueOf(copper));
        copperText.getStyleClass().add("coin-label");
        copperText.pseudoClassStateChanged(COPPER_PSEUDO_CLASS, true);
        result.add(copperText);
        result.add(new Text(" "));
        result.add(new Group(copperCoin));
        // Test.
        result.add(new Text(" (" + value + ")"));
        //
        return Collections.unmodifiableList(result);
    }
}
