package assembleur;

import java.io.PrintWriter;
import java.util.ArrayList;

//Par default, R12 est BP
//valeur à donner à une variable est par convention enregistrée dans R1
//pour lire une variable dans la pile, sa valeur est lue et enregistrée dans R2
public class Asr {

    private ArrayList<String> asr;
    public Asr(){
        this.asr=new ArrayList<String>();
    }
    public ArrayList<String> getAsr(){
        return this.asr;
    }
    public void incrementerSp(int nbr){
        this.asr.add("    ADD SP, SP, #"+nbr*4);
    }

    public void decrementerSp(int nbr) {
        this.asr.add("    SUB SP, SP, #"+nbr*4);
    }

    public void setVar(int valeur) {
        this.asr.add("    LDR R1, ="+valeur);
    }
    public void positionnerBP(){
        this.asr.add("    MOV R12,SP");
    }
    public void  plus(){
        this.asr.add("    ADD R1, R1, R2");
    }
    public void  plus(String param1,String param2,String param3){
        this.asr.add("    ADD "+param1+", "+param2+", "+param3); //R1-R2=>R1
    }
    public void  moins(String param1,String param2,String param3){
        this.asr.add("    SUB "+param1+", "+param2+", "+param3); //R1-R2=>R1
    }

    public void stockerValeurBP(int ordrePile){
        this.asr.add("    STR R1, [R12, #"+ordrePile*4+"]");
    }

    /**stockerValeurSP est destiné à stocker une valeur dans le stack qui est pointé par SP
     * */
    public void stockerValeurSP(){
        this.asr.add("    STR R1, [SP, #0]");
    }

    /**lireVarPile est destiné à lire un stack en référençant BP
     * @param ordrePile :nombre de stack à incrémenter pour atteindre le stack objectif
     * */
    public void lireVarPile(int ordrePile){
        this.asr.add("    LDR R2, [R12, #"+ordrePile*4+"]");
    }
    public void lireVarSP(){
        this.asr.add("    LDR R2, [SP, #0]");
    }




    public void enregistreValeur(){
        this.asr.add( "    MOV R1,R2");
    }

    public void mov(String op1, String op2){
        this.asr.add("    MOV " + op1 +", " +op2);
    }


    public void cmp(String op1,String op2){
        this.asr.add("    CMP " + op1 + ", " + op2);
    }

    public void b(String flag){
        this.asr.add("    B " + flag);
    }

    public void b(String cond, String flag){
        this.asr.add("    B" + cond + " " + flag);
    }

    public void flag(String name){
        this.asr.add(name);
    }




}
