package controllers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import play.mvc.Scope.Session;

@RequestScoped
public class Game {

    public static final int MAX_ATTEMPTS = 10;

    private int number;

    private int smallest;

    @Inject @MaxNumber
    private int maxNumber;

    private int biggest;

    private int remainingGuesses;

    @Inject
    private Session session;

    @Inject @Random
    private Instance<Integer> randomNumber;

    public boolean check(int guess) {
        if (guess > number) {
            biggest = guess - 1;
        } else if (guess < number) {
            smallest = guess + 1;
        } else if (guess == number) {
            remainingGuesses--;
            push();
            return true;
        }
        remainingGuesses--;
        push();
        return false;
    }

    @PostConstruct
    private void update() {
        number = getInSession("number");
        smallest = getInSession("smallest");
        biggest = getInSession("biggest");
        remainingGuesses = getInSession("remainingGuesses");
    }

    private int getInSession(final String key) {
        String value = session.get(key);
        if (value == null) {
            return -1;
        }
        return Integer.valueOf(value);
    }

    public void push() {
        session.put("number", number);
        session.put("smallest", smallest);
        session.put("remainingGuesses", remainingGuesses);
    }

    public void reset() {
        int random = randomNumber.get();
        session.put("smallest", 0);
        session.put("remainingGuesses", MAX_ATTEMPTS);
        session.put("biggest", maxNumber);
        session.put("number", random);
        update();
    }

    public int getBiggest() {
        return biggest;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getNumber() {
        return number;
    }

    public int getRemainingGuesses() {
        return remainingGuesses;
    }

    public int getSmallest() {
        return smallest;
    }

    public int getRandomNumber() {
        return randomNumber.get();
    }
}
