package ast;

public class Idf extends AbstractIdf{

    public <T> T accept(AstVisitor<T> visitor){
        return visitor.visit(this);
    }

    public String name;

    public Idf(int lineNumber, String name){
        super(lineNumber);
        this.name=name;
    }

    public boolean isAffectable(){
        return true;
    }
}
