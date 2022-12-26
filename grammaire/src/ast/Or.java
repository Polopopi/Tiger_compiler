package ast;

public class Or extends Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast left;
    public Ast right;

    /* expr_test & expr_test */
    public Or(Ast left, Ast right){
        this.left = left;
        this.right = right;
    }
}
