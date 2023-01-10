package entities;


import controller.UserIO;
import entities.chancecards.ChanceAction;
import entities.fields.Brewery;
import entities.fields.Ferry;
import entities.fields.Field;
import language.Message;

public class ChanceCardImpl implements ChanceAction {
    private final GameBoard gameBoard;
    private final UserIO userIO;

    public ChanceCardImpl(GameBoard gameBoard, UserIO userIO) {
        this.gameBoard = gameBoard;
        this.userIO = userIO;
    }

    @Override
    public void getOutOfJail() {
        Player currentPlayer = gameBoard.getCurrentPlayer();
        currentPlayer.addGetOutOfJailCard();
    }

    @Override
    public void changeBal(int i) {
        gameBoard.getCurrentPlayer().addBalance(i);
    }

    //    You receive the "Matador grant" of DKK 40000, but only if your total values do not exceed DKK 15000.
    @Override
    public void changeBalConditional(int amount, int condition) {
        Player player = gameBoard.getCurrentPlayer();
        if (gameBoard.totalPlayerValue(gameBoard.getCurrentPlayer()) < condition) {
            userIO.showMessage(Message.giftToPoorPlayerCard(player.getName(), amount));
            player.addBalance(amount);
        } else {
            userIO.showMessage(Message.noGiftToRichPlayerCard(player.getName(), amount));
        }
    }

    @Override
    public void changeBalFromPlayers(int amount) {
        for (Player p : gameBoard.getPlayers()) {
            gameBoard.getCurrentPlayer().addBalance(amount);
            p.addBalance(-amount);
        }
    }

    @Override
    public void moveToField(int index) {
        gameBoard.getCurrentPlayer().setPosition(index);
        //TODO make player get extra turn
    }

    @Override
    public void moveSpaces(int spaces) {
        int playerPos = gameBoard.getCurrentPlayer().getPosition();
        if (playerPos + spaces > 0 && playerPos + spaces < gameBoard.getFields().length) {
            gameBoard.getCurrentPlayer().setPosition(playerPos + spaces);
        } else if (playerPos + spaces < 0) {
            gameBoard.getCurrentPlayer().setPosition(gameBoard.getFields().length + playerPos + spaces);
        } else {
            gameBoard.getCurrentPlayer().setPosition(gameBoard.getFields().length - playerPos + spaces);
        }
    }

    @Override
    public void moveToFerry() {
        Field[] fields = gameBoard.getFields();
        int pos = gameBoard.getCurrentPlayer().getPosition();
        while (!(fields[pos] instanceof Ferry)) {
            pos++;
            if (pos == 40) pos = 0;
        }
        gameBoard.getCurrentPlayer().setPosition(pos);
    }

    @Override
    public void payPerProperty(int perHouse, int perHotel) {

        int houses = gameBoard.getCurrentPlayer().getHouses();
        int hotels = gameBoard.getCurrentPlayer().getHotels();

        gameBoard.getCurrentPlayer().addBalance(-((houses * perHouse) + (hotels * perHotel)));
    }

    @Override
    public void goToJail() {
        gameBoard.getCurrentPlayer().setJailed(true);
        gameBoard.getCurrentPlayer().setPosition(10);
        //TODO check if it works
        // Could we make gameboard somehow recieve an int so we can control how big it is for testing?
    }

    @Override
    public void moveToBrewery() {
        Field[] fields = gameBoard.getFields();
        int pos = gameBoard.getCurrentPlayer().getPosition();
        while (!(fields[pos] instanceof Brewery)) {
            pos++;
            if (pos == 40) pos = 0;
        }
        gameBoard.getCurrentPlayer().setPosition(pos);
    }

    @Override
    public void printDescription(String desc) {
        String playerName = gameBoard.getCurrentPlayer().getName();
        userIO.showMessage(Message.chanceCard(playerName, desc));
    }
}