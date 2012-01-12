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

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import com.atomicleopard.webelemental.matchers.HasAttributeMatcher;
import com.atomicleopard.webelemental.matchers.HasClassMatcher;
import com.atomicleopard.webelemental.matchers.HasIdMatcher;
import com.atomicleopard.webelemental.matchers.HasSizeMatcher;
import com.atomicleopard.webelemental.matchers.HasTextMatcher;
import com.atomicleopard.webelemental.matchers.HasValueMatcher;
import com.atomicleopard.webelemental.matchers.IsVisibleMatcher;

/**
 * <p>
 * {@link ElementMatchers} provides static factory methods for producing
 * hamcrest {@link Matcher}s for different {@link Element} properties.
 * </p>
 * <p>
 * As an alternative, consider using an {@link ElementMatcher} obtained from
 * {@link Element#verify()} for a fluent, declarative API.
 * </p>
 */
public final class ElementMatchers {
	ElementMatchers() {
	}

	public static Matcher<Element> id(String string) {
		return id(is(string));
	}

	public static Matcher<Element> id(Matcher<String> matcher) {
		return new HasIdMatcher(matcher);
	}

	public static Matcher<Element> cssClass(String string) {
		return cssClass(Matchers.is(string));
	}

	public static Matcher<Element> cssClass(Matcher<String> matcher) {
		return new HasClassMatcher(matcher);
	}

	public static Matcher<Element> attr(String attribute, String value) {
		return attr(attribute, Matchers.is(value));
	}

	public static Matcher<Element> attr(String attribute, Matcher<String> matcher) {
		return new HasAttributeMatcher(attribute, matcher);
	}

	public static Matcher<Element> value(String string) {
		return value(Matchers.is(string));
	}

	public static Matcher<Element> value(Matcher<String> matcher) {
		return new HasValueMatcher(matcher);
	}

	public static Matcher<Element> size(int size) {
		return size(is(size));
	}

	public static Matcher<Element> size(Matcher<Integer> matcher) {
		return new HasSizeMatcher(matcher);
	}

	public static Matcher<Element> text(String string) {
		return text(Matchers.is(string));
	}

	public static Matcher<Element> text(Matcher<String> matcher) {
		return new HasTextMatcher(matcher);
	}

	public static Matcher<Element> visible() {
		return new IsVisibleMatcher();
	}

	public static Matcher<Element> notVisible() {
		return not(new IsVisibleMatcher());
	}
}
