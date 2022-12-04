package ast;
import java.util.ArrayList;

public class SeqExpr implements Ast{
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public SeqExpr(){
        this.listAst = new ArrayList<>();
    }

    public void addExpre(Ast Expr){
        this.listAst.add(Expr);
    }
}
