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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hdiv.config.StartPage;
import org.hdiv.regex.PatternMatcherFactory;
import org.springframework.util.Assert;

/**
 * <p>
 * Registry to add exclusions to the validation phase.
 * </p>
 * <p>
 * Can contain two types of exclusions, URL and parameter. The first to exclude URLs from validation and the second to
 * exclude parameter names.
 * </p>
 * 
 * @since 2.1.7
 */
public class ExclusionRegistry {

	private PatternMatcherFactory patternMatcherFactory;

	private final List<UrlExclusionRegistration> urlRegistrations = new ArrayList<UrlExclusionRegistration>();
	private final List<ParamExclusionRegistration> paramRegistrations = new ArrayList<ParamExclusionRegistration>();

	public ExclusionRegistry(PatternMatcherFactory patternMatcherFactory) {
		this.patternMatcherFactory = patternMatcherFactory;
	}

	public UrlExclusionRegistration addUrlExclusions(String... urlPatterns) {
		Assert.notEmpty(urlPatterns, "Url patterns are required");
		UrlExclusionRegistration registration = new UrlExclusionRegistration(urlPatterns);
		urlRegistrations.add(registration);
		return registration;
	}

	public ParamExclusionRegistration addParamExclusions(String... parameterPatterns) {
		Assert.notEmpty(parameterPatterns, "Parameter patterns are required");
		ParamExclusionRegistration registration = new ParamExclusionRegistration(parameterPatterns);
		paramRegistrations.add(registration);
		return registration;
	}

	protected List<StartPage> getUrlExclusions() {

		List<StartPage> allStartPages = new ArrayList<StartPage>();

		for (UrlExclusionRegistration regitration : urlRegistrations) {
			List<StartPage> startPages = regitration.getExclusions();
			for (StartPage sp : startPages) {
				// Compile Pattern
				sp.setCompiledPattern(patternMatcherFactory.getPatternMatcher(sp.getPattern()));
				allStartPages.add(sp);
			}
		}
		return allStartPages;
	}

	protected List<String> getParamExclusions() {

		List<String> paramExclusions = new ArrayList<String>();

		for (ParamExclusionRegistration regitration : paramRegistrations) {
			String urlPattern = regitration.getUrlPattern();
			if (urlPattern == null) {
				for (String paramPattern : regitration.getParameterPatterns()) {
					paramExclusions.add(paramPattern);
				}
			}
		}
		return paramExclusions;
	}

	protected Map<String, List<String>> getParamExclusionsForUrl() {

		Map<String, List<String>> paramExclusions = new HashMap<String, List<String>>();

		for (ParamExclusionRegistration regitration : paramRegistrations) {
			String urlPattern = regitration.getUrlPattern();
			if (urlPattern != null) {
				List<String> paramPatterns = new ArrayList<String>();
				for (String paramPattern : regitration.getParameterPatterns()) {
					paramPatterns.add(paramPattern);
				}
				paramExclusions.put(urlPattern, paramPatterns);
			}
		}
		return paramExclusions;
	}
}
