package tds;

public abstract class LaterVerif{
    public abstract void check(TdsCreator creator);
    public boolean isAliasVerif(){
        return false;
    }
}

// type a := {x:b}
// type b := {x:a}