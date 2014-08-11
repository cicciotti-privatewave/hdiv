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
package org.hdiv.config.annotation.builders;

import static org.junit.Assert.*;

import org.hdiv.config.HDIVConfig;
import org.hdiv.regex.PatternMatcherFactory;
import org.junit.Before;
import org.junit.Test;

public class SecurityConfigBuilderTest {

	private SecurityConfigBuilder builder;

	@Before
	public void setUp() {
		this.builder = new SecurityConfigBuilder(new PatternMatcherFactory());
	}

	@Test
	public void build() {
		assertNotNull(this.builder);

		this.builder
			.cookiesConfidentiality(false)
			.stateParameterName("state")
			.maxPagesPerSession(23)
			.reuseExistingPageInAjaxRequest(true)
			.cipher()
				.algorithm("algorithm").and()
			.sessionExpired()
				.loginPage("/login.html");

		HDIVConfig config = this.builder.build();
		assertNotNull(config);
		assertEquals(false, config.isCookiesConfidentialityActivated());
		assertEquals("state", config.getStateParameterName());
		assertEquals(true, config.isReuseExistingPageInAjaxRequest());
		
		assertEquals(23, this.builder.getMaxPagesPerSession());
		assertEquals("algorithm", this.builder.getCipherConfigure().getAlgorithm());
	}
}
