package Enities;

import Enities.ChanceCards.ChanceAction;

import Enities.Fields.Brewery;
import Enities.Fields.Ferry;
import Enities.Fields.Field;
import Language.LanguageHandler;
import View.View;

import java.io.IOException;

public class ActualChanceCard implements ChanceAction {
    GameBoard gameBoard;
    View view;
    LanguageHandler language;
    public ActualChanceCard(GameBoard gameBoard,View view){
        this.gameBoard = gameBoard;
        this.view = view;
        try {
            this.language = new LanguageHandler();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getOutOfJail() {
            Player currentPlayer = gameBoard.getCurrentPlayer();
            currentPlayer.addGetOutOfJailCard();
    }

    @Override
    public void changeBal(int i) {
        gameBoard.getCurrentPlayer().addBalance(i);
        //TODO i is always positive, maybe split the chance cards in pos / neg
    }

    @Override
    public void changeBalConditional(int amount, int condition) {
        if(gameBoard.getCurrentPlayer().totalValue() <condition){
            view.showMessage(gameBoard.getCurrentPlayer().getName()+language.languageMap.get("succesfulConditionChanceCard")+amount+" DKK");
        }
        else{
            view.showMessage(gameBoard.getCurrentPlayer().getName()+language.languageMap.get("unSuccesfulConditionChanceCard"));
        }
    }

    @Override
    public void changeBalFromPlayers(int amount) {
        for (Player p : gameBoard.getPlayers()){
            gameBoard.getCurrentPlayer().addBalance(amount);
            p.addBalance(-amount);
        }
    }

    @Override
    public void moveToField(int index) {
        gameBoard.getCurrentPlayer().setPosition(index);
        //TODO make sure they also get a turn
    }

    @Override
    public void moveSpaces(int spaces) {
        int playerPos =gameBoard.getCurrentPlayer().getPosition();
        if(playerPos+spaces>0&&playerPos+spaces<gameBoard.getFields().length){
            gameBoard.getCurrentPlayer().setPosition(playerPos+spaces);
        }
       else if(playerPos+spaces<0){
            gameBoard.getCurrentPlayer().setPosition(gameBoard.getFields().length+playerPos+spaces);
        }
       else{
            gameBoard.getCurrentPlayer().setPosition(gameBoard.getFields().length-playerPos+spaces);
        }
    }

    @Override
    public void moveToFerry() {
        Field[] fields = gameBoard.getFields();
        int pos = gameBoard.getCurrentPlayer().getPosition();
       while(!(fields[pos] instanceof Ferry)){
           pos++;
           if(pos==40) pos=0;
       }
       gameBoard.getCurrentPlayer().setPosition(pos);
    }

    @Override
    public void payPerProperty(int perHouse, int perHotel) {

        int houses = gameBoard.getCurrentPlayer().getHouses();
        int hotels = gameBoard.getCurrentPlayer().getHotels();

        gameBoard.getCurrentPlayer().addBalance(-((houses*perHouse)+(hotels*perHotel)));
    }

    @Override
    public void goToJail() {
        gameBoard.getCurrentPlayer().setJailed(true);
        gameBoard.getCurrentPlayer().setPosition(10);
        //TODO check if it works
    }

    @Override
    public void moveToBrewery() {
        Field[] fields = gameBoard.getFields();
        int pos = gameBoard.getCurrentPlayer().getPosition();
        while(!(fields[pos] instanceof Brewery)){
            pos++;
            if(pos==40) pos=0;
        }
        gameBoard.getCurrentPlayer().setPosition(pos);
    }

    @Override
    public void printDesc(String desc) {
        view.showMessage(desc);
    }

}
