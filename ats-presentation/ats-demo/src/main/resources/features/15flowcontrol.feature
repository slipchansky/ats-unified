Feature: Demonstrates flow control
	Scenario: if-else-endif
	Given set a = 2
	And set b = "x" 
	And if a < 3
		And set b = "Y"
	And else
		And set b = "Z"
	And endif		  
	
	Scenario: IFDEF-ELSE-ENDIF
	And set a = 2
	And ifdef $a
		And set b = "Y"
	And else
		And set b = "Z"
	And endif 

	Scenario: IFNDEF-ELSE-ENDIF
	And undef a
	And ifdef $a
		And set b = "Y"
	And else
		And set b = "Z"
	And endif 
	