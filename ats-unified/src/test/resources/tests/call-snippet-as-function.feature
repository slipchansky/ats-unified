Feature: Call snippet as function with direct referring data of snippet context

Scenario: Call snippet as function with direct referring data of snippet context
    # use syntax 
    # set var = <snippet-local-var-name>@<snippet name>
    # may be used in set/copy/remember expressions
    #  
  
	And new snippet "prepare section configuration"
		And begin
			And set options = "prepared section $option config"
			And set configuration = {"options" : [$options]}
			And set moreconfig = "Something else"
			And debug 
		And end
	And end

	 
	# lets define some "snippet parameter(s)"
	And set option = "option_value"
	
	
	
	#call snippet and set set snippet "configuration" variable to our "cfg" variable  
	And set cfg = $configuration.options from prepare section configuration
	
	#call snippet and set set snippet "moreconfig" variable to our "more" variable  
	And set more = $moreconfig from prepare section configuration
	
	And print $cfg title "prepared configuration"
	And print $more title "more configuration extracted as snippet return"

	# extract "moreconfig" from executed snippet context explicitly 	
	And print $.moreconfig title "more configuration extracted from executed snippet context"
	
	And set expectedCfg = { "options" : ["prepared section option_value config"] } 
	And set expectedCfgOptions = $expectedCfg.options
	 
	And $expectedCfgOptions == $cfg
	
	And $more == "Something else"
	And $.moreconfig == "Something else"
	And $expectedCfg == $.configuration 

	# ask for all local snippet variables 
	And set all = $ from prepare section configuration
	
	And $expectedCfg == $all.configuration
	And "Something else" == $all.moreconfig
	