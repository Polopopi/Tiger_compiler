package ast;

public class While extends Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public Ast condition;
    public Ast bloc;

    public While(int lineNumber, Ast condition, Ast bloc){
        super(lineNumber);
        this.condition = condition;
        this.bloc = bloc;
    }
}
