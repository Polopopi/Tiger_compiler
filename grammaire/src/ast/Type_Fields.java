package ast;
import java.util.ArrayList;

public class Type_Fields implements Ast {
    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public ArrayList<Ast> listAst;

    public Type_Fields(){
        this.listAst = new ArrayList<>();
    }

    public void addField(Ast Type_Fields){
        this.listAst.add(Type_Fields);
    }
}
