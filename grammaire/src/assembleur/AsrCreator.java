package assembleur;

import ast.*;

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
            Path path = Path.of("./src/assembleur/div.S");
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
    public String visit(Equal affect) {

        return null;
    }

    @Override
    public String visit(Diff affect) {
        return null;
    }

    @Override
    public String visit(Inf inf) {
        return null;
    }

    @Override
    public String visit(Sup affect) {
        return null;
    }

    @Override
    public String visit(InfEqual affect) {
        return null;
    }

    @Override
    public String visit(SupEqual affect) {
        return null;
    }

    @Override
    public String visit(Plus affect) {
        String left= plus.left.accept(this);
        String right=plus.right.accept(this);
        if (left!=null){
            asr.incrementerSp(1);
            asr.setVar(Integer.parseInt(left));
            asr.stockerValeurSP();
        }
        if (right!=null){
            int rightValue = Integer.parseInt(right);
            asr.setVar(rightValue);
        }

        asr.lireVarSP();
        asr.plus("R1","R2","R1");
        asr.stockerValeurSP();

        return null;
    }

    @Override
    public String visit(Minus minus) {//
        String left= minus.left.accept(this);
        String right=minus.right.accept(this); // STEVEN Il faudrait le faire après car sinon le résultat de left contenu dans R1 sera écrasé
        if (left!=null){
            asr.incrementerSp(1);
            asr.setVar(Integer.parseInt(left));
            asr.stockerValeurSP();
        }
        if (right!=null){
            int rightValue = Integer.parseInt(right);
            asr.setVar(rightValue);
        }

        asr.lireVarSP();
        asr.moins("R1","R2","R1");//R2 est le registre où on enregistre la valeur lue depuis la pile, R1 est le registre
                                  // où on peut donner une valeur à un stack
        asr.stockerValeurSP();  // STEVEN pk on stocke la valeur dans la pile après le SUB ? 
                                //jcrois on stocke le résultat du membre gauche dans la pile pour faire le SUB mais c tout

        return null;
    }

    @Override
    public String visit(Mult affect) {
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
    public String visit(IfThen affect) {
        return null;
    }

    @Override
    public String visit(IfThenElse affect) {
        return null;
    }

    @Override
    public String visit(Let affect) {
        return null;
    }

    @Override
    public String visit(For affect) {
        return null;
    }

    @Override
    public String visit(While affect) {
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
    public String visit(SeqExpr affect) {
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
