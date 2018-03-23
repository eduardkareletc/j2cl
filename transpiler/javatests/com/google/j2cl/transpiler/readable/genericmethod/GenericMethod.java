/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.j2cl.transpiler.readable.genericmethod;

import java.util.List;
import javaemul.internal.annotations.UncheckedCast;

public class GenericMethod<T> {
  public <T, S> void foo(T f, S s) {} // two type parameters, no bounds.

  public void fun(Object o) {}

  public <T extends Exception> void fun(T t) {} // type parameter with bounds.

  public <T extends Error> void fun(T t) { // type parameter with different bounds.
    new GenericMethod<T>() { // inherit method T
      public void fun2(T t) {} // inherit method T

      public <T> void fun2(T t) {} // redefine T
    };

    class LocalClass<T> extends GenericMethod<T> {
      public void fun2(T t) {}

      public <T extends Number> void fun2(T t) {}
    }
    new LocalClass<T>();
  }

  public <T> GenericMethod<T> bar() {
    return null;
  } // return parameterized type.

  public <T> T[] fun(T[] array) { // generic array type
    return array;
  }

  public <T> T checked() {
    return null;
  }

  @UncheckedCast
  public <T> T unchecked() {
    return null;
  }

  public void test() {
    GenericMethod<Number> g = new GenericMethod<>();
    g.foo(g, g); // call generic method without diamond.
    g.<Error, Exception>foo(new Error(), new Exception()); // call generic method with diamond.

    g.fun(new Object());
    g.fun(new Exception());
    g.fun(new Error());
    g.fun(new String[] {"asdf"});

    String s = checked();
    s = unchecked();
  }

  static class SuperContainer<C extends Container<?>> {
    C get() {
      return null;
    }
  }

  static class Container<CT extends Content> {
    CT get() {
      return null;
    }
  }

  static class Content {}

  public static Content testErasureCastOnWildCard() {
    List<Container<?>> list = null;
    return list.get(0).get();
  }

  public static <T extends Content> Content testErasureCastOnBoundedTypeVariable() {
    List<Container<T>> list = null;
    return list.get(0).get();
  }

  public static Content testErasureCastOnWildCardTwoLevels() {
    List<SuperContainer<? extends Container<? extends Content>>> list = null;
    return list.get(0).get().get();
  }

  public static <CT extends Container<C>, C extends Content>
      Content testErasureCastOnBoundedTypeVariableTwoLevels() {
    List<SuperContainer<CT>> list = null;
    return list.get(0).get().get();
  }
}
