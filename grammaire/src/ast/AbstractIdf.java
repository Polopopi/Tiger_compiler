package ast;

public abstract class AbstractIdf extends Ast {
    public AbstractIdf(int lineNumber){
        super(lineNumber);
    }

    @Override
    public boolean isAffectable(){
        return(true);
    }
}
