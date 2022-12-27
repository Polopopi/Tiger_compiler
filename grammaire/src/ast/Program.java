package ast;

public class Program extends Ast {

    // Utile pour la dernière partie
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast affect;

    public Program(Ast affect) {
        this.affect = affect;
    }
}
