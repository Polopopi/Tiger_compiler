package ast;

public class FctDeclaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }
/* 'function' id '(' type_fields? ')' (':' type_id)? '=' expr_affect */
    public Ast fonctionID;
    public Ast typeFields;
    public Ast typeId;
    public Ast exprAffect;

    public FctDeclaration(Ast fonctionID, Ast typeFields, Ast typeId, Ast exprAffect){
        this.fonctionID=fonctionID;
        this.typeFields=typeFields;
        this.typeId = typeId;
        this.exprAffect=exprAffect;   
    }
}
