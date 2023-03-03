package assembleur;

import ast.*;
import tds.Tds;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class AsrCreator implements AstVisitor<String> {
    private Asr asr;
    private int labelId;
    private ArrayList<Tds> listTds;
    private Tds currentTds;
    private int oldTdsId;
    private int idGenerator;

    private Tds getTds(){
        return(listTds.get(oldTdsId++));
    }

    

    private int generateId(){
        return(idGenerator++);
    }

    private String generateLabel(){
        return("label"+generateId());
    }

    public AsrCreator(){
        this.asr=new Asr();
        labelId = 0;
    }
    public void asrFichier(String asrFileName) throws IOException {
        ArrayList<String> data=asr.getAsr();
        Path fichier= Paths.get(asrFileName);
        Files.write(fichier,data, StandardCharsets.UTF_8);
    }
    @Override
    public String visit(Idf affect) {
        return null;
    }

    @Override
    public String visit(Print affect) {
        return null;
    }

    @Override
    public String visit(Program program) {
        asr.jump("end_functions");

        try {
            Path path = Path.of("./src/assembleur/fonctions.S");
            String div_str = Files.readString(path);
            asr.addFunction(div_str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        asr.label("end_functions");

        String texte = program.affect.accept(this);

        asr.end();
        return null;
    }

    @Override
    public String visit(Affect affect) {
        return null;
    }

    @Override
    public String visit(Or or) {
        String left = or.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }

        asr.cmp("R1", "#0");
        asr.setCond("NE");
        asr.setVar(1);
        int labelIdCopie = labelId;
        labelId++;
        asr.jump("end_or_" + labelIdCopie);
        asr.resetCond();

        asr.incrementerSp(1);
        asr.stockerValeurSP();

        //asr.empilerFlags();
        String right = or.right.accept(this);
        //asr.depilerFlags();

        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);
        
        asr.or("R1", "R2", "R1");

        asr.label("end_or_" + labelIdCopie);

        return null;
    }

    @Override
    public String visit(And and) {
        String left = and.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }

        asr.cmp("R1", "#0");
        asr.setCond("EQ");
        asr.setVar(1);
        int labelIdCopie = labelId;
        labelId++;
        asr.jump("end_and_" + labelIdCopie);
        asr.resetCond();

        asr.incrementerSp(1);
        asr.stockerValeurSP();

        //asr.empilerFlags();
        String right = and.right.accept(this);
        //asr.depilerFlags();

        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);
        
        asr.or("R1", "R2", "R1");

        asr.label("end_and_" + labelIdCopie);
        return null;
    }

    @Override
    public String visit(Equal equal) {
        String left = equal.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = equal.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);

        asr.cmp("R2", "R1");
        asr.setCond("EQ");
        asr.setVar(1);
        asr.setCond("NE");
        asr.setVar(0);
        asr.resetCond();

        return null;
    }

    @Override
    public String visit(Diff diff) {
        String left = diff.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = diff.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);

        asr.cmp("R2", "R1");
        asr.setCond("NE");
        asr.setVar(1);
        asr.setCond("EQ");
        asr.setVar(0);
        asr.resetCond();

        return null;
    }

    @Override
    public String visit(Inf inf) {
        String left = inf.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = inf.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);

        asr.cmp("R2", "R1");
        asr.setCond("LT");
        asr.setVar(1);
        asr.setCond("GE");
        asr.setVar(0);
        asr.resetCond();

        return null;
    }

    @Override
    public String visit(Sup sup) {
        String left = sup.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = sup.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);

        asr.cmp("R2", "R1");
        asr.setCond("GT");
        asr.setVar(1);
        asr.setCond("LE");
        asr.setVar(0);
        asr.resetCond();

        return null;
    }

    @Override
    public String visit(InfEqual infEqual) {
        String left = infEqual.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = infEqual.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);

        asr.cmp("R2", "R1");
        asr.setCond("LE");
        asr.setVar(1);
        asr.setCond("GT");
        asr.setVar(0);
        asr.resetCond();

        return null;
    }

    @Override
    public String visit(SupEqual supEqual) {
        String left = supEqual.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = supEqual.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);

        asr.cmp("R2", "R1");
        asr.setCond("GE");
        asr.setVar(1);
        asr.setCond("LT");
        asr.setVar(0);
        asr.resetCond();

        return null;
    }

    @Override
    public String visit(Plus plus) {
        String left= plus.left.accept(this);
        String right=plus.right.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();
        if (right!=null){
            int rightValue = Integer.parseInt(right);
            asr.setVar(rightValue);
        }
        asr.lireVarSP();
        asr.plus("R1","R2","R1");
        asr.decrementerSp(1);

        return null;
    }

    @Override
    public String visit(Minus minus) {// RES DANS R11
        String left= minus.left.accept(this);
        if (left != null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right=minus.right.accept(this);
        if (right != null){
            int rightValue = Integer.parseInt(right);
            asr.setVar(rightValue);
        }
        asr.lireVarSP();
        asr.moins("R1","R2","R1");
        asr.decrementerSp(1);//R2 est le registre où on enregistre la valeur lue depuis la pile, R1 est le registre
                                  // où on peut donner une valeur à un stack

        return null;
    }

    @Override
    public String visit(Mult mult) {
        String left = mult.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.empiler ("R11");

        String right = mult.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.depiler("R0");
        
        asr.link("mult");

        return null;
    }

    @Override
    public String visit(Divide divide) {
        String left = divide.left.accept(this);
        if (left!=null){
            asr.setVar(Integer.parseInt(left));
        }
        asr.incrementerSp(1);
        asr.stockerValeurSP();

        String right = divide.right.accept(this);
        if (right!=null){
            asr.setVar(Integer.parseInt(right));
        }

        asr.lireVarSP();
        asr.decrementerSp(1);
        
        asr.link("div");

        return null;
    }


    @Override
    public String visit(MinusExpr minusExpr) {
        return "-" + String.valueOf(minusExpr.expr.accept(this));
    }

    @Override
    public String visit(IfThen ifThen) {
        String thenFlag = generateLabel();
        String elseFlag = generateLabel();

        ifThen.condition.accept(this);
        asr.cmp("r1","#0");
        asr.b("NE", thenFlag);
        asr.b(elseFlag);
        asr.label(thenFlag);
        ifThen.thenBlock.accept(this);
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        String thenFlag = generateLabel();
        String elseFlag = generateLabel();

        ifThenElse.condition.accept(this);
        asr.cmp("r1","#0");
        asr.b("NE", thenFlag);
        asr.b(elseFlag);
        asr.label(thenFlag);
        ifThenElse.thenBlock.accept(this);
        return null;
    }

    @Override
    public String visit(Let affect) {
        return null;
    }

    @Override
    public String visit(For affect) {
        String forFlag = generateLabel();
        String forEndFlag = generateLabel();

        asr.label(forFlag);
        asr.empilerValeurs("R11");


        return null;
    }

    @Override
    public String visit(While whileNode) {
        String whileFlag = generateLabel();
        String whileEndFlag = generateLabel();

        asr.label(whileFlag);
        whileNode.condition.accept(this);
        asr.cmp("r1", "#0");
        asr.b("EQ",whileEndFlag);

        whileNode.bloc.accept(this);

        asr.b(whileFlag);
        asr.label(whileEndFlag);

        return null;
    }

    @Override
    public String visit(BreakExpr affect) {
        return null;
    }

    @Override
    public String visit(NilExpr affect) {
        return null;
    }

    @Override
    public String visit(IntExpr intExpr) {
        return String.valueOf(intExpr.value);
    }

    @Override
    public String visit(StrExpr affect) {

        return null;
    }

    @Override
    public String visit(SeqExpr seqExpr) {
        for (Ast expr:seqExpr.listExpr){
            expr.accept(this);
        }

        return null;
    }

    @Override
    public String visit(DeclarationList affect) {
        return null;
    }

    @Override
    public String visit(ListExpr affect) {
        return null;
    }

    @Override
    public String visit(Type_Declaration affect) {
        return null;
    }

    @Override
    public String visit(Type_Fields affect) {
        return null;
    }

    @Override
    public String visit(Type_Field affect) {
        return null;
    }

    @Override
    public String visit(TypeType affect) {
        return null;
    }

    @Override
    public String visit(TypeRecord affect) {
        return null;
    }

    @Override
    public String visit(TypeRecordVoid affect) {
        return null;
    }

    @Override
    public String visit(TypeArray affect) {
        return null;
    }

    @Override
    public String visit(Field affect) {
        return null;
    }

    @Override
    public String visit(FieldList affect) {
        return null;
    }

    @Override
    public String visit(VarDeclaration affect) {
        return null;
    }

    @Override
    public String visit(FctDeclaration affect) {
        return null;
    }

    @Override
    public String visit(ProcDeclaration affect) {
        return null;
    }

    @Override
    public String visit(Fct2Declaration affect) {
        return null;
    }

    @Override
    public String visit(Fct2DeclarationType affect) {
        return null;
    }

    @Override
    public String visit(LvalueField affect) {
        return null;
    }

    @Override
    public String visit(LvalueIndex affect) {
        return null;
    }

    @Override
    public String visit(Array affect) {
        return null;
    }

    @Override
    public String visit(LvalueRecord affect) {
        return null;
    }

    @Override
    public String visit(Call affect) {
        return null;
    }

    @Override
    public String visit(VarDeclarationType affect) {
        return null;
    }
}
