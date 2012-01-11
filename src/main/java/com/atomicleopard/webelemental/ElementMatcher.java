/*
 *  Copyright (c) 2011 Nicholas Okunew
 *  All rights reserved.
 *  
 *  This file is part of the com.atomicleopard.webelemental library
 *  
 *  The com.atomicleopard.webelemental library is free software: you 
 *  can redistribute it and/or modify it under the terms of the GNU
 *  Lesser General Public License as published by the Free Software Foundation, 
 *  either version 3 of the License, or (at your option) any later version.
 *  
 *  The com.atomicleopard.webelemental library is distributed in the hope
 *  that it will be useful, but WITHOUT ANY WARRANTY; without even
 *  the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with the com.atomicleopard.webelemental library.  If not, see 
 *  http://www.gnu.org/licenses/lgpl-3.0.html.
 */
package com.atomicleopard.webelemental;

import static com.atomicleopard.webelemental.ElementMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.hamcrest.Matcher;

public class ElementMatcher {
	private Element element;

	public ElementMatcher(Element element) {
		super();
		this.element = element;
	}

	public ElementMatcher isPresent() {
		assertThat(element, size(not(0)));
		return this;
	}

	public ElementMatcher isNotPresent() {
		assertThat(element, size(0));
		return this;
	}

	public ElementMatcher isNotEmpty() {
		assertThat(trimToNull(element.text()), not(nullValue()));
		return this;
	}

	public ElementMatcher hasSize(int size) {
		return hasSize(is(size));
	}

	public ElementMatcher hasSize(Matcher<Integer> matcher) {
		assertThat(element, size(matcher));
		return this;
	}

	public ElementMatcher isVisible() {
		assertThat(element.isVisible(), is(true));
		return this;
	}

	public ElementMatcher isNotVisible() {
		assertThat(element.isVisible(), is(false));
		return this;
	}

	public ElementMatcher hasAttribute(String attribute, String expected) {
		return hasAttribute(attribute, is(expected));
	}

	public ElementMatcher hasAttribute(String attribute, Matcher<String> matcher) {
		assertThat(element, attr(attribute, matcher));
		return this;
	}

	public ElementMatcher hasText(Matcher<String> matcher) {
		assertThat(element.text(), matcher);
		return this;
	}

	public ElementMatcher hasText(String expectedText) {
		return hasText(is(expectedText));
	}

	public ElementMatcher hasValue(Matcher<String> matcher) {
		assertThat(element, value(matcher));
		return this;
	}

	public ElementMatcher hasValue(String value) {
		assertThat(element, value(is(value)));
		return this;
	}

	public ElementMatcher hasId(String id) {
		assertThat(element, id(id));
		return this;
	}

	public ElementMatcher hasId(Matcher<String> matcher) {
		assertThat(element, id(matcher));
		return this;
	}

	public ElementMatcher using(Matcher<Element> matcher) {
		assertThat(element, matcher);
		return this;
	}

	String trimToNull(String string) {
		if (string != null) {
			string = string.trim();
			if (string.length() == 0) {
				string = null;
			}
		}
		return string;
	}
}
