/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package powermock.modules.test.mockito.junit4.agent;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.exceptions.MethodNotFoundException;
import org.powermock.reflect.exceptions.TooManyMethodsFoundException;
import samples.suppressmethod.SuppressMethod;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

@PrepareForTest(SuppressMethod.class)
public class StubMethodTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

	@Test
	public void whenStubbingInstanceMethodTheMethodReturnsTheStubbedValue() throws Exception {
		String expectedValue = "Hello";
		stub(method(SuppressMethod.class, "getObject")).toReturn(expectedValue);

		SuppressMethod tested = new SuppressMethod();

		assertEquals(expectedValue, tested.getObject());
		assertEquals(expectedValue, tested.getObject());
	}

	@Test
	public void whenStubbingStaticMethodTheMethodReturnsTheStubbedValue() throws Exception {
		String expectedValue = "Hello";
		stub(method(SuppressMethod.class, "getObjectStatic")).toReturn(expectedValue);

		assertEquals(expectedValue, SuppressMethod.getObjectStatic());
		assertEquals(expectedValue, SuppressMethod.getObjectStatic());
	}

	@Test
	public void whenStubbingInstanceMethodWithPrimiteValueTheMethodReturnsTheStubbedValue() throws Exception {
		float expectedValue = 4;
		stub(method(SuppressMethod.class, "getFloat")).toReturn(expectedValue);

		SuppressMethod tested = new SuppressMethod();

		assertEquals(expectedValue, tested.getFloat(), 0.0f);
		assertEquals(expectedValue, tested.getFloat(), 0.0f);
	}

	@Test(expected = TooManyMethodsFoundException.class)
	public void whenSeveralMethodsFoundThenTooManyMethodsFoundExceptionIsThrown() throws Exception {
		stub(method(SuppressMethod.class, "sameName"));
	}

	@Test(expected = MethodNotFoundException.class)
	public void whenNoMethodsFoundThenMethodNotFoundExceptionIsThrown() throws Exception {
		stub(method(SuppressMethod.class, "notFound"));
	}

	@Test
	public void whenStubbingInstanceMethodByPassingTheMethodTheMethodReturnsTheStubbedValue() throws Exception {
		String expected = "Hello";
		stub(method(SuppressMethod.class, "getObject")).toReturn(expected);

		SuppressMethod tested = new SuppressMethod();

		assertEquals(expected, tested.getObject());
		assertEquals(expected, tested.getObject());
	}

	@Test
	public void whenStubbingStaticMethodByPassingTheMethodTheMethodReturnsTheStubbedValue() throws Exception {
		String expected = "Hello";
		stub(method(SuppressMethod.class, "getObjectStatic")).toReturn(expected);

		assertEquals(expected, SuppressMethod.getObjectStatic());
		assertEquals(expected, SuppressMethod.getObjectStatic());
	}

	@Test(expected = ClassCastException.class)
	public void whenStubbingInstanceMethodWithWrongReturnTypeThenClasscastExceptionIsThrown() throws Exception {
		String illegalReturnType = "Hello";
		stub(method(SuppressMethod.class, "getFloat")).toReturn(illegalReturnType);
		SuppressMethod tested = new SuppressMethod();
		tested.getFloat();
	}

	@Test
	public void whenStubbingInstanceMethodToThrowExceptionTheMethodThrowsTheStubbedException() throws Exception {
		Exception expected = new Exception("message");
		stub(method(SuppressMethod.class, "getObject")).toThrow(expected);

		SuppressMethod tested = new SuppressMethod();

		try {
			tested.getObject();
			fail();
		} catch (Exception e) {
			assertEquals("message", e.getMessage());
		}
	}

	@Test
	public void whenStubbingStaticMethodToThrowExceptionTheMethodThrowsTheStubbedException() throws Exception {
		Exception expected = new Exception("message");
		stub(method(SuppressMethod.class, "getObjectStatic")).toThrow(expected);
		try {
			SuppressMethod.getObjectStatic();
			fail();
		} catch (Exception e) {
			assertEquals("message", e.getMessage());
		}
	}

}
