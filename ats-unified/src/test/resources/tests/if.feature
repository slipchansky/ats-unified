Feature: assertions

Scenario: assertions
	Given start
	
	And set x = ["a", "b"]
	And set y = ["1", "2"]
	
	And set z = ["2", "1"]
	And set a = 1
	And set b = 2
	And set c = 1
	And if $a == $c
	
	And if $a == "1"
	And else
		And fail "$a == '1'"
	And endif
		
	And if $b != $a
	And else
		And fail "$b != $a"
	And endif	
		
	And if $y == $z
	And else
		And fail "$y == $z"
	And endif	
	
	And if $x != $y
	And else
		And fail "$x != $y"
	And endif	
	
	And if $a == 1
	And else
		And fail "$a == 1"
	And endif	
	
	And if 1 == $a
	And else
		And fail "1 == $a"
	And endif
	
	And set str = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)"
	And set otherStr = "mama myla ramu"
	
	And if $str ~= /.*\(build (.*)\)/
	And else
		And fail "$str ~= /.*build (.*))/"
	And endif
	
	And if $otherStr !~ /.*\(build (.*)\)/
	And else
		And fail "$otherStr !~ /.*build (.*))/"
	And endif
	
	
	And set pattern = ".*\(build (.*)\)"
	
	And $str ~= $pattern 
	And $otherStr !~ $pattern  
		
	
	Then set ok = "OK"
	
Scenario: one more scenario
	Given start
	And set x = ["a", "b"]
	And set y = ["1", "2"]
	And set z = ["2", "1"]
	And set a = 1
	And set b = 2
	And set c = 1
	
	And if $a == $c
	And else
		And fail "$a == $c"
	And end
		
	And if $a == "1"
	And else
		And fail "$a == '1'"
	And end	
	
	And if $b != $a
	And else
		And fail "$b != $a"
	And end	
	
	And if $y == $z
	And else
		And fail "$y == $z"
	And end	
	
	And if $x != $y
	And else
		And fail "$x != $y"
	And end	
	
	And if $a == 1
	And else
		And fail "$a == 1"
	And end	
	
	And if 1 == $a
	And else
		And fail "1 == $a"
	And end	
	
	Then remember ok = "OK"
		