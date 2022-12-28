package ast;

public class IntExpr implements Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public int value;

    public IntExpr(int value){
        this.value = value;
    }
}
