/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito;

/**
 * Represents an active thread-local mock of a singleton instance. The same lifecycle caveats of {@link MockedStatic} apply here.
 * <p>
 * Stubbing and verification on the instance can be done using the standard Mockito APIs.
 *
 * @see Mockito#mockSingleton(Object)
 * @param <T> The type of the singleton being mocked.
 */
public interface MockedSingleton<T> extends ScopedMock {

    /**
     * Returns the mocked singleton instance.
     */
    T getInstance();
}
