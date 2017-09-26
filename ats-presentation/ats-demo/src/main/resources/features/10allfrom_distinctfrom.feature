Feature: JsonPath all from, distinct from demonstration
 	Scenario: all from
 	Given set arr = [1, 2, 3, 3, 1, 2, 4, 6, 5, 8, 7]
 	When set all = all [*] from $arr 
 	And set distinct = distinct [*] from $arr
 	Then print $all
 	And print $distinct