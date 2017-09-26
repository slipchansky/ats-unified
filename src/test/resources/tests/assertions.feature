Feature: assertions

Scenario: assertions
	Given start
	
	And set n = null
	And $n == $n
	And $n == null
	And null == $n
	And set x = ["a", "b"]
	And set y = ["1", "2"]
	
	And set z = ["2", "1"]
	And set a = 1
	And set b = 2
	And set c = 1
	Then $a == $c
	And $a == "1"
	And $b != $a
	And $y == $z
	And $x != $y
	And $a == 1
	And 1 == $a
	
	And set str = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)"
	And set otherStr = "mama myla ramu"
	
	And $str ~= /.*\(build (.*)\)/
	And $otherStr !~ /.*\(build (.*)\)/
	
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
	Then $a == $c
	And $a == "1"
	And $b != $a
	And $y == $z
	And $x != $y
	And $a == 1
	And 1 == $a
	Then remember ok = "OK"
		