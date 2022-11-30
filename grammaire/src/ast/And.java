package ast;

public class And implements Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;

    /* expr_test & expr_test */
    public And(Ast left, Ast right){
        this.left = left;
        this.right = right;
    }

}
