Feature: snippet

Scenario: test storing data into local memory
	Given start
	And set a = []
	
	And new snippet "s1"
		And modify $a begin
			And add $x
		And end
	And save snippet
	
	And set x = "1" 
	And s1;
	
	And new snippet "s2"
		And modify $a begin
			And add $y
		And end
		And s1;
	And save snippet	
	
	And set y = "2" 
	And set x = "3" 
	And s2;
	
	
	And print $a
