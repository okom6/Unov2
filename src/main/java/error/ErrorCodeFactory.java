package error;

public class ErrorCodeFactory {

    public static GoodMoveCode produceGoodMoveCode(){
        return new GoodMoveCode();
    }

    public static NoChoosenColourErrorCode produceNoChoosenColourErrorCode(){
        return new NoChoosenColourErrorCode();
    }

    public static WrongCardErrorCode produceWrongCardErrorCode(){
        return new WrongCardErrorCode();
    }
}
