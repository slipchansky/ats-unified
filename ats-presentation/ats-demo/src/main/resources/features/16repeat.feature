Feature: Demonstration of n-times repetition cycle
	Scenario: iterator qualified repetition
	Given start 
	And set count = 10
	And repeat iterator $count times
		And print $iterator
	And next
	
	Scenario: no iterator (default i iterator) repetition
	Given start 
	And set count = 10
	And repeat $count times
		And print $i title "default iterator of i"
	And next
	
	
	Scenario: endless repetition, break by $i > 10
	# warning!!! break interrupt repetition only but not current operation flow ( we'll get all items from 0 to 10)
	Given start 
	And set count = 10
	And repeat
		And if $i > 9
			And break
		And endif
		And print $i title "endless repeat, i"	
	And next
	
	Scenario: endless repetition, break by $i > 10. how to avoid unwanted output of previous scenario
	# how to avoid previous confusion
	Given start 
	And set count = 10
	And repeat
		And if $i > 9
			And break
		And else	
			And print $i title "endless repeat, i <= 9"
		And endif
	And next
	