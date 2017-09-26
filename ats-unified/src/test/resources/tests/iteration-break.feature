Feature: for each

Scenario: Break iteration repeat
	Given start
	And set a = []
	
	And repeat i
		And add $i to $a
		And if $i == 10
			Then break
		And endif
	And next
	And $a.size == 10
	And $a[0] == 1
	And $a[9] == 10
	
	
	And set array = ["1", "2"]
	And set dst = []
	Given set x = "A" 
	And for each i of $array begin
		And add $i to $dst
		And if $i == "1"
			Then break
		And endif 
	And next
	And $dst.size == 1
	And $dst[0] == "1"
		
	