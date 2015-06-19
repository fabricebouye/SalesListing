package test.scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.css.PseudoClass;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Classe utilitaire pour la gestion des labels dans l'interface graphique.
 * @author Fabrice Bouyé
 */
public enum LabelUtils {

    INSTANCE;

    /**
     * La valeur d'une pièce d'or en pièces de cuivre.
     */
    private static final int GOLD_VALUE = 10000;
    /**
     * La valeur d'une pièce d'arge en pièces de cuivre.
     */
    private static final int SILVER_VALUE = 100;

    /**
     * Pseudo-classe appliquée aux pièces d'or.
     */
    private static final PseudoClass FLAVOR_PSEUDO_CLASS = PseudoClass.getPseudoClass("flavor"); // NOI18N.
    /**
     * Pseudo-classe appliquée aux pièces d'or.
     */
    private static final PseudoClass GOLD_PSEUDO_CLASS = PseudoClass.getPseudoClass("gold"); // NOI18N.
    /**
     * Pseudo-classe appliquée aux pièces d'argent.
     */
    private static final PseudoClass SILVER_PSEUDO_CLASS = PseudoClass.getPseudoClass("silver"); // NOI18N.
    /**
     * Pseudo-classe appliquée aux pièces de cuivre.
     */
    private static final PseudoClass COPPER_PSEUDO_CLASS = PseudoClass.getPseudoClass("copper"); // NOI18N.

    /**
     * Génère les labels utilisés pour afficher la description d'un objet.
     * @param value La valeur de la description.
     * @return Une instance de {@code List<Text>} non-modifiable, jamais {@code null}.
     */
    public static List<Text> labelsForDescription(final String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.unmodifiableList(new ArrayList(0));
        }
        final String[] tokens = value.split("\\n"); // NOI18N.
        final List<Text> result = Arrays.stream(tokens)
                .map(token -> {
                    final boolean isFlavor = token.startsWith("<c=@flavor>"); // NOI18N.
                    final String text = token.replace("<c=@flavor>", ""); // NOI18N.
                    final Text label = new Text(text + '\n'); // NOI18N.
                    label.getStyleClass().add("item-description-label"); // NOI18N.
                    label.pseudoClassStateChanged(FLAVOR_PSEUDO_CLASS, isFlavor);
                    return label;
                })
                .collect(Collectors.toList());
        return Collections.unmodifiableList(result);
    }

    /**
     * Génère les labels utilisés pour afficher des sommes en monnaie du jeu.
     * <br/>La valeur 0 est cachée.
     * @param value La valeur en pièces de cuivre.
     * @return Une instance de {@code List<Node>} non-modifiable, jamais {@code null}.
     */
    public static List<Node> labelsForCoins(final int value) {
        return labelsForCoins(value, false);
    }

    /**
     * Génère les labels utilisés pour afficher des sommes en monnaie du jeu.
     * @param value La valeur en pièces de cuivre.
     * @param showEmpty Si {@code true} la valeur 0 est affichée.
     * @return Une instance de {@code List<Node>} non-modifiable, jamais {@code null}.
     */
    public static List<Node> labelsForCoins(final int value, final boolean showEmpty) {
        final List<Node> result = new ArrayList<>();
        final int gold = value / GOLD_VALUE;
        final int silver = (value - gold * GOLD_VALUE) / SILVER_VALUE;
        final int copper = (value - gold * GOLD_VALUE - silver * SILVER_VALUE);
        if (showEmpty || gold > 0) {
            final Region goldCoin = new Region();
            goldCoin.getStyleClass().add("coin"); // NOI18N.
            goldCoin.pseudoClassStateChanged(GOLD_PSEUDO_CLASS, true);
            final Text goldText = new Text(String.valueOf(gold));
            goldText.getStyleClass().add("coin-label"); // NOI18N.
            goldText.pseudoClassStateChanged(GOLD_PSEUDO_CLASS, true);
            result.add(goldText);
            result.add(new Text(" ")); // NOI18N.
            result.add(new Group(goldCoin));
            result.add(new Text(" ")); // NOI18N.
        }
        if (showEmpty || silver > 0) {
            final Region silverCoin = new Region();
            silverCoin.getStyleClass().add("coin"); // NOI18N.
            silverCoin.pseudoClassStateChanged(SILVER_PSEUDO_CLASS, true);
            final Text silverText = new Text(String.valueOf(silver));
            silverText.getStyleClass().add("coin-label"); // NOI18N.
            silverText.pseudoClassStateChanged(SILVER_PSEUDO_CLASS, true);
            result.add(silverText);
            result.add(new Text(" ")); // NOI18N.
            result.add(new Group(silverCoin));
            result.add(new Text(" ")); // NOI18N.
        }
        if (showEmpty || copper > 0) {
            final Region copperCoin = new Region();
            copperCoin.getStyleClass().add("coin"); // NOI18N.
            copperCoin.pseudoClassStateChanged(COPPER_PSEUDO_CLASS, true);
            final Text copperText = new Text(String.valueOf(copper));
            copperText.getStyleClass().add("coin-label"); // NOI18N.
            copperText.pseudoClassStateChanged(COPPER_PSEUDO_CLASS, true);
            result.add(copperText);
            result.add(new Text(" ")); // NOI18N.
            result.add(new Group(copperCoin));
            result.add(new Text(" ")); // NOI18N.
        }
        if (!result.isEmpty()) {
            result.remove(result.size() - 1);
        }
        // Test.
//        result.add(new Text(" (" + value + ")"));
        //
        return Collections.unmodifiableList(result);
    }
}
