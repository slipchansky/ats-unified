Feature: for each

Scenario: "for each" clause for iterating array
	Given start
	And set outer = ["a", "b"]
	And set inner = ["1", "2"]
	And set a = []
	And for each o of $outer begin
		And print $o title "outrer"
		And modify $a begin
			And add $o
		And end 
		And for each i of $inner begin
			Then print $i title "inner"
			And modify $a begin
				And add $i
			And end 
		And next
	And next	
	