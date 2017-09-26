Feature: test substring extracting by regexp
Scenario: test substring extracting by regexp
	Given set str = "Java(TM) SE Runtime Environment (build 1.8.0_65-b17)"
	And set ver = $str/.* \(build (.*)\)/
	And "1.8.0_65-b17" == $ver


	And set array:
	"""
	[ 
		"java version \"1.8.0_65\"", 
		"Java(TM) SE Runtime Environment (build 1.8.0_65-b17)", 
		"Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)"
	]
	""" 
	And set ver = $array/.*java version "(.*)".*/
	And "1.8.0_65" == $ver 
	