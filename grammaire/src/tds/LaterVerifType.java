package tds;

public class LaterVerifType implements LaterVerif{
    private String type;
    private Tds tds;

    public LaterVerifType(String type, Tds tds){
        this.type = type;
        this.tds = tds;
    }

    public void check(TdsCreator creator){
        tds.existType(type);
    }
    
}