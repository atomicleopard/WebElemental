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

import static com.atomicleopard.expressive.Expressive.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.webelemental.Element;
import com.atomicleopard.webelemental.ElementTest;
import com.atomicleopard.webelemental.matchers.HasClassMatcher;

public class HasClassMatcherTest {
	@Test
	public void shouldReturnTrueIfElementHasAttributeMatchingMatcher() {
		WebElement element1 = ElementTest.webElement("div", null, Expressive.<String, String> map("class", "myClass yourClass"));
		WebElement element2 = ElementTest.webElement("div", null, Expressive.<String, String> map("class", "otherClass"));
		Element element = new Element(list(element1, element2));
		assertThat(new HasClassMatcher(is("yourClass")).matches(element), is(true));
		assertThat(new HasClassMatcher(is("otherClass")).matches(element), is(false));

		assertThat(new HasClassMatcher(containsString("Class")).matches(element), is(true));
		assertThat(new HasClassMatcher(containsString("notClass")).matches(element), is(false));
	}

	@Test
	public void shouldDescribeTo() {
		Description description = new StringDescription();
		new HasClassMatcher(is("whatever")).describeTo(description);
		assertThat(description.toString(), containsString("has class "));
	}
}
