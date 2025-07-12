package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.*;

public class Controller {
	
    @FXML private Text wordD;
    @FXML private Text hint;
    @FXML private Text wrongGuessCount;

    @FXML private GridPane letterGrid;
    
    @FXML private ImageView hangmanImage;

    private final List<String> words=Arrays.asList("ELEPHANT", "KANGAROO", "TOUCAN", "GIRAFFE", "CROCODILE");
    private final Map<String, String>wordHints =Map.of(
    	    "ELEPHANT", "A large gray animal with a trunk",
    	    "KANGAROO", "An Australian marsupial",
    	    "TOUCAN", "A bird with a large colorful beak",
    	    "GIRAFFE", "The tallest land animal",
    	    "CROCODILE", "A reptile found in rivers"
    	);

    private String selectedW;
    private Set<Character> guessedLetters;
    private int wrongGuesses =0;
    private final int maxWrong =6;

    
    @FXML
    public void initialize() {
        setupGame();
    }

    public void setupGame() {
        guessedLetters =new HashSet<>();
        wrongGuesses =0;

        
 
        Random rand = new Random();
        selectedW = words.get(rand.nextInt(words.size()));
        hint.setText("Hint: " + wordHints.get(selectedW));
        wrongGuessCount.setText("Wrong guesses: " + wrongGuesses + "/" + maxWrong);

       

       
        updateWordDisplay();

       
        letterGrid.getChildren().clear();
        char c ='A';
        for(int i = 0; i < 26; i++, c++) {
            Button btn = new Button(String.valueOf(c));
            btn.setPrefSize(40, 40);
            btn.setOnAction(e ->handleGuess(btn));
            letterGrid.add(btn, i%7, i/7);
        }

        updateImage();
        wrongGuessCount.setText("Wrong guesses: 0/" +maxWrong);

    }

    private void handleGuess(Button btn) {
    	
        String letter = btn.getText();
        btn.setDisable(true);
        
        char guess =letter.charAt(0);
        wrongGuessCount.setText("Wrong guesses: " + wrongGuesses+ "/" +maxWrong);

        if(selectedW.contains(letter)) {
        	
            guessedLetters.add(guess);
            
            updateWordDisplay();
            
            if(isWordGuessed()) {
                showAlert("Congratulations!", "You guessed the word!");
                disableAllButtons();
            }
        } else {
            wrongGuesses++;
            updateImage();
            if(wrongGuesses >= maxWrong) {
                showAlert("Try Again", "You failed! The word was: " + selectedW);
                disableAllButtons();
            }
        }
    }

    private void updateWordDisplay() {
        StringBuilder display = new StringBuilder();
        for(char c : selectedW.toCharArray()) {
            if(guessedLetters.contains(c)) {
                display.append(c).append(" ");
            } else{
                display.append("_ ");
            }
        }
        wordD.setText(display.toString().trim());
    }

    private void updateImage() {
        String imagePath = "/images/hangman" + wrongGuesses + ".png";
        URL imageUrl = getClass().getResource(imagePath);
        
        if(imageUrl != null) {
            hangmanImage.setImage(new Image(imageUrl.toExternalForm()));
        } else {
            System.err.println("Image not found: " + imagePath);
        }
    }


    private boolean isWordGuessed() {
        for(char c :selectedW.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private void disableAllButtons() {
        letterGrid.getChildren().forEach(node -> node.setDisable(true));
    }

    @FXML
    public void resetGame() {
        setupGame();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

//    @FXML private Text      wordDisplay;
//    @FXML private Button    btnL, btnH, btnM, btnP;
//    @FXML private Button    btnO2, btnU2, btnA2, btnI2;
//    @FXML private Button    btnN3, btnK3, btnR3, btnB3;
//    @FXML private Button    btnF4, btnR4, btnC4, btnA4;
//
//    @FXML
//    public void initialize() {
//        lionImage.setImage(new Image(
//            getClass().getResource("/images/bulbasaur.jpg")
//                     .toExternalForm()));
//        toucanImage.setImage(new Image(
//            getClass().getResource("/images/charmander.jpg")
//                     .toExternalForm()));
//        monkeyImage.setImage(new Image(
//            getClass().getResource("/images/latias.jpg")
//                     .toExternalForm()));
//        giraffeImage.setImage(new Image(
//            getClass().getResource("/images/pikachu.jpg")
//                     .toExternalForm()));
//    }
//
//    @FXML
//    private void handleLetterClick(ActionEvent evt) {
//        Button btn = (Button) evt.getSource();
//        String id = btn.getId();
//
//        boolean correct = switch (id) {
//            case "btnL"  -> setWord(word1, "LION");
//            case "btnO2" -> setWord(word2, "TOUCAN");
//            case "btnN3" -> setWord(word3, "MONKEY");
//            case "btnF4" -> setWord(word4, "GIRAFFE");
//            default      -> false;
//        };
//
//        Alert alert = new Alert(correct ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
//        alert.setHeaderText(null);
//        alert.setContentText(correct ? "Correct!" : "Try Again");
//        alert.showAndWait();
//    }
//
//    private boolean setWord(Text txt, String full) {
//        txt.setText(full);
//        return true;
//    }
//}
