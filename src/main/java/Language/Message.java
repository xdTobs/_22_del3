package Language;

public class Message {
    LanguageController languageController = new LanguageController();


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
