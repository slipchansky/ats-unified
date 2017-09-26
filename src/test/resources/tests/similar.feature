Feature: String similarity assertions
Scenario: Test string similarity assertion
	And set str = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)"
	And set otherStr = "mama myla ramu"
	
	
	And $str ~= /.*\(build (.*)\)/
	And $otherStr !~ /.*\(build (.*)\)/
	
	And set pattern = ".*\(build (.*)\)"
	
	And $str ~= $pattern 
	And $otherStr !~ $pattern  
	
	