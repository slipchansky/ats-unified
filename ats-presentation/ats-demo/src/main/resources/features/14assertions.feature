Feature: Demonstration of assertions checking
	Scenario: check assertions
	Given set n = null
	And set x = ["a", "b"]
	And set y = ["1", "2"]
	And set z = ["2", "1"]
	And set a = 1
	And set b = 2
	And set c = 1
	Then $n == $n
	And $n == null
	And null == $n 
	And $a == $c
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
	And $y contains all of $z 
	And $z contains any of $y 
	And $z does not contain $x 
	And $z does not contain "mama"
	And $z contains "2" 
	And set pattern = ".*\(build (.*)\)"
	And $str includes "Runtime Environment" 
	And $str does not include "mama" 
	And $str ~= $pattern 
	And $otherStr !~ $pattern  
