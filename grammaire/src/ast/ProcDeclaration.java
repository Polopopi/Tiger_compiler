package ast;

public class ProcDeclaration extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast fonctionID;
    public Ast typeFields;
    public Ast exprAffect;

    public ProcDeclaration(int lineNumber, Ast fonctionID, Ast typeFields, Ast exprAffect){
        super(lineNumber);
        this.fonctionID=fonctionID;
        this.typeFields = typeFields;
        this.exprAffect=exprAffect;
    }
    
}
