package assembleur;

import ast.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class AsrCreator implements AstVisitor<String> {
    private Asr asr;

    private int idGenerator;

    private int generateId(){
        return(idGenerator++);
    }

    private String generateFlag(){
        return("flag"+generateId());
    }

    public AsrCreator(){
        this.asr=new Asr();

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
        String texte=program.affect.accept(this);
        return null;
    }

    @Override
    public String visit(Affect affect) {

        return null;
    }

    @Override
    public String visit(Or affect) {

        return null;
    }

    @Override
    public String visit(And affect) {

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
    public String visit(Inf affect) {
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
        return null;
    }

    @Override
    public String visit(Minus minus) {//
        String left= minus.left.accept(this);
        String right=minus.right.accept(this);
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
        asr.stockerValeurSP();

        return null;
    }

    @Override
    public String visit(Mult affect) {
        return null;
    }

    @Override
    public String visit(Divide affect) {
        return null;
    }

    @Override
    public String visit(MinusExpr affect) {
        return null;
    }

    @Override
    public String visit(IfThen ifThen) {
        String thenFlag = generateFlag();
        String elseFlag = generateFlag();

        ifThen.condition.accept(this);
        asr.cmp("r1","#0");
        asr.b("NE", thenFlag);
        asr.b(elseFlag);
        asr.flag(thenFlag);
        ifThen.thenBlock.accept(this);
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElse) {
        String thenFlag = generateFlag();
        String elseFlag = generateFlag();

        ifThenElse.condition.accept(this);
        asr.cmp("r1","#0");
        asr.b("NE", thenFlag);
        asr.b(elseFlag);
        asr.flag(thenFlag);
        ifThenElse.thenBlock.accept(this);
        return null;
    }

    @Override
    public String visit(Let affect) {
        return null;
    }

    @Override
    public String visit(For affect) {
        String forFlag = generateFlag();
        String forEndFlag = generateFlag();

        asr.flag(forFlag);


        return null;
    }

    @Override
    public String visit(While whileNode) {
        String whileFlag = generateFlag();
        String whileEndFlag = generateFlag();

        asr.flag(whileFlag);
        whileNode.condition.accept(this);
        asr.cmp("r1", "#0");
        asr.b("EQ",whileEndFlag);

        whileNode.bloc.accept(this);

        asr.b(whileFlag);
        asr.flag(whileEndFlag);

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
