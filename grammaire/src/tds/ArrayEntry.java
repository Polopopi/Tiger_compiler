package tds;

import java.util.ArrayList;

public class ArrayEntry extends TypeEntry{
    private String typeComposite;


    public ArrayEntry(String symbol, int size, String typeComposite){
        super(symbol, size);
        this.typeComposite = typeComposite;
    }

    public ArrayEntry(String symbol, int size, String typeComposite, ArrayList<String> previousAlias, String alias){
        super(symbol, size, previousAlias, alias);
        this.typeComposite = typeComposite;
    }

    public String getTypeComposite() {
        return typeComposite;
    }
}
