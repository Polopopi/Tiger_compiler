package tds;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class TypeEntry extends Entry{
    public TypeEntry(String symbol){
        super(symbol);
    }

    public TypeEntry(TypeEntry typeEntry){
        super(typeEntry.getSymbol());
    }

    public boolean isRecord(){
        return false;
    }

    public boolean isArray(){
        return false;
    }

    public boolean isAlias(){
        return false;
    }
}


