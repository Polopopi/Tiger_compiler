package tds;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TypeEntry extends Entry{
    TypeEntry parentAlias = null;

    public TypeEntry(String symbol,int size){
        super(symbol, size);
    }

    public TypeEntry(String symbol,int size, TypeEntry parentAlias){
        super(symbol, size);
        this.parentAlias = parentAlias;
    }

    public TypeEntry getParentAlias(){
        return parentAlias;
    }
}
