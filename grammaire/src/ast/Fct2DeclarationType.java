package ast;

public class Fct2DeclarationType extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast typeID;
    public Ast exprAffect;

    public Fct2DeclarationType(Ast typeId, Ast exprAffect){
        this.typeID=typeId;
        this.exprAffect=exprAffect;
    }
    
}
