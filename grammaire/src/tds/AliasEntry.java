package tds;



public class AliasEntry extends TypeEntry{
    
    private String parent;

    public AliasEntry(String symbol, int size, String parent){
        super(symbol);
        this.parent = parent;
    }

    public AliasEntry(TypeEntry typeEntry){
        super(typeEntry);
        this.parent = null; // DU coup c mieux de prendre en compte l'alias dans le getSymbol()
    }

    public String getParent() {
        return parent;
    }

    @Override
    public boolean isAlias(){
        return true;
    }

    public void setParent(String parent){
        this.parent = parent;
    }

    public void print(){
        System.out.printf("| ALIAS  | Id : %-15s | Parent : %-15s |\n", this.getSymbol(), parent);
    }
}