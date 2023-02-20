package assembleur;

import java.io.PrintWriter;

//Par default, R12 est BP
//valeur à donner à une variable est par convention enregistrée dans R1
//pour lire une variable dans la pile, sa valeur est lue et enregistrée dans R2
public class Asr {


    public String incrementerSp(int nbr){
        return "    ADD SP, SP, #"+nbr*4;
    }

    public String decrementerSp(int nbr){
        return "    SUB SP, SP, #"+nbr*4;
    }


    public String setVar(int valeur){
        return "    LDR R1, ="+valeur ;
    }

    public String positionnerBP(){
        return "    MOV R12,SP";
    }

    public String  plus(){
        return "    ADD R1, R1, R2";
    }
    public String  moins(){
        return "    SUB R1, R1, R2";//R1-R2=>R1
    }

    public String stockerValeurBP(int ordrePile){
        return  "    STR R1, [R12, #"+ordrePile*4+"]";
    }

    /**stockerValeurSP est destiné à stocker une valeur dans le stack qui est pointé par SP
     * */
    public String stockerValeurSP(){
        return  "    STR R1, [SP, #0]";
    }

    /**lireVarPile est destiné à lire un stack en référençant BP
     * @param ordrePile :nombre de stack à incrémenter pour atteindre le stack objectif
     * */
    public String lireVarPile(int ordrePile){
        return "    LDR R2, [R12, #"+ordrePile*4+"]";
    }
    public String lireVarSP(){
        return "    LDR R2, [SP, #0]";
    }




    public String enregistreValeur(){
        return "    MOV R1,R2";
    }









}
