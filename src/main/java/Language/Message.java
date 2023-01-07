package Language;


public class Message {
    LanguageController languageController = new LanguageController();

    public enum Type {
        CHANCE_CARD_MSG,
        DKK_PER,
        EXTRA_TURN,
        GAME_LOST_MSG,
        GAME_WON_MSG,
        LEAVE_JAIL_MSG,
        MOVE_TO,
        NO,
        NO_MORE_HOUSES,
        ON_GET_OUT_OF_JAIL,
        ON_PICK_FIELD_CHANCE,
        PARKING_MSG,
        PASSED_START_MSG,
        PLAYER_COUNT_MSG,
        PLAYER_NAME1,
        PLAYER_NAME2,
        PLAYER_NAME3,
        PLAYER_NAME4,
        PLAYER_TOTAL_VALUE,
        ROLL_DICE_MSG,
        SELECT_HOUSE,
        START_MSG,
        SUCCESSFUL_CONDITION_CHANCE_CARD,
        TAX_PROMPT,
        UN_SUCCESSFUL_CONDITION_CHANCE_CARD,
        WANT_TO_BAIL_OUT,
        WANT_TO_BUY_HOUSE,
        WANT_TO_BUY_PROMPT,
        YES,
    }
}