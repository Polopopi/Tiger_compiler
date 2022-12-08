package graphViz;

import java.io.FileOutputStream;
import java.io.IOException;


import ast.*;

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

    //////partie 2

    @Override
    public String visit(MinusExpr minusExpr) {
        String nodeIdentifier = this.nextState();
        
        String next = minusExpr.expr.accept(this);
        this.addNode(nodeIdentifier, "-");

        this.addTransition(nodeIdentifier, next);
        return(nodeIdentifier);
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
    public String visit(Let let){
        String nodeIdentifier = this.nextState();

        String declarationList = let.declarationList.accept(this);
        String seqExpr = let.seqExpr.accept(this);
        
        this.addNode(nodeIdentifier, "let");
        this.addTransition(nodeIdentifier, declarationList);
        this.addTransition(nodeIdentifier, seqExpr);

        return nodeIdentifier;

    } 

    @Override
    public String visit(For forNode){
        String nodeIdentifier = this.nextState();

        String id = forNode.id.accept(this);
        String debut = forNode.debut.accept(this);
        String fin = forNode.fin.accept(this);
        String bloc = forNode.bloc.accept(this);

        this.addNode(nodeIdentifier, "for");
        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, debut);
        this.addTransition(nodeIdentifier, fin);
        this.addTransition(nodeIdentifier, bloc);

        return(nodeIdentifier);
    }

    @Override
    public String visit(While whileNode){
        String nodeIdentifier = this.nextState();

        String condition = whileNode.condition.accept(this);
        String bloc = whileNode.bloc.accept(this);

        this.addNode(nodeIdentifier, "while");
        this.addTransition(nodeIdentifier, condition);
        this.addTransition(nodeIdentifier, bloc);

        return nodeIdentifier;
    }
