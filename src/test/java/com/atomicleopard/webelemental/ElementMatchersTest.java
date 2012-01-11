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

import static com.atomicleopard.expressive.Expressive.*;
import static com.atomicleopard.webelemental.ElementMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.webelemental.Element;
import com.atomicleopard.webelemental.ElementMatchers;
import com.atomicleopard.webelemental.matchers.HasAttributeMatcher;
import com.atomicleopard.webelemental.matchers.HasClassMatcher;
import com.atomicleopard.webelemental.matchers.HasIdMatcher;
import com.atomicleopard.webelemental.matchers.HasSizeMatcher;
import com.atomicleopard.webelemental.matchers.HasTextMatcher;
import com.atomicleopard.webelemental.matchers.HasValueMatcher;
import com.atomicleopard.webelemental.matchers.IsVisibleMatcher;

public class ElementMatchersTest {
	private WebElement element1 = ElementTest.webElement("div", "text 1", Expressive.<String, String> map("id", "value1", "class", "myClass yourClass", "data-attr", "data1", "value", "1"));
	private WebElement element2 = ElementTest.webElement("div", "text 2", Expressive.<String, String> map("id", "value2", "class", "otherClass", "data-attr", "data2", "value", "2"));
	private Element element = new Element(list(element1, element2));
	private Description description = new StringDescription();

	@Test
	public void shouldProvideStaticMatcherForAttributeValue() {
		assertThat(attr("data-attr", "value"), is(HasAttributeMatcher.class));

		assertThat(element, attr("data-attr", "data1"));
		assertThat(element, not(attr("data-attr", "data2")));

		attr("data-attr", "data1").describeTo(description);
		assertThat(description.toString(), is("has attribute \"data-attr\" is \"data1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForMatchedAttributeValue() {
		assertThat(attr("data-attr", containsString("value")), is(HasAttributeMatcher.class));

		assertThat(element, attr("data-attr", containsString("data1")));
		assertThat(element, attr("data-attr", containsString("data")));
		assertThat(element, not(attr("data-attr", containsString("data2"))));

		attr("data-attr", containsString("data1")).describeTo(description);
		assertThat(description.toString(), is("has attribute \"data-attr\" a string containing \"data1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForId() {
		assertThat(id("value"), is(HasIdMatcher.class));

		assertThat(element, id("value1"));
		assertThat(element, not(id("value2")));

		id("value1").describeTo(description);
		assertThat(description.toString(), is("has id is \"value1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForMatchedId() {
		assertThat(id(containsString("value")), is(HasIdMatcher.class));

		assertThat(element, id(containsString("value1")));
		assertThat(element, id(containsString("value")));
		assertThat(element, not(id(containsString("value2"))));

		id(containsString("value1")).describeTo(description);
		assertThat(description.toString(), is("has id a string containing \"value1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForCssClass() {
		assertThat(cssClass("value"), is(HasClassMatcher.class));

		assertThat(element, cssClass("yourClass"));
		assertThat(element, not(cssClass("otherClass")));

		cssClass("value1").describeTo(description);
		assertThat(description.toString(), is("has class a collection containing is \"value1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForMatchedCssClass() {
		assertThat(cssClass(containsString("value")), is(HasClassMatcher.class));

		assertThat(element, cssClass(containsString("myClass")));
		assertThat(element, cssClass(containsString("Class")));
		assertThat(element, not(cssClass(containsString("otherClass"))));

		cssClass(containsString("value1")).describeTo(description);
		assertThat(description.toString(), is("has class a collection containing a string containing \"value1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForValue() {
		assertThat(value("value"), is(HasValueMatcher.class));

		assertThat(element, value("1"));
		assertThat(element, not(value("0")));

		value("value1").describeTo(description);
		assertThat(description.toString(), is("has value is \"value1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForMatchedValue() {
		assertThat(value(containsString("value")), is(HasValueMatcher.class));

		assertThat(element, value(containsString("1")));
		assertThat(element, not(value(containsString("0"))));

		value(containsString("value1")).describeTo(description);
		assertThat(description.toString(), is("has value a string containing \"value1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForText() {
		assertThat(text("text"), is(HasTextMatcher.class));

		assertThat(element, text("text 1"));
		assertThat(element, not(text("something else")));

		text("text1").describeTo(description);
		assertThat(description.toString(), is("has text is \"text1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForMatchedText() {
		assertThat(text(containsString("text")), is(HasTextMatcher.class));

		assertThat(element, text(containsString("text")));
		assertThat(element, not(text(containsString("not"))));

		text(containsString("text1")).describeTo(description);
		assertThat(description.toString(), is("has text a string containing \"text1\""));
	}

	@Test
	public void shouldProvideStaticMatcherForSize() {
		assertThat(size(0), is(HasSizeMatcher.class));

		assertThat(element, size(2));
		assertThat(element, not(size(1)));

		size(2).describeTo(description);
		assertThat(description.toString(), is("has size is <2>"));
	}

	@Test
	public void shouldProvideStaticMatcherForMatchedSize() {
		assertThat(size(greaterThan(1)), is(HasSizeMatcher.class));

		assertThat(element, size(greaterThan(1)));
		assertThat(element, not(size(lessThan(1))));

		size(greaterThan(1)).describeTo(description);
		assertThat(description.toString(), is("has size a value greater than <1>"));
	}

	@Test
	public void shouldProvideStaticMatcherForVisible() {
		assertThat(visible(), is(IsVisibleMatcher.class));
		when(element1.isDisplayed()).thenReturn(true);

		assertThat(element, visible());

		visible().describeTo(description);
		assertThat(description.toString(), is("element to be visible"));
	}

	@Test
	public void shouldProvideStaticMatcherFoNotVisible() {
		assertThat(element, notVisible());

		notVisible().describeTo(description);
		assertThat(description.toString(), is("not element to be visible"));
	}

	@Test
	public void shouldHaveANonPublicCtor() {
		assertThat(new ElementMatchers(), is(notNullValue()));
	}
}
