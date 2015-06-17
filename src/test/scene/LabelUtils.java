package test.scene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

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
            final Label goldLabel = new Label(String.valueOf(gold));
            goldLabel.getStyleClass().add("coin-label");
            goldLabel.pseudoClassStateChanged(GOLD_PSEUDO_CLASS, true);
            goldLabel.setGraphic(goldCoin);
            result.add(goldLabel);
            result.add(new Label(" "));
        }
        if (silver > 0 || gold > 0) {
            final Region silverCoin = new Region();
            silverCoin.getStyleClass().add("coin");
            silverCoin.pseudoClassStateChanged(SILVER_PSEUDO_CLASS, true);
            final Label silverLabel = new Label(String.valueOf(silver));
            silverLabel.getStyleClass().add("coin-label");
            silverLabel.pseudoClassStateChanged(SILVER_PSEUDO_CLASS, true);
            silverLabel.setGraphic(silverCoin);
            result.add(silverLabel);
            result.add(new Label(" "));
        }
        final Region copperCoin = new Region();
        copperCoin.getStyleClass().add("coin");
        copperCoin.pseudoClassStateChanged(COPPER_PSEUDO_CLASS, true);
        final Label copperLabel = new Label(String.valueOf(copper));
        copperLabel.getStyleClass().add("coin-label");
        copperLabel.pseudoClassStateChanged(COPPER_PSEUDO_CLASS, true);
        copperLabel.setGraphic(copperCoin);
        result.add(copperLabel);
        //
        return Collections.unmodifiableList(result);
    }
}
