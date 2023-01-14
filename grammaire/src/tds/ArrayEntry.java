package tds;

public class ArrayEntry extends TypeEntry{
    private String typeComposite;

    /*    public ArrayEntry(String symbol, int size, String typeComposite){
        super(symbol, size);
        this.typeComposite = typeComposite;
    }

    public ArrayEntry(String symbol, int size, String typeComposite, ArrayEntry parentAlias){
        super(symbol, size, parentAlias);
        this.typeComposite = typeComposite;
    }
    */

    public ArrayEntry(TypeEntry typeEntry){
        super(typeEntry);
    }

    public String getTypeComposite() {
        return typeComposite;
    }

    @Override
    public boolean isArray(){
        return true;
    }

    public void print(){
        System.out.printf("Id : %-20s | Type %-20s\n", this.getSymbol(), typeComposite);
    }
}
