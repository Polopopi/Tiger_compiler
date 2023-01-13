package tds;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.Collection;

public abstract class TypeEntry extends Entry{
    public TypeEntry(String symbol,int size){
        super(symbol, size);
    }
}
