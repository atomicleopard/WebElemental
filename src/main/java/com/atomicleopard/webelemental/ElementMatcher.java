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
import org.hamcrest.Matchers;

/**
 * <p>
 * {@link ElementMatcher} provides an accessible, fluent API to perform
 * assertions on the selected elements within an {@link Element}.
 * </p>
 * <p>
 * An {@link ElementMatcher} is obtained by invoking {@link Element#verify()} on
 * an Element.
 * </p>
 * <p>
 * While JUnit assertions and Hamcrest matchers are powerful and useful for java
 * developers, they offer a higher barrier of entry to non-experts.
 * ElementMatcher is an acknowledgment that the audience for web tests can be
 * different to the audience for unit tests and makes an effort to make the
 * verifications and assertions occurring more readable.
 * </p>
 * <p>
 * But don't worry, you can still go Hamcrest crazy. All the methods offer
 * overloads allowing you to harness the flexibility and power of hamcrest
 * matchers.
 * </p>
 * <p>
 * If you wish to perform assertions directly against an {@link Element} using
 * JUnit, you can access {@link Matchers} from the factory class
 * {@link ElementMatchers}.
 * </p>
 * 
 * @see Element
 * @see ElementMatchers
 */
public class ElementMatcher {
	private Element element;

	public ElementMatcher(Element element) {
		super();
		this.element = element;
	}

	/**
	 * Asserts that the Element is present, in that at least one element was
	 * selected
	 * 
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher isPresent() {
		assertThat(element, size(not(0)));
		return this;
	}

	/**
	 * Asserts that the Element is not present, in that no elements were
	 * selected
	 * 
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher isNotPresent() {
		assertThat(element, size(0));
		return this;
	}

	/**
	 * Asserts that the Element is not empty of content, in that the first
	 * selected element has some textual content
	 * 
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher isNotEmpty() {
		assertThat(trimToNull(element.text()), not(nullValue()));
		return this;
	}

	/**
	 * Asserts that the Element has the specified number of selected elements
	 * 
	 * @param size
	 *            the expected number of selected elements
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasSize(int size) {
		return hasSize(is(size));
	}

	/**
	 * Asserts that the Element has a number of selected elements matching the
	 * given matcher
	 * 
	 * @param matcher
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasSize(Matcher<Integer> matcher) {
		assertThat(element, size(matcher));
		return this;
	}

	/**
	 * Asserts that the Element is visible
	 * 
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 * @see Element#isVisible()
	 */
	public ElementMatcher isVisible() {
		assertThat(element.isVisible(), is(true));
		return this;
	}

	/**
	 * Asserts that the Element is not visible
	 * 
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 * @see Element#isVisible()
	 */
	public ElementMatcher isNotVisible() {
		assertThat(element.isVisible(), is(false));
		return this;
	}

	/**
	 * Asserts that the Element has the given attribute with the given value
	 * 
	 * @param attribute
	 *            the name of the attribute to verify
	 * @param expected
	 *            the expected value of the named attribute
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasAttribute(String attribute, String expected) {
		return hasAttribute(attribute, is(expected));
	}

	/**
	 * Asserts that the Element has the given attribute matching the given value
	 * 
	 * @param attribute
	 *            the name of the attribute to verify
	 * @param matcher
	 *            the matcher to verify the named attribute with
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasAttribute(String attribute, Matcher<String> matcher) {
		assertThat(element, attr(attribute, matcher));
		return this;
	}

	/**
	 * Asserts that the Element's text matches the given expected text
	 * 
	 * @param expectedText
	 *            the expected value of the Element's text
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasText(String expectedText) {
		return hasText(is(expectedText));
	}

	/**
	 * Asserts that the Element's text matches the given matcher
	 * 
	 * @param matcher
	 *            the matcher to verify the text with
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasText(Matcher<String> matcher) {
		assertThat(element.text(), matcher);
		return this;
	}

	/**
	 * Asserts that the Element's value matches the given expected value
	 * 
	 * @param value
	 *            the expected value of the Element's value
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasValue(String value) {
		assertThat(element, value(is(value)));
		return this;
	}

	/**
	 * Asserts that the Element's value matches the given matcher
	 * 
	 * @param matcher
	 *            the matcher to verify the value with
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasValue(Matcher<String> matcher) {
		assertThat(element, value(matcher));
		return this;
	}

	/**
	 * Asserts that the Element's id matches the given expected value
	 * 
	 * @param value
	 *            the expected value of the Element's id
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasId(String id) {
		assertThat(element, id(id));
		return this;
	}

	/**
	 * Asserts that the Element's id matches the given matcher
	 * 
	 * @param matcher
	 *            the matcher to verify the id with
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
	public ElementMatcher hasId(Matcher<String> matcher) {
		assertThat(element, id(matcher));
		return this;
	}

	/**
	 * Asserts that the Element matches the given matcher
	 * 
	 * @param matcher
	 *            the matcher to verify the Element with
	 * @return this {@link ElementMatcher}, supporting a fluid API
	 */
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
