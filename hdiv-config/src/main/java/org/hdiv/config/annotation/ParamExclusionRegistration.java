/**
 * Copyright 2005-2013 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.config.annotation;

import org.springframework.util.Assert;

/**
 * Contains the data of one parameter exclusion.
 * 
 * @since 2.1.7
 */
public class ParamExclusionRegistration {

	private String[] parameterPatterns;

	private String urlPattern;

	public ParamExclusionRegistration(String[] parameterPatterns) {
		Assert.notEmpty(parameterPatterns, "Parameter names pattern are required to create a exclusion.");
		this.parameterPatterns = parameterPatterns;
	}

	public void forUrls(String urlPattern) {
		Assert.notNull(urlPattern, "A URL path is required");
		this.urlPattern = urlPattern;
	}

	protected String[] getParameterPatterns() {
		return parameterPatterns;
	}

	protected String getUrlPattern() {
		return urlPattern;
	}

}
