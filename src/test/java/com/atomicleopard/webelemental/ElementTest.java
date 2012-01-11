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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.atomicleopard.expressive.Cast;
import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.webelemental.Element;
import com.atomicleopard.webelemental.ElementMatcher;

public class ElementTest {

	private List<WebElement> elements;
	private WebElement element1;
	private WebElement element2;
	private WebElement element3;
	private WebElement element4;
	private Element emptyElement = new Element(Collections.<WebElement> emptyList());

	@Before
	public void before() {
		element1 = webElement("h1", "Heading", Expressive.<String, String> map("id", "h1", "class", "myClass otherClass", "index", "0", "value", "A"));
		element2 = webElement("p", "paragraph", Expressive.<String, String> map("id", "p", "class", "myClass", "index", "1", "value", "B"));
		element3 = webElement("li", "list", Expressive.<String, String> map("id", "list", "class", "myClass", "index", "2", "value", "C"));
		element4 = webElement("div", "just a div", Expressive.<String, String> map("id", "div", "class", "myClass", "index", "3", "val", "D"));

		elements = list(element1, element2, element3, element4);
	}

	@Test
	public void shouldWrapAListOfWebElements() {
		Element element = new Element(elements);
		assertThat(element.get(0).id(), is("h1"));
		assertThat(element.get(1).id(), is("p"));
		assertThat(element.get(2).id(), is("list"));
		assertThat(element.get(3).id(), is("div"));
		assertThat(element.get(4).isEmpty(), is(true));
	}

	@Test
	public void shouldWrapASingleElement() {
		Element element = new Element(element2);
		assertThat(element.get(0).id(), is("p"));
		assertThat(element.get(1).isEmpty(), is(true));
	}

	@Test
	public void shouldClickAllContainedWebElementsAndReturnSelfReference() {
		Element element = new Element(elements);
		assertThat(element.click(), is(element));
		verify(element1).click();
		verify(element2).click();
		verify(element3).click();
		verify(element4).click();
	}

	@Test
	public void shouldClickWithoutFailingAndReturnSelfReference() {
		assertThat(emptyElement.click(), is(sameInstance(emptyElement)));
	}

	@Test
	public void shouldReturnAttributeValueFromFirstWebElement() {
		Element element = new Element(elements);
		assertThat(element.attr("index"), is("0"));
	}

	@Test
	public void shouldReturnAttributeValueNullWhenNoWebElements() {
		assertThat(emptyElement.attr("index"), is(nullValue()));
	}

	@Test
	public void shouldReturnValueFromFirstWebElement() {
		Element element = new Element(elements);
		assertThat(element.val(), is("A"));
	}

	@Test
	public void shouldReturnValueNullWhenNoWebElements() {
		assertThat(emptyElement.val(), is(nullValue()));
	}

	@Test
	public void shouldReturnIdFromFirstWebElement() {
		Element element = new Element(elements);
		assertThat(element.id(), is("h1"));
	}

	@Test
	public void shouldReturnIdNullWhenNoWebElements() {
		assertThat(emptyElement.id(), is(nullValue()));
	}

	@Test
	public void shouldReturnHtmlOfFirstWebElement() {
		Element element = new Element(elements);
		assertThat(element.html(), is("Heading"));
	}

	@Test
	public void shouldReturnNullHtmlWhenNoWebElement() {
		assertThat(emptyElement.html(), is(nullValue()));
	}

	@Test
	public void shouldReturnClassesOfFirstElement() {
		Element element = new Element(elements);
		assertThat(element.classes(), is(list("myClass", "otherClass")));
	}

	@Test
	public void shouldReturnClassesAsEmptyCollectionWhenNoWebElements() {
		assertThat(emptyElement.classes(), is(Collections.<String> emptyList()));
	}

	@Test
	public void shouldReturnTextOfFirstWebElement() {
		Element element = new Element(elements);
		assertThat(element.text(), is("Heading"));
	}

	@Test
	public void shouldReturnNullTextWhenNoWebElement() {
		assertThat(emptyElement.text(), is(nullValue()));
	}

	@Test
	public void shouldSetTextBySendingKeysOnAllWebElementAndReturningSelfReference() {
		Element element = new Element(elements);
		assertThat(element.text("New Value"), is(sameInstance(element)));
		verify(element1).clear();
		verify(element1).sendKeys("New Value");

		verify(element2).clear();
		verify(element2).sendKeys("New Value");

		verify(element3).clear();
		verify(element3).sendKeys("New Value");

		verify(element4).clear();
		verify(element4).sendKeys("New Value");
	}

	@Test
	public void shouldSetValBySendingKeysOnAllWebElementAndReturningSelfReference() {
		Element element = new Element(elements);
		assertThat(element.val("New Value"), is(sameInstance(element)));
		verify(element1).clear();
		verify(element1).sendKeys("New Value");

		verify(element2).clear();
		verify(element2).sendKeys("New Value");

		verify(element3).clear();
		verify(element3).sendKeys("New Value");

		verify(element4).clear();
		verify(element4).sendKeys("New Value");
	}

	@Test
	public void shouldReturnSizeOfContainedWebElements() {
		assertThat(new Element(elements).size(), is(4));
		assertThat(new Element(list(element2)).size(), is(1));
		assertThat(emptyElement.size(), is(0));
	}

	@Test
	public void shouldReturnTrueForIsEmptyWhenNoContainedWebElements() {
		assertThat(new Element(elements).isEmpty(), is(false));
		assertThat(new Element(list(element2)).isEmpty(), is(false));
		assertThat(emptyElement.isEmpty(), is(true));
		assertThat(new Element((WebElement) null).isEmpty(), is(true));
	}

