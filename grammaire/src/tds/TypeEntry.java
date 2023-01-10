package tds;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TypeEntry extends Entry{
    //Faudrait choisir une solution

    //private String alias = null;
    private ArrayList<String> alias = new ArrayList<String>();

    public TypeEntry(String symbol,int size){
        super(symbol, size);
    }

    public TypeEntry(String symbol,int size, ArrayList<String> previousAlias, String alias){
        super(symbol, size);
        if (!previousAlias.isEmpty())
            this.alias.addAll(previousAlias);
        this.alias.add(alias);
    }

    /*
    public TypeEntry(String symbol,int size, String alias){
        super(symbol, size);
        this.alias = alias;
    }
    */

    public ArrayList<String> getAlias(){
        return this.alias;
    }
}
