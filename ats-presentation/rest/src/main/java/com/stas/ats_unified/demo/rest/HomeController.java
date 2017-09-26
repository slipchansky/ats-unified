/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package com.stas.ats_unified.demo.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/{tenantId}/echo", method = RequestMethod.POST)
    public Map echo(
    		@PathVariable("tenantId") String tenantId,  
    		@RequestParam Map<String, String> params, 
    		@RequestBody  Map body, 
    		@RequestHeader("sessionId") String sessionId) throws Exception {
    	Map result = new HashMap<> ();
    	result.put("tenantId", tenantId);
    	result.put ("parameters", params);
    	result.put ("body", body);
    	result.put ("session", sessionId);
        return result;
    }
}
