{
  "id" : "knowledgefaq",
  "name" : "knowledgefaq",
  "description" : "",
  "type" : [ "ARTICLE", "FAQ", "STDRESPONSE" ],
  "feedbackType" : "EITHER",
  "access" : {
    "all" : true,
    "skills" : [ ]
  },
  "version" : "2.0",
  "languages" : {
    "en_EN" : {
      "name" : "knowledgefaq",
      "description" : "",
      "defaultFilterConditions" : [ ],
      "schema" : "nlp"
    },
  	"en_US" : {
      "name" : "knowledgefaq",
      "description" : "",
      "defaultFilterConditions" : [ ],
      "schema" : "nlp"
    }
  },
  "customFields" : [ ],
  "mediaTypes" : [ "any" ],
  "behavior" : {
    "faq" : {
      "trendingPeriodInDays" : "30",
      "outOfDomain" : "0.6",
      "numberOfAnswersInPreConfidenceSelection" : "10",
      "numberOfAnswers" : "3"
    }
  },
  "public" : true,
  "external" : false,
  "active" : true
  
}