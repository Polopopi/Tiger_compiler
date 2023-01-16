package tds;
import ast.Ast;

public class LaterVerifArray extends LaterVerifType{
    private ArrayEntry arrayEntry;

    public LaterVerifArray(ArrayEntry arrayEntry, Ast typeAst, Tds tds){
        super(typeAst, tds);
        this.arrayEntry = arrayEntry;
    }

    public ArrayEntry getTypeEntry(){
        return arrayEntry;
    }

    @Override
    public void check(TdsCreator creator){
        creator.setNameIdf(true);
        String type = this.getTypeAst().accept(creator);
        creator.setNameIdf(false);
        if (this.getTds().existType(type)){
            String typeAlias = this.getTds().getTypeEntry(type).getSymbol();
            arrayEntry.setTypeComposite(typeAlias);
        }
        else{
            System.out.println("Erreur ligne " + this.getTypeAst().lineNumber + " : le type " + type + "n'est pas défini pour l'array " + arrayEntry.getSymbol());
            this.getTds().removeTypeEntry(arrayEntry);
        }
    }
}
