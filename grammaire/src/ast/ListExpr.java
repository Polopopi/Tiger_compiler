package ast;
import java.util.ArrayList;

public class ListExpr implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public ListExpr(){
        this.listAst = new ArrayList<>();
    }

    public void addExpre(Ast Expr){
        this.listAst.add(Expr);
    }
}