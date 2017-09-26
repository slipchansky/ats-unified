Feature: test repeat feature
Scenario: test repeat scenario
	And set a = []
	And repeat i 10 times
		And add $i to $a
	And next
	And $a.size == 10
	
	And $a[0] == 1
	And $a[9] == 10
	
	And print $a
