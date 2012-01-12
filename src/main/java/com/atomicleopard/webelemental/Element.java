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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.atomicleopard.expressive.EList;
import com.atomicleopard.expressive.EListImpl;
import com.atomicleopard.expressive.ETransformer;
import com.atomicleopard.expressive.Expressive;
import com.atomicleopard.expressive.transform.CollectionTransformer;
import com.atomicleopard.expressive.transform.ETransformers;

/**
 * <p>
 * {@link Element} wraps the WebElement of Selenium to provide a cleaner and
 * easier to use interface.
 * </p>
 * <p>
 * The Element interface is designed to be similar to the actions available in
 * the jQuery, leveraging both its familiarity to web developers and its simple
 * but powerful interface.
 * </p>
 * <p>
 * An Element wraps zero or more WebElements unifying the interface with which
 * we deal with elements within Selenium tests. The web elements contained
 * within an Element are generally referred to as the selected, or matched,
 * elements.
 * </p>
 * <p>
 * As a general rule, action methods (such as {@link #click()}, {@link #val()}
 * etc) act on all selected elements, while query methods (such as
 * {@link #text()}, {@link #classes()} act on the first selected element.
 * </p>
 */
public class Element implements Iterable<Element> {
	private EList<WebElement> elements;

	public Element(WebElement element) {
		this.elements = new EListImpl<WebElement>();
		if (element != null) {
			elements.add(element);
		}
	}

	public Element(List<WebElement> elements) {
		this.elements = new EListImpl<WebElement>();
		if (elements != null) {
			this.elements.addAll(elements);
		}
	}

	/**
	 * @return the id of the first selected element, null if no id exists, or
	 *         null if no elements are selected
	 */
	public String id() {
		return attr("id");
	}

	/**
	 * @return a list of the classes (as in CSS classes) on the first selected
	 *         element, or an empty list if no elements are selected or no
	 *         classes exist
	 */
	public EList<String> classes() {
		String classes = attr("class");
		return classes == null ? Expressive.<String> list() : list(classes.split("\\s"));
	}

	/**
	 * @return the value of the first selected element, null if no value exists,
	 *         or null if no elements are selected
	 */
	public String val() {
		return attr("value");
	}

	/**
	 * Returns the value of the named attribute on the first selected element.
	 * 
	 * @param name
	 *            the name of the attribute to return
	 * @return the value of the named attribute on the first selected element,
	 *         null if the attribute does not exist, or null if no elements are
	 *         selected
	 */
	public String attr(String name) {
		return elements.isEmpty() ? null : elements.first().getAttribute(name);
	}

	/**
	 * Sets the value of all selected elements to the given value
	 * 
	 * @param value
	 *            the value to assign to all selected elements
	 * @return this Element, supporting a fluid API
	 */
	public Element val(CharSequence value) {
		for (WebElement element : elements) {
			element.clear();
			element.sendKeys(value);
		}
		return this;
	}

	/**
	 * Clicks all selected elements
	 * 
	 * @return this Element, supporting a fluid API
	 */
	public Element click() {
		for (WebElement element : elements) {
			element.click();
		}
		return this;
	}

	/**
	 * Returns the html of the first selected element, or null if no elements are
	 * selected. This is usually synonymous with innerHtml.
	 * 
	 * @return the html of the first selected element, or null if no elements are
	 *         selected
	 * @see WebElement#getText()
	 */
	public String html() {
		return elements.isEmpty() ? null : elements.first().getText();
	}

	/**
	 * Returns the text of the first selected element, or null if no elements are
	 * selected.
	 * 
	 * @return the text of the first selected element, or null if no elements are
	 *         selected
	 * @see WebElement#getText()
	 */
	public String text() {
		return elements.isEmpty() ? null : elements.first().getText();
	}

	/**
	 * Sets the text of all selected elements to the given value
	 * 
	 * @param value
	 *            the text value to write on all selected elements
	 * @return this Element, supporting a fluid API
	 */
	public Element text(CharSequence value) {
		for (WebElement element : elements) {
			element.clear();
			element.sendKeys(value);
		}
		return this;
	}

	/**
	 * @return the number of selected elements
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * @return true is there are no selected elements within this
	 *         {@linkplain Element}
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * @return true if the first selected element is visible, false if no
	 *         elements are selected or the first matching element is not visible
	 */
	public boolean isVisible() {
		return elements.isEmpty() ? false : elements.first().isDisplayed();
	}

	/**
	 * @return an Element containing the parent element of every selected element
	 *         within this Element
	 */
	public Element parent() {
		EList<WebElement> list = new EListImpl<WebElement>();
		for (WebElement element : elements) {
			list.addAll(element.findElements(By.xpath("..")));
		}
		return new Element(list);
	}

