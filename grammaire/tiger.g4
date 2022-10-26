grammar tiger;
 
@header{
package parser;
}
 
program : expr EOF ;
 

expr   :
       | STR
       | INT
       | 'nil'
       | '-' expr
       | operator
       | id (lvalue|call)
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
 
