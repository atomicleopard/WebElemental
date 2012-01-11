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
import com.atomicleopard.webelemental.matchers.HasSizeMatcher;

public class HasSizeMatcherTest {
	@Test
	public void shouldReturnTrueIfElementHasAttributeMatchingMatcher() {
		WebElement webElement1 = ElementTest.webElement("div", null, Expressive.<String, String> map("id", "myId1"));
		WebElement webElement2 = ElementTest.webElement("div", null, Expressive.<String, String> map("id", "myId2"));
		WebElement webElement3 = ElementTest.webElement("div", null, Expressive.<String, String> map("id", "myId3"));
		Element element = new Element(list(webElement1, webElement2, webElement3));
		assertThat(new HasSizeMatcher(is(3)).matches(element), is(true));
		assertThat(new HasSizeMatcher(is(2)).matches(element), is(false));

		assertThat(new HasSizeMatcher(greaterThan(2)).matches(element), is(true));
		assertThat(new HasSizeMatcher(greaterThan(3)).matches(element), is(false));
	}

	@Test
	public void shouldDescribeTo() {
		Description description = new StringDescription();
		new HasSizeMatcher(is(0)).describeTo(description);
		assertThat(description.toString(), is("has size is <0>"));
	}
}
