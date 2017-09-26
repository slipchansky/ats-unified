Feature: Shell execution demonstration feature
	Scenario: Running shell, analyze output
	Given shell "java -version"
	And print $ title "shell output"
	And set versionInfo = all output.[?(@=~ /java version.*/)] from $
	And set versionStr = $versionInfo[0]
	And print $versionStr
	And set version = $versionStr/.*java version "(.*)".*/
	And print $version
	 
	Scenario: Running shell at particular directory
	Given shell "dir" at "c:/windows"
	And print $ title "directory of c:/windows"
	 
		