package tds;

import javax.sound.sampled.AudioFileFormat.Type;

public class AliasEntry extends TypeEntry{
    
    private String parent;

    public AliasEntry(String symbol, int size, String parent){
        super(symbol);
        this.parent = parent;
    }

    public AliasEntry(TypeEntry typeEntry){
        super(typeEntry);
        this.parent = typeEntry.getSymbol(); // DU coup c mieux de prendre en compte l'alias dans le getSymbol()
    }

    public String getParent() {
        return parent;
    }

    @Override
    public boolean isAlias(){
        return true;
    }

    public void print(){
        System.out.printf("Id : %-20s | Parent : %-20s\n", this.getSymbol(), parent);
    }
}