a = {
	"wellsfargo" : {
		"id" : "wellsfargo",
		"name" : "wellsfargo",
		"description" : "Test Knowledge Base 11",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishName",
				"description" : "Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd"
		}, {
			"type" : "string",
			"name" : "strField",
			"defaultValue" : "DEFAULT",
			"defaultValues" : null,
			"displayName" : "String Field",
			"visibility" : "PUBLIC",
			"regex" : ""
		}, {
			"type" : "bool",
			"name" : "boolField",
			"defaultValue" : "true",
			"defaultValues" : null,
			"displayName" : "Boolean Field",
			"visibility" : "PUBLIC"
		}, {
			"type" : "enum",
			"name" : "enumField",
			"defaultValue" : null,
			"defaultValues" : [ "val1" ],
			"displayName" : "Enumeration Field",
			"visibility" : "PUBLIC",
			"values" : [ "val1", "val2", "val3" ],
			"singleSelect" : null
		}, {
			"type" : "numeric",
			"name" : "numField",
			"defaultValue" : null,
			"defaultValues" : null,
			"displayName" : "Numeric Field",
			"visibility" : "PUBLIC",
			"minValue" : "0",
			"maxValue" : "999",
			"stringFormat" : ""
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : true,
		"active" : true,
		"external" : false
	},
	"groupon_notactive" : {
		"id" : "groupon_notactive",
		"name" : "inactive",
		"description" : "",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.1-SNAPSHOT",
		"languages" : {
			"en_US" : {
				"name" : "inactive",
				"description" : "",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : false,
		"active" : false,
		"external" : false
	},
	"groupon" : {
		"id" : "groupon",
		"name" : "groupon",
		"description" : "Groupon with skill",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishName",
				"description" : "Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "string",
			"name" : "strField",
			"defaultValue" : "DEFAULT",
			"defaultValues" : null,
			"displayName" : "String Field",
			"visibility" : "PUBLIC",
			"regex" : ""
		}, {
			"type" : "numeric",
			"name" : "numField",
			"defaultValue" : "555",
			"defaultValues" : null,
			"displayName" : "Numeric Field",
			"visibility" : "PUBLIC",
			"minValue" : "0",
			"maxValue" : "999",
			"stringFormat" : ""
		}, {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01 00:00:00",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd HH:mm:ss"
		}, {
			"type" : "enum",
			"name" : "enumField",
			"defaultValue" : ",val1,",
			"defaultValues" : null,
			"displayName" : "Enumeration Field",
			"visibility" : "PUBLIC",
			"values" : [ "val1", "val2", "val3" ],
			"singleSelect" : null
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : true,
		"active" : true,
		"external" : false
	},
	"inactive" : {
		"id" : "inactive",
		"name" : "inactive",
		"description" : "",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.1-SNAPSHOT",
		"languages" : {
			"en_US" : {
				"name" : "inactive",
				"description" : "",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : false,
		"active" : false,
		"external" : false
	},
	"knowledgefaq" : {
		"id" : "knowledgefaq",
		"name" : "knowledgeFAQ",
		"description" : "Test Knowledge Base dsfd",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishName",
				"description" : "Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "string",
			"name" : "strField",
			"defaultValue" : "DEFAULT",
			"defaultValues" : null,
			"displayName" : "String Field",
			"visibility" : "PUBLIC",
			"regex" : ""
		}, {
			"type" : "numeric",
			"name" : "numField",
			"defaultValue" : "555",
			"defaultValues" : null,
			"displayName" : "Numeric Field",
			"visibility" : "PUBLIC",
			"minValue" : "0",
			"maxValue" : "999",
			"stringFormat" : ""
		}, {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd"
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : true,
		"active" : true,
		"external" : false
	},
	"bank_of_america" : {
		"id" : "bank_of_america",
		"name" : "bank_of_america",
		"description" : "Test Knowledge Base 11",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishName",
				"description" : "Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "enum",
			"name" : "enumField",
			"defaultValue" : ",val1,",
			"defaultValues" : null,
			"displayName" : "Enumeration Field",
			"visibility" : "PUBLIC",
			"values" : [ "val1", "val2", "val3" ],
			"singleSelect" : null
		}, {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd"
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : true,
		"active" : true,
		"external" : false
	},
	"groupon_notpublic" : {
		"id" : "groupon_notpublic",
		"name" : "groupon_notpublic",
		"description" : "",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.1-SNAPSHOT",
		"languages" : {
			"en_US" : {
				"name" : "groupon_notpublic",
				"description" : "",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			},
			"index" : {
				"numberOfShards" : "5",
				"numberOfReplicas" : "3"
			}
		},
		"public" : false,
		"active" : true,
		"external" : false
	},
	"articles" : {
		"id" : "articles",
		"name" : "articles",
		"description" : "Test Knowledge Base For Articles",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishArticlesName",
				"description" : "Articles Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "string",
			"name" : "strField",
			"defaultValue" : "DEFAULT",
			"defaultValues" : null,
			"displayName" : "String Field",
			"visibility" : "PUBLIC",
			"regex" : ""
		}, {
			"type" : "numeric",
			"name" : "numField",
			"defaultValue" : "555",
			"defaultValues" : null,
			"displayName" : "Numeric Field",
			"visibility" : "PUBLIC",
			"minValue" : "0",
			"maxValue" : "999",
			"stringFormat" : ""
		}, {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01 00:00:00",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd HH:mm:ss"
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : true,
		"active" : true,
		"external" : false
	},
	"groupon1" : {
		"id" : "groupon1",
		"name" : "groupon1",
		"description" : "Groupon with skill",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : false,
			"skills" : [ "skill_groupon" ]
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishName",
				"description" : "Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "string",
			"name" : "strField",
			"defaultValue" : "DEFAULT",
			"defaultValues" : null,
			"displayName" : "String Field",
			"visibility" : "PUBLIC",
			"regex" : ""
		}, {
			"type" : "numeric",
			"name" : "numField",
			"defaultValue" : "555",
			"defaultValues" : null,
			"displayName" : "Numeric Field",
			"visibility" : "PUBLIC",
			"minValue" : "0",
			"maxValue" : "999",
			"stringFormat" : ""
		}, {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01 00:00:00",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd HH:mm:ss"
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : false,
		"active" : true,
		"external" : false
	},
	"orbitz" : {
		"id" : "orbitz",
		"name" : "orbitz",
		"description" : "Test Knowledge Base 11",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : true,
			"skills" : []
		},
		"version" : "8.5.000.11",
		"languages" : {
			"en_US" : {
				"name" : "EnglishName",
				"description" : "Description on English",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [ {
			"type" : "string",
			"name" : "strField",
			"defaultValue" : "DEFAULT",
			"defaultValues" : null,
			"displayName" : "String Field",
			"visibility" : "PUBLIC",
			"regex" : ""
		}, {
			"type" : "numeric",
			"name" : "numField",
			"defaultValue" : "555",
			"defaultValues" : null,
			"displayName" : "Numeric Field",
			"visibility" : "PUBLIC",
			"minValue" : "0",
			"maxValue" : "999",
			"stringFormat" : ""
		}, {
			"type" : "datetime",
			"name" : "dateField",
			"defaultValue" : "2014-09-01 00:00:00",
			"defaultValues" : null,
			"displayName" : "Date Field",
			"visibility" : "PUBLIC",
			"stringFormat" : "yyyy-MM-dd HH:mm:ss"
		} ],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"trendingPeriodInDays" : "30",
				"numberOfAnswers" : "3"
			}
		},
		"public" : true,
		"active" : true,
		"external" : false
	},
	"inactive_skilled" : {
		"id" : "inactive_skilled",
		"name" : "inactive_skilled",
		"description" : "",
		"type" : [ "FAQ", "ARTICLE", "STDRESPONSE" ],
		"feedbackType" : "EITHER",
		"access" : {
			"all" : false,
			"skills" : [ "grouponSkill" ]
		},
		"version" : "8.5.100.07",
		"languages" : {
			"en_US" : {
				"name" : "inactive_skilled",
				"description" : "",
				"defaultFilterConditions" : [],
				"schema" : "nlp"
			}
		},
		"customFields" : [],
		"mediaTypes" : [],
		"behavior" : {
			"faq" : {
				"trendingPeriodInDays" : "30",
				"outOfDomain" : "0.00001",
				"numberOfAnswersInPreConfidenceSelection" : "10",
				"numberOfAnswers" : "3"
			}
		},
		"public" : false,
		"active" : false,
		"external" : false
	}
}