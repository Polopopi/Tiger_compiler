package ast;

public class ProcDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast fonctionID;
    public Ast typeFields;
    public Ast exprAffect;

    public ProcDeclaration(Ast fonctionID, Ast typeFields, Ast exprAffect){
        this.fonctionID=fonctionID;
        this.typeFields = typeFields;
        this.exprAffect=exprAffect;
    }
    
}
