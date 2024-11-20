package com.example.fxgame;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class ScrollableTextArea extends VBox {

    private final TextArea textArea;
    private final int maxLines = 100;

    public ScrollableTextArea() {
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        this.getChildren().add(textArea);
    }

    public void appendText(String text) {

        Platform.runLater(() -> {
            String[] lines = textArea.getText().split("\n");
            if (lines.length >= maxLines) {
                textArea.setText(String.join("\n", java.util.Arrays.copyOfRange(lines, lines.length - maxLines + 1, lines.length)));
            }
            textArea.appendText(text + "\n");
            textArea.positionCaret(textArea.getLength());
        });
    }
}