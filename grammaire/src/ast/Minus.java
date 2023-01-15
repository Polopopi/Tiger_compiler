package ast;

public class Minus extends Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;


    /* expr_mult - expr_mult */
    public Minus(int lineNumber, Ast left, Ast right){
        super(lineNumber);
        this.left = left;
        this.right = right;
    }
}
