package ast;
import java.util.ArrayList;

public class Declaration_list implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public Declaration_list(){
        this.listAst = new ArrayList<>();
    }

    public void addDecla(Ast Declaration){
        this.listAst.add(Declaration);
    }
}