	/**
	 * Selects all elements matching the given CSS selector that are children of
	 * the selected elements within this Element.
	 * 
	 * @param selector
	 *            specified as a CSS, or jQuery style, selector
	 * @return an Element containing all elements matching the given selector
	 */
	public Element find(String selector) {
		return find(By.cssSelector(selector));
	}

	/**
	 * Selects all elements matching the given selector that are children of the
	 * selected elements within this Element.
	 * 
	 * @param selector
	 * @return an Element containing all elements matching the given selector
	 */
	public Element find(By selector) {
		EList<WebElement> list = new EListImpl<WebElement>();
		for (WebElement webElement : elements) {
			list.addAll(webElement.findElements(selector));
		}
		return new Element(list);
	}

	/**
	 * @return an {@link Iterator} over all selected {@link Element}s
	 */
	public Iterator<Element> iterator() {
		EList<Element> list = new EListImpl<Element>();
		for (WebElement webElement : elements) {
			list.add(new Element(webElement));
		}
		return list.iterator();
	}

	/**
	 * @return the first selected element, the returned Element will be empty if
	 *         this Element has no selected elements
	 */
	public Element first() {
		return new Element(elements.first());
	}

	/**
	 * Return the selected element at the given index. The returned Element will
	 * be empty if this Element has no selected element at the given index
	 * 
	 * @param index
	 * @return the selected element at the given index, returned Element will be
	 *         empty if this Element has no selected element at the given index
	 */
	public Element get(int index) {
		return new Element(elements.at(index));
	}

	/**
	 * @return the last selected element, the returned Element will be empty if
	 *         this Element has no selected elements
	 */
	public Element last() {
		return new Element(elements.last());
	}

	/**
	 * @return a random selected element from all the selected elements, the
	 *         returned Element will be empty if this Element has no selected
	 *         elements
	 */
	public Element any() {
		EList<WebElement> duplicate = elements.duplicate();
		Collections.shuffle(duplicate);
		return new Element(duplicate.first());
	}

	/**
	 * <p>
	 * Performs the given transformation on all the selected elements within this
	 * {@link Element}.
	 * </p>
	 * <p>
	 * This can be useful for extracting data from this Element, or performing a
	 * series of operations.<br/>
	 * Example - return all the ids of the selected elements:
	 * 
	 * <pre>
	 * <code>
	 * EList&lt;String&gt; ids = element.each(new ETransformer&lt;Element, String&gt;() {
	 * 	public String to(Element from) {
	 * 		return from.id();
	 * 	}
	 * });
	 * </code>
	 * </pre>
	 * 
	 * Example - set the value of all the selected elements to something unique:
	 * 
	 * <pre>
	 * <code>
	 * element.each(new ETransformer&lt;Element, Void&gt;() {
	 * 	private int unique = 1;
	 * 	public Void to(Element from) {
	 * 		from.val("Unique"+ (unique++));
	 * 		return null;
	 * 	}
	 * });
	 * </code>
	 * </pre>
	 * 
	 * Example - advanced, extract data from a table:
	 * 
	 * <pre>
	 * <code>
	 * Element table = ...
	 * Element tableRows = table.find("tbody tr");
	 * EList&lt;List&lt;String&gt;&gt; tableContents = tableRows.each(new ETransformer&lt;Element, List&lt;String&gt;&gt;() {
	 * 	public List&lt;String&gt; to(Element from) {
	 * 		List&lt;String&gt; cellContent = from.find("td").each(ETransformers.&lt;Element, String&gt; toProperty("text", Element.class));
	 * 		return cellContent;
	 * 	}
	 * });
	 * </code>
	 * </pre>
	 * 
	 * The {@link ETransformer} interface enables powerful transformations, and
	 * is worth investigating. To help you write succinct code, many common
	 * operations are available statically using {@link ETransformers}
	 * </p>
	 * 
	 * @param eTransformer
	 *            the transformation to apply to all selected elements
	 * @return the result of applying the given transformation across each
	 *         selected element
	 */
	public <T> EList<T> each(ETransformer<Element, T> eTransformer) {
		return new CollectionTransformer<Element, T>(eTransformer).to(this);
	}

	/**
	 * Returns an {@link ElementMatcher}, which allows assertions and
	 * verifications to be applied to the set of selected elements using a fluid,
	 * declarative API.
	 * 
	 * @return an {@link ElementMatcher} for all the selected elements
	 */
	public ElementMatcher verify() {
		return new ElementMatcher(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = elements.size();
		if (size > 1) {
			for (Element element : this) {
				sb.append(element);
				sb.append(", ");
			}
		} else if (size == 0) {
			sb.append("None");
		} else {
			sb.append("<");
			sb.append(elements.first().getTagName());
			sb.append(String.format(" id='%s'", id()));
			sb.append(String.format(" class='%s'", attr("class")));
			sb.append("/>");
		}
		return sb.toString();
	}
}
