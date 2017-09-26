Feature: Extracting substring by pattern
	Scenario: Substring extraction
	    Given set str = "java version '1.8.0_65'"
	    When set version = $str/java version '(.*)'.*/ 
	    Then print $version