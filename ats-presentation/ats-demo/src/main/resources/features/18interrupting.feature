Feature: Demonstrate of testing interrupting
	Scenario: Force fail
		Given start
		And set a = "3"
		And set b = [1, 2, 3]
		And fail "message"
		
	Scenario: Force abort
		Given start
		And set a = "3"
		And set b = [1, 2, 3]
		And print "$a, $b"
		And abort
		
	Scenario: This scenario is unreachable after abort
		Given start
		And print "This message will not be printed due to feature execution abortion"
