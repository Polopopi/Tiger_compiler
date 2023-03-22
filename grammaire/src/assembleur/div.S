div		;Division de R1 par R0 et res dans R0
		STMFA	SP!, {R3-R6}
		
		MOV		R6, #0		;Registre de res, on fait tendre R6 vers res et on mov dans R0 à la fin
		MOV		R3, #0		;0 pour res positif, 1 pour res négatif
		
		CMP		R1, #0
		RSBLT	R1, R1, #0 	;R1 = |R1|
		EORLT	R3, R3, #1	;R3 = 1 mais autant faire un MOV
		
		CMP		R0, #0
		RSBLT	R0, R0, #0
		EORLT	R3, R3, #1
		
		
		MOV		R4, R0		;Transfert dans registre de travail
		MOV		R5, #1
		
_div_max	
		LSL		R4, R4, #1	;Multiplie R4(R0) par 2
		LSL		R5, R5, #1	;Multiplie R5 par 2
		CMP		R4, R1		;Jusqu'à ce que R4(R0) > R1, on dépasse l'égalité délibérément
		BLE		_div_max
		
		
_div_loop	
		LSR		R4, R4, #1	;Divise R4(R0) par 2
		LSR		R5, R5, #1	;Divise R5 par 2
		CMP		R4, R1		;Jusqu'à ce que R4(R0) <= R1 DONC 3 <= 12/R5 soit R5 <= 12/3
		BGT		_div_loop
		
		ADD		R6, R6, R5
		SUB		R1, R1, R4
		CMP		R1, R0		;Jusqu'à ce que R1 < R0 SOIT R1 n'est plus divisible par R0
		BGE		_div_loop
		
		CMP		R3, #1		;Si res est négatif
		BNE		_div_exit
		CMP		R1, #0		;S'il y a un reste de 1
		ADDNE	R6, R6, #1	;on arrondi à l'entier sup
		RSB		R6, R6, #0	;On remet les nombres en négatifs
		RSB		R1, R1, #0
		ADDNE	R1, R1, R0	;Reste entre le résultat exacte et approximé car entier
		
		
_div_exit	
		MOV		R0, R6
		LDMFA	SP!, {R3-R6}
		MOV		PC, LR
