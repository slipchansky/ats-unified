Feature: Call REST feature
	Scenario: Call rest scenario
	    Given start
	    And set tenantId = "some_tenant"
	    And set kbId = "groupon"
	    And set requestBody = {"a" : "A", "object" : {"x" : "X", "y" : ["1", "2", $kbId] } }
	    When using REST
		    And home = "http://localhost:8080"
		    And url = "/{tenantId}/echo"
		    And param kbId
		    And param lang = "en"
		    And header sessionId = "session identifier"
		    And body = $requestBody
	    	And method POST
	    And send
	    And print $
	