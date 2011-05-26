package controllers;

import javax.inject.Inject;
import play.mvc.*;

public class Application extends Controller {

    @Inject static Game game;

    public static void index() {
        game.reset();
        String message = "You can start guessing ....";
        int smallest = game.getSmallest();
        int biggest = game.getBiggest();
        int remainingGuesses = game.getRemainingGuesses();
        render(smallest, biggest, remainingGuesses, message, game);
    }

    public static void guess(int guess) {
        String message = "";
        boolean guessed = game.check(guess);
        if (guessed) {
           message = "You guessed " + guess + " in "
                   + (Game.MAX_ATTEMPTS - game.getRemainingGuesses())
                   + " attempts";
        } else if (guess < game.getNumber()) {
           message = "Higher ! ";
        } else if (guess > game.getNumber()) {
           message = "Lower ! ";
        }
        int smallest = game.getSmallest();
        int biggest = game.getBiggest();
        int remainingGuesses = game.getRemainingGuesses();
        renderTemplate("Application/index.html", guessed, message,
                smallest, biggest, remainingGuesses);
    }

    public static void reset() {
        index();
    }
}