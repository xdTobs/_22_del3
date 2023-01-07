package Language;

import Enities.Fields.RentableField;

public class Message {
    LanguageController languageController = new LanguageController();

    public static String wantToBuyField(RentableField field) {
        return languageController.getMessage("wantToBuyPrompt1") + " " + field.getName() + languageController.getMessage("wantToBuyPrompt2");
    }

    public String[] yesOrNo() {
        return new String[]{languageController.getMessage("yes"), languageController.getMessage("no")};
    }


    public enum Type {
        CHANCECARDMSG,
        DKKPER,
        EXTRATURN,
        GAMELOSTMSG,
        GAMEWONMSG,
        LEAVEJAILMSG,
        MOVETO,
        NO,
        NOMOREHOUSES,
        ONGETOUTOFJAIL,
        ONPICKFIELDCHANCE,
        PARKINGMSG,
        PASSEDSTARTMSG,
        PLAYERCOUNTMSG,
        PLAYERNAME1,
        PLAYERNAME2,
        PLAYERNAME3,
        PLAYERNAME4,
        PLAYERTOTALVALUE,
        ROLLDICEMSG,
        SELECTHOUSE,
        STARTMSG,
        SUCCESFULCONDITIONCHANCECARD,
        TAXPROMPT,
        UNSUCCESFULCONDITIONCHANCECARD,
        WANTTOBAILOUT,
        WANTTOBUYHOUSE,
        WANTTOBUYPROMPT1,
        WANTTOBUYPROMPT2,
        YES
    }

}
