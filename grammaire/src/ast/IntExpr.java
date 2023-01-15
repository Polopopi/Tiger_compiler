package ast;

public class IntExpr extends Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public int value;

    public IntExpr(int lineNumber, int value){
        super(lineNumber);
        this.value = value;
    }
}
