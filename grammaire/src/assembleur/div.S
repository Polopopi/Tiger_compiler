div		;Division de R2 par R1
		STMFA	SP!, {R3-R5}
		
		MOV		R0, #0		;Registre de res, on fait tendre R0 vers res
		MOV		R3, #0		;0 pour res positif, 1 pour res négatif
		
		CMP		R2, #0
		RSBLT	R2, R2, #0 	;R2 = |R2|
		EORLT	R3, R3, #1	;R3 = 1 mais autant faire un MOV
		
		CMP		R1, #0
		RSBLT	R1, R1, #0
		EORLT	R3, R3, #1
		
		
		MOV		R4, R1		;Transfert dans registre de travail
		MOV		R5, #1
		
_div_max	
		LSL		R4, R4, #1	;Multiplie R4(R1) par 2
		LSL		R5, R5, #1	;Multiplie R5 par 2
		CMP		R4, R2		;Jusqu'à ce que R4(R1) > R2, on dépasse l'égalité délibérément
		BLE		_div_max
		
		
_div_loop	
		LSR		R4, R4, #1	;Divise R4(R1) par 2
		LSR		R5, R5, #1	;Divise R5 par 2
		CMP		R4, R2		;Jusqu'à ce que R4(R1) <= R2 DONC 3 <= 12/R5 soit R5 <= 12/3
		BGT		_div_loop
		
		ADD		R0, R0, R5
		SUB		R2, R2, R4
		CMP		R2, R1		;Jusqu'à ce que R2 < R1 SOIT R2 n'est plus divisible par R1
		BGE		_div_loop
		
		CMP		R3, #1		;Si res est négatif
		BNE		_div_exit
		CMP		R2, #0		;S'il y a un reste de 1
		ADDNE	R0, R0, #1	;on arrondi à l'entier sup
		RSB		R0, R0, #0	;On remet les nombres en négatifs
		RSB		R2, R2, #0
		ADDNE	R2, R2, R1	;Différence entre le résultat exacte et approximé car entier
		
		
_div_exit	
		LDMFA	SP!, {R3-R5}
		MOV		PC, LR
