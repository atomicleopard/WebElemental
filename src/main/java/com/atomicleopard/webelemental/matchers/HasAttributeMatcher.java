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
package com.atomicleopard.webelemental.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import com.atomicleopard.webelemental.Element;

public class HasAttributeMatcher extends BaseMatcher<Element> {

	private final Matcher<String> matcher;
	private String attribute;

	public HasAttributeMatcher(String attribute, Matcher<String> matcher) {
		this.attribute = attribute;
		this.matcher = matcher;
	}

	public boolean matches(Object o) {
		return matcher.matches(((Element) o).attr(attribute));
	}

	public void describeTo(Description description) {
		description.appendText("has attribute \"" + attribute + "\" ");
		matcher.describeTo(description);
	}

}
