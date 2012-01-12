/**
 * WebElemental is a small library to make writing Selenium tests easier.
 * <p>
 * WebElemental introduces the {@link com.atomicleopard.webelemental.Element} class, which works with an API modeled on jQuery.<br/>
 * This means that web tests can be written in a way that is familiar to those used to CSS selectors and jQuery.
 * </p>
 * <p>
 * There are several key motivations for the creation of this library:</p>
 * <ul>
 * <li>It is often just as necessary to verify the absence of elements as the presence of them - this is something that is challenging with the {@link org.openqa.selenium.WebDriver} interface</li>
 * <li>When using the <a href="http://code.google.com/p/selenium/wiki/PageObjects">Page Object pattern</a> being bound to specific data can often make tests quite fragile. This has led to constructs such as {@link com.atomicleopard.webelemental.Element#any()} and {@link com.atomicleopard.webelemental.Element#first()}</li>
 * <li>Selection of elements is most naturally performed using CSS selectors by Web Developers - Selenium places equal weight on Xpath selection, which experience has shown tends to be more fragile.</li>
 * </ul>
 * <p>So, onwards with some examples:<br/>
 * Firstly, to leverage this library, you need to create some simple way of instantiating new {@link com.atomicleopard.webelemental.Element}s.
 * For example, if you are using the Page Object pattern, you may have a base class with access to the {@link org.openqa.selenium.WebDriver}.
 * In this class you can add:
 * <pre><code>
 * protected Element find(By selector) {
 *     List&lt;WebElement&gt; findElements = getDriver().findElements(selector);
 *     return new Element(findElements);
 * }
 * 
 * protected Element find(String selector) {
 *     return find(By.cssSelector(selector));
 * }
 * </code></pre>
 * Once you have obtained an {@link com.atomicleopard.webelemental.Element}, you can interact with it in the following way:
 * <pre><code>
 * find("#firstName").val("Anthony");
 * find("#actionPublish").click();
 * </code></pre>
 * </p>
 * <p>
 * {@link com.atomicleopard.webelemental.Element} wraps a group of {@link org.openqa.selenium.WebElement}s, that is, it isn't necessarily
 * a single element on the web page, but is all the elements that matched the selection criteria.
 * What this means is that when you interact with an element, you interact generally with all selected elements.
 * This allows us to do things like this:
 * <pre><code>
 * find(".entry").click(); // click everything
 * find(".entry").get(1).click(); // click the second item
 * find("input").any().text("Updated"); // update the text of one of the inputs, it doesnt matter which
 * </code></pre>
 * </p>
 * <p>
 * When we get down to it, Selenium tests have limited value without assertions. 
 * Experience has shown that the audience for web tests is usually wider and less specifically skilled in java than it would be
 * for unit tests. 
 * JUnit tests, particularly with Hamcrest matchers, have a conceptual overhead which can limit the audience. 
 * To help remedy this, to support the Element class this library supplies the {@link com.atomicleopard.webelemental.ElementMatcher} class.
 * You can obtain an ElementMatcher for an Element by calling the {@link com.atomicleopard.webelemental.Element#verify()} method. 
 * <pre><code>
 * find(".entry").verify().isVisible(); //assert that at least one WebElement matched by the '.entry' selector is visible
 * find("input.username").verify().isPresent().hasId("j_username").hasValue("Anthony@mailinator.com");
 * </code></pre>
 * {@link com.atomicleopard.webelemental.ElementMatcher} methods provide a fluent syntax (or <i>chainable interface</i>) so that many related assertions
 * can be made inline easily.
 * </p>
 * <p>
 * For those of you comfortable using regular JUnit assertions, {@link com.atomicleopard.webelemental.ElementMatchers} provides static factory methods for
 * creating Hamcrest matchers specific to the {@link com.atomicleopard.webelemental.Element} class.
 * </p>
 */
package com.atomicleopard.webelemental;

