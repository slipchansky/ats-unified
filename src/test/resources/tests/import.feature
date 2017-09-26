Feature: import

Scenario: test storing data into local memory
	Given import "snippet.feature"
	Then start
	And print "Now we will call snippet from using using"
	
	And set a = []
	And set y = "a" 
	And set x = "b"
	And s2;
	And print $a title "Result of applying used snippet to a"
	
	
