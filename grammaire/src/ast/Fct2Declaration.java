package ast;

public class Fct2Declaration implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Idf typeID;
    public Ast expr_affect;
    public Fct2Declaration(Idf typeId, Ast expr_affect){
        this.typeID=typeId;
        this.expr_affect=expr_affect;
    }
}
