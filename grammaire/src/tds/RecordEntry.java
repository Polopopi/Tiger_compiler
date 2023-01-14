package tds;

import java.util.ArrayList;

public class RecordEntry extends TypeEntry{
    private ArrayList<Field> fields;


    public RecordEntry(String symbol, int size){
        super(symbol);
    }

    public RecordEntry(TypeEntry typeEntry){
        super(typeEntry);
    }

    public void addField(Field field){
        this.fields.add(field);
    }

}
