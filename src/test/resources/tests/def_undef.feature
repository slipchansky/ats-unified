Feature: def_undef

Scenario:
	Given start
	Then set x = "test"
	And $x == "test"
	And def x = "newvalue"
	And $x == "test"
	And undef x
	And ifdef $x
	Then fail "X must not be defined"
	And endif
