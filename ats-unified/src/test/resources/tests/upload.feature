Feature: File uploading using REST
	Scenario: upload file
	Given start
    And remember formUser = "gks_super"
    And remember formPass = "p"
    And set attachmentPath = "data/sample.json"
        
	And using REST
    And FORMAUTH username $formUser password $formPass
	And home = "http://ua-slypchan-lt:9000/gks-cms"
	And url = "/tenants/1/kbs/groupon/langs/en/docs/b9201858-08d8-4639-a64b-58b10c0bb9cc/attachments"
	And attachment = $attachmentPath
	#And attachment = "data/sample.json"
	And method POST
	And send
	And print $
	 