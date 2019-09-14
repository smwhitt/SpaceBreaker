package app.screens;

import app.app;
import app.components.Timer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * @author Samantha Whitt
 * helpful methods used for setting text on screens
 * example: set header font and alignment for end screen
 */
public class SpecialFont {
    /**
     * makes text large, very bolded, and in pixelated font (header)
     * @param headerText
     */
    public static void makeHeader(Text headerText) {
        headerText.setFont(Font.font("Krungthep", FontWeight.EXTRA_BOLD, 30));
        makeTextFont(headerText);
    }

    /**
     * makes text slightly smaller and in pixelated font (regular text)
     * @param text
     */
    public static void makePlainText(Text text) {
        text.setFont(Font.font("Krungthep", FontWeight.BOLD, 15));
        makeTextFont(text);
    }

    /**
     * makes text white and aligned
     * @param text
     */
    public static void makeTextFont(Text text) {
        text.setFill(Color.WHITE);
        text.setTextAlignment(TextAlignment.CENTER);
    }

    public static Text setSceneText(Text textName, String text, int x, int y) {
        textName.setText(text);
        textName.setX(x);
        textName.setY(y);
        return textName;
    }

    /**
     * sets text to display stats for end screens
     * @param stats
     * @param score
     * @param time
     */
    public static void setStats(Text stats, int score, int time) {
        stats.setText("\n\nScore: " + score + "\nTIME: " + Timer.getMinutes(time) + " min " + Timer.getSeconds(time) + " sec");
    }
}
