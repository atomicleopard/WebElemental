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

	public String id() {
		return attr("id");
	}

	public EList<String> classes() {
		String classes = attr("class");
		return classes == null ? Expressive.<String> list() : list(classes.split("\\s"));
	}

	public String val() {
		return attr("value");
	}

	public String attr(String name) {
		return elements.isEmpty() ? null : elements.first().getAttribute(name);
	}

	public Element val(CharSequence value) {
		for (WebElement element : elements) {
			element.clear();
			element.sendKeys(value);
		}
		return this;
	}

	public Element click() {
		for (WebElement element : elements) {
			element.click();
		}
		return this;
	}

	public String html() {
		return elements.isEmpty() ? null : elements.first().getText();
	}

	public String text() {
		return elements.isEmpty() ? null : elements.first().getText();
	}

	public Element text(CharSequence value) {
		for (WebElement element : elements) {
			element.clear();
			element.sendKeys(value);
		}
		return this;
	}

	public int size() {
		return elements.size();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public boolean isVisible() {
		return elements.isEmpty() ? false : elements.first().isDisplayed();
	}

	public Element parent() {
		EList<WebElement> list = new EListImpl<WebElement>();
		for (WebElement element : elements) {
			list.addAll(element.findElements(By.xpath("..")));
		}
		return new Element(list);
	}

	public Element find(String selector) {
		return find(By.cssSelector(selector));
	}

	public Element find(By selector) {
		EList<WebElement> list = new EListImpl<WebElement>();
		for (WebElement webElement : elements) {
			list.addAll(webElement.findElements(selector));
		}
		return new Element(list);
	}

	public Iterator<Element> iterator() {
		EList<Element> list = new EListImpl<Element>();
		for (WebElement webElement : elements) {
			list.add(new Element(webElement));
		}
		return list.iterator();
	}

	public Element first() {
		return new Element(elements.first());
	}

	/**
	 * Return the element at the specified index
	 * 
	 * @param index
	 * @return
	 */
	public Element get(int index) {
		return new Element(elements.at(index));
	}

	public Element last() {
		return new Element(elements.last());
	}

	public Element any() {
		EList<WebElement> duplicate = elements.duplicate();
		Collections.shuffle(duplicate);
		return new Element(duplicate.first());
	}

	public <T> EList<T> each(ETransformer<Element, T> eTransformer) {
		return new CollectionTransformer<Element, T>(eTransformer).to(this);
	}

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
