package ast;

public class Fct2Declaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Idf typeID;
    public Ast exprAffect;
    public Fct2Declaration(Idf typeId, Ast exprAffect){
        this.typeID=typeId;
        this.exprAffect=exprAffect;
    }
}
