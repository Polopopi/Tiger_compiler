package tds;
import ast.Ast;

public abstract class LaterVerifType extends LaterVerif{
    private Ast typeAst;
    private Tds tds;

    public LaterVerifType(Ast typeAst, Tds tds){
        this.typeAst = typeAst;
        this.tds = tds;
    }

    public abstract void check(TdsCreator creator);
    public abstract TypeEntry getTypeEntry();

    public Tds getTds(){
        return this.tds;
    }

    public Ast getTypeAst(){
        return this.typeAst;
    }
    
}