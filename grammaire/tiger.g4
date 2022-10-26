grammar tiger;
 
@header{
package parser;
}
 
program : exprop EOF ;

exprop :
       | expr (operator expr)*
       ;

expr   :
       | STR
       | 'nil'
       | '-' expr
       | id (lvalue|call)
       | conditionnel
       ;

operator :
       | plus               // pour faire +, -, *, /
       | ineg               // pour faire <, >, >=, <=
       | compar             // pour faire = (==) et <> (!=)
       ;

ineg   :
       | expr ('<'|'>'|'<='|'>=') expr
       ;

compar :
       | expr ('='|'<>') expr
       ;

plus    : mult(('+'|'/') mult)*
        ;

mult    : value (('*'|'/')value)*
        ;


value   : INT
        | ID
        | '(' expr ')'
        ;

lvalue :
       | ('.' id ('[' expr ']')?)* affect?
       ;

affect :
       | ':=' expr
       ;

call : 
       | '(' expr* ')'
       ;

id :
       | ID  
       ;

type_id :
       |ID
       ;

conditionnel :
       | 'if' expr 'then' expr ('else' expr)?
       ;

// J'ai tout supprimer pour l'instant pour pas qu'on soit trop influencé, on peut s'inspirer de la grammaire du manuel,
//  mais il y a beaucoup de chose récursive gauche à traité et de factorisation à faire.
// Adrien

 
// Les terminaux (def des exp régulières reconnaissant les tokens)
 
ID     : ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
       ;

INT    : ('0'..'9')+
       ;
 
STR    : '"'('a'..'z'|'A'..'Z'|'0'..'9')*'"'
	  ;
 
WS     : [ \n\t\r]+ ->skip
       ;
 
COM    : [(‘\*’.*‘*\’)]+ ->skip
       ;
 
