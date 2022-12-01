package graphViz;

import java.io.FileOutputStream;
import java.io.IOException;

import ast.AstVisitor;

import ast.Program;
import ast.Affect;
import ast.Or;
import ast.And;
import ast.Equal;
import ast.Diff;
import ast.Inf;
import ast.Sup;
import ast.InfEqual;
import ast.SupEqual;
import ast.Plus;
import ast.Minus;
import ast.Mult;
import ast.Divide;

import ast.Ast;
import ast.Idf;
import ast.IfThen;
import ast.IfThenElse;
import ast.InstrList;
import ast.IntNode;
import ast.Print;


public class GraphVizVisitor implements AstVisitor<String> {

    private int state;
    private String nodeBuffer;
    private String linkBuffer;

    public GraphVizVisitor(){
        this.state = 0;
        this.nodeBuffer = "digraph \"ast\"{\n\n\tnodesep=1;\n\tranksep=1;\n\n";
        this.linkBuffer = "\n";
    }

    public void dumpGraph(String filepath) throws IOException{
            
        FileOutputStream output = new FileOutputStream(filepath);

        String buffer = this.nodeBuffer + this.linkBuffer + "}";
        byte[] strToBytes = buffer.getBytes();

        output.write(strToBytes);

        output.close();

    }


    private String nextState(){
        int returnedState = this.state;
        this.state++;
        return "N"+ returnedState;
    }

    private void addTransition(String from,String dest){
        this.linkBuffer += String.format("\t%s -> %s; \n", from,dest);

    }

    private void addNode(String node,String label){
        this.nodeBuffer += String.format("\t%s [label=\"%s\", shape=\"box\"];\n", node,label);

    }

    
    // Partie 1

    @Override
    public String visit(Program program) {

        String nodeIdentifier = this.nextState();

        String instructionsState = program.affect.accept(this);

        this.addNode(nodeIdentifier, "Program");
        this.addTransition(nodeIdentifier, instructionsState);

        return nodeIdentifier;

    }
    
    @Override
    public String visit(Affect affect) {

        String nodeIdentifier = this.nextState();

        String idfState = affect.idf.accept(this);
        String expressionState = affect.expr.accept(this);

        this.addNode(nodeIdentifier, ":=");
        this.addTransition(nodeIdentifier, idfState);
        this.addTransition(nodeIdentifier, expressionState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Or or){
        String nodeIdentifier = this.nextState();

        String leftState = or.left.accept(this);
        String rightState = or.right.accept(this);

        this.addNode(nodeIdentifier, "|");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(And and){
        String nodeIdentifier = this.nextState();

        String leftState = and.left.accept(this);
        String rightState = and.right.accept(this);

        this.addNode(nodeIdentifier, "&");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Equal equal){
        String nodeIdentifier = this.nextState();

        String leftState = equal.left.accept(this);
        String rightState = equal.right.accept(this);

        this.addNode(nodeIdentifier, "=");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Diff diff){
        String nodeIdentifier = this.nextState();

        String leftState = diff.left.accept(this);
        String rightState = diff.right.accept(this);

        this.addNode(nodeIdentifier, "<>");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Inf inf){
        String nodeIdentifier = this.nextState();

        String leftState = inf.left.accept(this);
        String rightState = inf.right.accept(this);

        this.addNode(nodeIdentifier, "<");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(Sup sup){
        String nodeIdentifier = this.nextState();

        String leftState = sup.left.accept(this);
        String rightState = sup.right.accept(this);

        this.addNode(nodeIdentifier, ">");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(InfEqual infEqual){
        String nodeIdentifier = this.nextState();

        String leftState = infEqual.left.accept(this);
        String rightState = infEqual.right.accept(this);

        this.addNode(nodeIdentifier, "<=");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }

    @Override
    public String visit(SupEqual supEqual){
        String nodeIdentifier = this.nextState();

        String leftState = supEqual.left.accept(this);
        String rightState = supEqual.right.accept(this);

        this.addNode(nodeIdentifier, ">=");
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }


    @Override
    public String visit(Plus plus) {

        String nodeIdentifier = this.nextState();

        String leftState = plus.left.accept(this);
        String rightState = plus.right.accept(this);

        this.addNode(nodeIdentifier, "+");
        
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;

    }

    @Override
    public String visit(Minus minus) {

        String nodeIdentifier = this.nextState();

        String leftState = minus.left.accept(this);
        String rightState = minus.right.accept(this);

        this.addNode(nodeIdentifier, "-");
        
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;

    }

    @Override
    public String visit(Mult mult) {

        String nodeIdentifier = this.nextState();

        String leftState = mult.left.accept(this);
        String rightState = mult.right.accept(this);

        this.addNode(nodeIdentifier, "*");
        
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;

    }

    @Override
    public String visit(Divide divide) {

        String nodeIdentifier = this.nextState();

        String leftState = divide.left.accept(this);
        String rightState = divide.right.accept(this);

        this.addNode(nodeIdentifier, "/");
        
        this.addTransition(nodeIdentifier, leftState);
        this.addTransition(nodeIdentifier, rightState);

        return nodeIdentifier;
    }



    ////////////////////////////////////////////////////////////



    @Override
    public String visit(Idf idf) {

        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, idf.name);

        return nodeIdentifier;

    }

    @Override
    public String visit(IfThen ifThen) {

        String nodeIdentifier = this.nextState();

        String conditionState = ifThen.condition.accept(this);
        String thenBlockState = ifThen.thenBlock.accept(this);

        this.addNode(nodeIdentifier, "IfThen");

        this.addTransition(nodeIdentifier, conditionState);
        this.addTransition(nodeIdentifier, thenBlockState);

        return nodeIdentifier;
        
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        
        String nodeIdentifier = this.nextState();

        String conditionState = ifThenElse.condition.accept(this);
        String thenBlockState = ifThenElse.thenBlock.accept(this);
        String elseBlockState = ifThenElse.elseBlock.accept(this);
        
        this.addNode(nodeIdentifier, "IfThenElse");

        this.addTransition(nodeIdentifier, conditionState);
        this.addTransition(nodeIdentifier, thenBlockState);
        this.addTransition(nodeIdentifier, elseBlockState);

        return nodeIdentifier;

    }

    @Override
    public String visit(InstrList instrList) {
        
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "InstrList");

        for (Ast ast:instrList.instrList){

            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);

        }

        return nodeIdentifier;

    }

    @Override
    public String visit(IntNode intNode) {

        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, String.valueOf(intNode.value));

        return nodeIdentifier;

    }

    @Override
    public String visit(Print print) {

        String nodeIdentifier = this.nextState();

        String valueState = print.value.accept(this);

        this.addNode(nodeIdentifier, "print");
        this.addTransition(nodeIdentifier, valueState);

        return nodeIdentifier;


    }    
}
