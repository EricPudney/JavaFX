package com.example.fxgame;

import characters.Character;
import characters.Hero;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CharacterCreation implements AppAwareController {

    private RPGApplication app;

    public void setApp(RPGApplication app) {
        this.app = app;
    }

    @FXML
    private TextField name;

    @FXML
    private ChoiceBox<Character.Type> characterType;

    @FXML
    private ChoiceBox<Character.Race> characterRace;

    @FXML
    private void initialize() {
        characterType.getItems().setAll(Character.Type.values());
        characterRace.getItems().setAll(Character.Race.values());
    }

    @FXML
    public void createCharacter() throws IOException {
        String characterName = name.getText();
        Character.Type chosenType = characterType.getValue();
        Character.Race chosenRace = characterRace.getValue();
        if (characterName.isEmpty() || chosenType == null || chosenRace == null) {
            showAlert("Error", "Please fill out all fields before submitting.", Alert.AlertType.ERROR);
            return;
        }
        Hero hero = createHero(characterName, chosenType, chosenRace);
        app.setHero(hero);
        showAlert("Character created!", app.getHero().toString(), Alert.AlertType.INFORMATION);
        app.startDungeonRun();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Hero createHero(String name, Character.Type type, Character.Race race) {
        int attack = 3;
        int health = 12;
        int shield = 1;
        double initiative = 0.5;
        double dodge = 0;
        double block = 0;
        double evasion = 0.45;
        switch (type) {
            case warrior:
                evasion -= 0.1;
                block += 0.3;
                dodge += 0.05;
                shield += 1;
                break;
            case mage:
                attack += 1;
                health -= 2;
                block += 0.1;
                dodge += 0.15;
                break;
            case ranger:
                attack -= 1;
                evasion += 0.05;
                block += 0.15;
                dodge += 0.25;
                break;
        }
        switch (race) {
            case human:
                break;
            case elf:
                initiative += 0.05;
                evasion += 0.05;
                dodge += 0.05;
                health -= 2;
                break;
            case dwarf:
                initiative -= 0.05;
                evasion -= 0.05;
                dodge -= 0.05;
                health += 2;
                break;
        }
        return new Hero(attack, health, shield, initiative, dodge, block, evasion, type, race, name);
    }
}

