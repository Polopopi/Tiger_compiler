package tds;

import javax.sound.sampled.AudioFileFormat.Type;

public class TypeAliasEntry extends TypeEntry{
    
    private String parent;

    public TypeAliasEntry(String symbol, int size, String parent){
        super(symbol);
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    @Override
    public String getSymbol(){
        return parent;
    }
}