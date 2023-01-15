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
        String type = this.getTypeAst().accept(creator);
        if (type != null){
            String typeAlias = this.getTds().getTypeEntry(type).getSymbol();
            arrayEntry.setTypeComposite(typeAlias);
        }
        else{
            this.getTds().removeTypeEntry(arrayEntry);
        }
    }
}
