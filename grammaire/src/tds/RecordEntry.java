package tds;

import java.util.ArrayList;

public class RecordEntry extends TypeEntry{
    private ArrayList<Field> fields;


    public RecordEntry(String symbol, int size){
        super(symbol, size);
    }

    public RecordEntry(String symbol, int size, RecordEntry parentAlias){
        super(symbol, size, parentAlias);
    }

    public void addField(Field field){
        this.fields.add(field);
    }

}
