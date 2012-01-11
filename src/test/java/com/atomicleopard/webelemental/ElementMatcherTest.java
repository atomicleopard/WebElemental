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
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.webelemental.Element;
import com.atomicleopard.webelemental.ElementMatcher;

/**
 * This test class has a series of pretty strange looking tests for two reasons:
 * <ul>
 * <li>The fluent api (i.e. returning this from each method invocation on
 * {@link ElementMatcher} makes for some weird looking asserts</li>
 * <li>The {@link ElementMatcher} class itself contains assertions, so many of
 * these tests are white-box tests, relying on the code under test failing by
 * the act of being tested if something changes</li>
 * </ul>
 * 
 * @author nick
 * 
 */
public class ElementMatcherTest {
	private WebElement element1 = ElementTest.webElement("div", "text 1", Expressive.<String, String> map("id", "value1", "class", "myClass yourClass", "data-attr", "data1", "value", "1"));
	private WebElement element2 = ElementTest.webElement("div", "text 2", Expressive.<String, String> map("id", "value2", "class", "otherClass", "data-attr", "data2", "value", "2"));
	private Element element = new Element(list(element1, element2));
	private ElementMatcher elementMatcher = new ElementMatcher(element);

	@Test
	public void shouldWrapElementAndMakeAssertionsOnThatElement() {
		assertThat(elementMatcher.using(sameInstance(element)), is(sameInstance(elementMatcher)));
		assertThat(elementMatcher.hasSize(2), is(sameInstance(elementMatcher)));
		assertThat(elementMatcher.hasId("value1"), is(sameInstance(elementMatcher)));

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasSize(1);
			}
		});
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasId("value2");
			}
		});
	}

	@Test
	public void shouldVerifyElementSize() {
		elementMatcher.hasSize(2);
		elementMatcher.hasSize(greaterThan(1));
		elementMatcher.hasSize(lessThan(3));

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasSize(1);
			}
		});
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasSize(not(2));
			}
		});
	}

	@Test
	public void shouldVerifyAttribute() {
		elementMatcher.hasAttribute("data-attr", "data1");
		elementMatcher.hasAttribute("data-attr", Matchers.<String> notNullValue());
		elementMatcher.hasAttribute("data-attr", is("data1"));
		elementMatcher.hasAttribute("data-attr", containsString("data"));

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasAttribute("data-attr", is("data2"));
			}
		});
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasAttribute("data-attr", not(Matchers.<String> notNullValue()));
			}
		});
	}

	@Test
	public void shouldVerifyText() {
		elementMatcher.hasText("text 1");
		elementMatcher.hasText(Matchers.<String> notNullValue());
		elementMatcher.hasText(is("text 1"));
		elementMatcher.hasText(containsString("text"));
		elementMatcher.hasText(not("text 2"));

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasText(is("not this"));
			}
		});
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasText(containsString("nope"));
			}
		});
	}

	@Test
	public void shouldVerifyValue() {
		elementMatcher.hasValue("1");
		elementMatcher.hasValue(Matchers.<String> notNullValue());
		elementMatcher.hasValue(is("1"));
		elementMatcher.hasValue(not("2"));

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasValue(is("not this"));
			}
		});
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasValue(containsString("nope"));
			}
		});
	}

	@Test
	public void shouldVerifyId() {
		elementMatcher.hasId("value1");
		elementMatcher.hasId(Matchers.<String> notNullValue());
		elementMatcher.hasId(is("value1"));
		elementMatcher.hasId(containsString("value"));
		elementMatcher.hasId(not("value2"));

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasId(is("not this"));
			}
		});
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.hasId(containsString("nope"));
			}
		});
	}

	@Test
	public void shouldVerifyIsVisible() {
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.isVisible();
			}
		});
		when(element1.isDisplayed()).thenReturn(true);
		elementMatcher.isVisible();
	}

	@Test
	public void shouldVerifyIsNotVisible() {
		elementMatcher.isNotVisible();
		when(element1.isDisplayed()).thenReturn(true);
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.isNotVisible();
			}
		});
	}

	@Test
	public void shouldVerifyIsPresent() {
		elementMatcher.isPresent();
		Element element = new Element(Collections.<WebElement> emptyList());
		elementMatcher = element.verify();

		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.isPresent();
			}
		});
	}

	@Test
	public void shouldVerifyIsNotPresent() {
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.isNotPresent();
			}
		});

		Element element = new Element(Collections.<WebElement> emptyList());
		elementMatcher = element.verify();
		elementMatcher.isNotPresent();
	}

	@Test
	public void shouldVerifyIsNotEmpty() {
		elementMatcher.isNotEmpty();
		when(element1.getText()).thenReturn("");
		expectAssertionFailure(new Runnable() {
			public void run() {
				elementMatcher.isNotEmpty();
			}
		});
	}

	@Test
	public void shouldTrimStringsToNull() {
		assertThat(elementMatcher.trimToNull(null), is(nullValue()));
		assertThat(elementMatcher.trimToNull(""), is(nullValue()));
		assertThat(elementMatcher.trimToNull(" "), is(nullValue()));
		assertThat(elementMatcher.trimToNull(" \t "), is(nullValue()));
		assertThat(elementMatcher.trimToNull(" \ta\t "), is("a"));
		assertThat(elementMatcher.trimToNull(" a b "), is("a b"));
	}

	private void expectAssertionFailure(Runnable runnable) {
		boolean fail = false;
		try { // sanity check that the above method calls are making assertions
			runnable.run();
			fail = true;
		} catch (AssertionError e) {
			// expected
		}
		if (fail) {
			fail("Expected an ElementMatcher invocation to fail with an AssertionError, but it didn't");
		}
	}
}