/* 
    @Override
    public String visit(LvalueExpr lvalueExpr){
        String nodeIdentifier = this.nextState();

        String lvalue = lvalueExpr.lvalue.accept(this);
        String suite = lvalueExpr.suite.accept(this);
        
        this.addNode(nodeIdentifier,"lvalueExpr");
        this.addTransition(nodeIdentifier, lvalue);
        this.addTransition(nodeIdentifier, suite);

        return nodeIdentifier;
    }
*/
/*
    @Override
    public String visit(LvalueExprTypeID lvalueExpr){
        String nodeIdentifier = this.nextState();

        String lvalue = lvalueExpr.lvalue.accept(this);
        String suite = lvalueExpr.suite.accept(this);
        
        this.addNode(nodeIdentifier,"lvalueExprTypeID");
        this.addTransition(nodeIdentifier, lvalue);
        this.addTransition(nodeIdentifier, suite);

        return nodeIdentifier;
    }
*/
    @Override
    public String visit(BreakExpr breakExpr){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "break");

        return nodeIdentifier;
    }

    @Override
    public String visit(NilExpr nil){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "nil");

        return(nodeIdentifier);
    }

    @Override
    public String visit(SeqExpr seqExpr){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "seqExpr");
        for (Ast ast:seqExpr.listExpr){

            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);

        }

        return(nodeIdentifier);
    }

    @Override
    public String visit(ListExpr listExpr){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "listExpr");
        for (Ast ast:listExpr.listExpr){

            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);

        }

        return(nodeIdentifier);
    }

    @Override
    public String visit(DeclarationList declarationList){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, "listDeclaration");
        for (Ast ast:declarationList.listAst){

            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);

        }

        return(nodeIdentifier);
    }

    @Override
    public String visit(IntExpr intExpr) {

        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, String.valueOf(intExpr.value));

        return nodeIdentifier;

    }

    @Override
    public String visit(StrExpr strExpr){
        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, strExpr.value);

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

    // PARTIE 3 :

    public String visit(Type_Declaration Type_Dec){
        String nodeIdentifier=this.nextState();
        
        String type_id=Type_Dec.type_id.accept(this);
        String type=Type_Dec.type.accept(this);

        this.addNode(nodeIdentifier, "type");
        
        this.addTransition(nodeIdentifier, type_id);
        this.addTransition(nodeIdentifier, type);
        return nodeIdentifier;
    }

    public String visit(Type_Field Type_Field){
        String nodeIdentifier=this.nextState();
        
        String type_id=Type_Field.type_id.accept(this);
        String id=Type_Field.id.accept(this);

        this.addNode(nodeIdentifier, "Type champs");
        
        this.addTransition(nodeIdentifier, type_id);
        this.addTransition(nodeIdentifier, id);
        return nodeIdentifier;
    }
        
    public String visit(Type_Fields Type_Flds){
        String nodeIdentifier=this.nextState();

        this.addNode(nodeIdentifier, "Liste de Type champs");
        
        for (Ast ast:Type_Flds.listAst){
            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
    
        }
        return nodeIdentifier;
    }

    public String visit(TypeType typeType){
        String nodeIdentifier=this.nextState();

        String id = typeType.typeCopie.accept(this);

        this.addNode(nodeIdentifier, "typeID");
        this.addTransition(nodeIdentifier, id);
        

        return nodeIdentifier;
    }

    public String visit(TypeArray typeArray){
        String nodeIdentifier=this.nextState();

        String array = typeArray.typeArray.accept(this);

        this.addNode(nodeIdentifier, "array of");
        this.addTransition(nodeIdentifier, array);
        

        return nodeIdentifier;
    }

    public String visit(TypeRecord typeRecord){
        String nodeIdentifier=this.nextState();

        String record = typeRecord.typeRecord.accept(this);

        this.addNode(nodeIdentifier, "{ }");
        this.addTransition(nodeIdentifier, record);
        

        return nodeIdentifier;
    }

    public String visit(TypeRecordVoid typeRecord){
        String nodeIdentifier=this.nextState();

        this.addNode(nodeIdentifier, "{ }");
        
        return nodeIdentifier;
    }

    //////partie 4
    public String visit(VarDeclaration varDeclaration){
        String nodeIdentifier=this.nextState();
        
        String id=varDeclaration.idf.accept(this);
        String expr=varDeclaration.expr.accept(this);
        this.addNode(nodeIdentifier, "var");
        
        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, expr);
        return nodeIdentifier;

    }

    public String visit(VarDeclarationType varDeclaration){
        String nodeIdentifier=this.nextState();
        
        String id=varDeclaration.idf.accept(this);
        String type=varDeclaration.type.accept(this);
        String expr=varDeclaration.expr.accept(this);
        this.addNode(nodeIdentifier, "var");
        
        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, type);
        this.addTransition(nodeIdentifier, expr);
        return nodeIdentifier;

    }
    public String visit(FctDeclaration fctDeclaration){
        String nodeIdentifier=this.nextState();
        
        String id=fctDeclaration.fonctionID.accept(this);
        String typeField=fctDeclaration.typeField.accept(this);
        String fct2declaration=fctDeclaration.fct2Declaration.accept(this);

        this.addNode(nodeIdentifier,"Function");

        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, typeField);
        this.addTransition(nodeIdentifier, fct2declaration);
        return nodeIdentifier;
    }
    public String visit(ProcDeclaration procDeclaration){
        String nodeIdentifier=this.nextState();
        
        String id=procDeclaration.fonctionID.accept(this);
        String fct2declaration=procDeclaration.fct2Declaration.accept(this);

        this.addNode(nodeIdentifier,"Function");

        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, fct2declaration);
        return nodeIdentifier;
    }
    public String visit(Fct2Declaration fct2Declaration){
        String nodeIdentifier=this.nextState();
        
        String exprAffect=fct2Declaration.exprAffect.accept(this);

        this.addNode(nodeIdentifier, "bloc");

        this.addTransition(nodeIdentifier, exprAffect);
        return nodeIdentifier;
    }
    public String visit(Fct2DeclarationType fct2Declaration){
        String nodeIdentifier=this.nextState();
        
        String type=fct2Declaration.typeID.accept(this);
        String exprAffect=fct2Declaration.exprAffect.accept(this);

        this.addNode(nodeIdentifier, "bloc");

        this.addTransition(nodeIdentifier, type);
        this.addTransition(nodeIdentifier, exprAffect);
        return nodeIdentifier;
    }
    public String visit(LvalueIndex lvalue){
        String nodeIdentifier=this.nextState();
        String left=lvalue.left.accept(this);
        String exprOr=lvalue.exprOr.accept(this);

        this.addNode(nodeIdentifier, "[]");
        
        this.addTransition(nodeIdentifier, exprOr);
        this.addTransition(nodeIdentifier, left);
        return nodeIdentifier;
    }
    public String visit(LvalueField lvalue){
        String nodeIdentifier=this.nextState();
        String id = lvalue.id.accept(this);
        String left=lvalue.left.accept(this);

        this.addNode(nodeIdentifier, ".");
        this.addTransition(nodeIdentifier, left);
        this.addTransition(nodeIdentifier, id);
        return nodeIdentifier;
    }

    public String visit(Call call){
        String nodeIdentifier=this.nextState();
        String id = call.id.accept(this);
        String listExpr=call.listExpr.accept(this);

        this.addNode(nodeIdentifier, "Call");
        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, listExpr);

        return(nodeIdentifier);
    }

    public String visit(Array array){
        String nodeIdentifier=this.nextState();
        String id = array.id.accept(this);
        String exprOr1=array.exprOr1.accept(this);
        String exprOr2=array.exprOr2.accept(this);
        
        this.addNode(nodeIdentifier, "Array");
        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, exprOr1);
        this.addTransition(nodeIdentifier, exprOr2);

        return nodeIdentifier;
    }
    public String visit(LvalueRecord record){
        String nodeIdentifier=this.nextState();

        String id=record.id.accept(this);
        String fieldList=record.fieldList.accept(this);

        this.addNode(nodeIdentifier, "Record");

        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, fieldList);

        return nodeIdentifier;
    }

    public String visit(Field field){
        String nodeIdentifier=this.nextState();

        String id=field.id.accept(this);
        String expr=field.expr.accept(this);

        this.addNode(nodeIdentifier, "=");

        this.addTransition(nodeIdentifier, id);
        this.addTransition(nodeIdentifier, expr);

        return nodeIdentifier;
    }

    public String visit(FieldList fieldList){
        String nodeIdentifier=this.nextState();

        this.addNode(nodeIdentifier, "FieldList");

        for (Ast ast : fieldList.listAst){
            String field = ast.accept(this);
            this.addTransition(nodeIdentifier, field);

        }

        return nodeIdentifier;
    }
    /*public String visit(RecordList recordList){
        String nodeIdentifier=this.nextState();

        this.addNode(nodeIdentifier, "RecordList");

        for (Ast ast:recordList.recordList){

            String astState = ast.accept(this);
            this.addTransition(nodeIdentifier, astState);
        }
        return nodeIdentifier;

    }*/


    ////////////////////////////////////////////////////////////



    @Override
    public String visit(Idf idf) {

        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, idf.name);

        return nodeIdentifier;

    }
/*
    @Override
    public String visit(IdfType idf) {

        String nodeIdentifier = this.nextState();

        this.addNode(nodeIdentifier, idf.name);

        return nodeIdentifier;

    }*/
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
}

    
