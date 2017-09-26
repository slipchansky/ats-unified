Feature: test os shell command

Scenario: test storing data into local memory
	Given start
	And prepare SHELL
	#And current directory "D:\index-app" 
	And command java -version
	And run
	And print $.output 
	And set ver = $.output/.*java version "(.*)".*/
	And print $ver
