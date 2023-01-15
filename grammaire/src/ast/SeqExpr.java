package ast;
import java.util.ArrayList;

public class SeqExpr extends Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listExpr;

    public SeqExpr(int lineNumber){
        super(lineNumber);
        this.listExpr = new ArrayList<>();
    }

    public void addExpre(Ast Expr){
        this.listExpr.add(Expr);
    }
}
