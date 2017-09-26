Feature: import - import

Scenario: test storing data into local memory
	Given import "import.feature"
	And print "Now we will call snippet"
	
	And set y = "c" 
	And set x = "d" 
	And s2;
	And print $a title "Result of applying used snippet to a"
	
	