	@Test
	public void shouldReturnTrueForIsVisibleIfNotEmptyAndFirstElementIsDisplayed() {
		assertThat(emptyElement.isVisible(), is(false));
		assertThat(new Element(elements).isVisible(), is(false));
		when(element2.isDisplayed()).thenReturn(true);
		assertThat(new Element(elements).isVisible(), is(false));
		when(element1.isDisplayed()).thenReturn(true);
		assertThat(new Element(elements).isVisible(), is(true));
	}

	@Test
	public void shouldFindParentElementsByXpathingUpwardsForEachWebElement() {
		WebElement parent1 = webElement("div", null, Expressive.<String, String> map("id", "div"));
		WebElement parent2 = webElement("p", null, Expressive.<String, String> map("id", "p"));
		WebElement parent3 = webElement("li", null, Expressive.<String, String> map("id", "li"));
		when(element1.findElements(Mockito.any(By.class))).thenReturn(list(parent1));
		when(element2.findElements(Mockito.any(By.class))).thenReturn(list(parent1));
		when(element3.findElements(Mockito.any(By.class))).thenReturn(list(parent2));
		when(element4.findElements(Mockito.any(By.class))).thenReturn(list(parent3));

		Element parentElement = new Element(elements).parent();
		assertThat(parentElement.get(0).id(), is("div"));
		assertThat(parentElement.get(1).id(), is("div"));
		assertThat(parentElement.get(2).id(), is("p"));
		assertThat(parentElement.get(3).id(), is("li"));
	}

	@Test
	public void shouldFindUsingCssSelector() {
		WebElement child1 = webElement("div", null, Expressive.<String, String> map("id", "div"));
		WebElement child2 = webElement("p", null, Expressive.<String, String> map("id", "p"));

		when(element2.findElements(Mockito.any(By.class))).thenReturn(list(child1, child2));
		Element element = spy(new Element(element2));
		Element result = element.find(".selector");
		assertThat(result.get(0).id(), is("div"));
		assertThat(result.get(1).id(), is("p"));

		verify(element, times(1)).find(By.cssSelector(".selector"));
	}

	@Test
	public void shouldFindUsingGivenSelector() {
		WebElement child1 = webElement("div", null, Expressive.<String, String> map("id", "div"));

		when(element2.findElements(Mockito.any(By.class))).thenReturn(list(child1));
		Element element = spy(new Element(element2));
		Element result = element.find(By.id("id"));
		assertThat(result.get(0).id(), is("div"));
		assertThat(result.get(1).isEmpty(), is(true));
	}

	@Test
	public void shouldImplementIterableIteratingOverContainedElements() {
		Element element = new Element(elements);
		assertThat(Cast.is(element, Iterable.class), is(true));
		Iterator<Element> iterator = element.iterator();
		assertThat(iterator.next().id(), is("h1"));
		assertThat(iterator.next().id(), is("p"));
		assertThat(iterator.next().id(), is("list"));
		assertThat(iterator.next().id(), is("div"));
		assertThat(iterator.hasNext(), is(false));

		assertThat(emptyElement.iterator().hasNext(), is(false));
	}

	@Test
	public void shouldReturnLastElementOrEmptyElementIfNoElements() {
		Element element = new Element(elements);
		assertThat(element.last().id(), is("div"));
		assertThat(emptyElement.last().isEmpty(), is(true));
	}

	@Test
	public void shouldReturnFirstElementOrEmptyElementIfNoElements() {
		Element element = new Element(elements);
		assertThat(element.first().id(), is("h1"));
		assertThat(emptyElement.first().isEmpty(), is(true));
	}

	@Test
	public void shouldReturnAnElementOrEmptyElementIfNoElements() {
		Element element = new Element(elements);
		assertThat(element.any().isEmpty(), is(false));
		assertThat(element.any().id(), is(notNullValue()));

		assertThat(emptyElement.any().isEmpty(), is(true));
	}

	@Test
	public void shouldTransformEachElementUsingThegivenTransformer() {
		Element element = new Element(elements);
		EList<String> results = element.each(new ETransformer<Element, String>() {
			public String to(Element from) {
				return from.id();
			}
		});
		assertThat(results, is(list("h1", "p", "list", "div")));
	}

	@Test
	public void shouldReturnAnElementMatcherWhenVerifyIsCalled() {
		Element element = new Element(elements);
		assertThat(element.verify(), is(notNullValue()));
		assertThat(element.verify(), is(ElementMatcher.class));

	}

	@Test
	public void shouldHaveASensibleToString() {
		assertThat(emptyElement.toString(), is("None"));
		assertThat(new Element(element1).toString(), is("<h1 id='h1' class='myClass otherClass'/>"));
		assertThat(new Element(list(element1, element2)).toString(), is("<h1 id='h1' class='myClass otherClass'/>, <p id='p' class='myClass'/>, "));

	}

	public static WebElement webElement(String element, String text, final Map<String, String> attributes) {
		WebElement webElement = mock(WebElement.class);
		when(webElement.getTagName()).thenReturn(element);
		when(webElement.getText()).thenReturn(text);
		when(webElement.getAttribute(anyString())).thenAnswer(new Answer<String>() {
			public String answer(InvocationOnMock invocation) throws Throwable {
				String key = (String) invocation.getArguments()[0];
				return attributes.get(key);
			}
		});

		return webElement;
	}
}
