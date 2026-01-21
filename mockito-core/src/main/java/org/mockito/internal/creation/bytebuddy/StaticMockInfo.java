/*
 * Copyright (c) 2026 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.creation.bytebuddy;

import org.mockito.internal.creation.bytebuddy.access.MockMethodInterceptor;

/** Container for properties of a static mock. */
public class StaticMockInfo {

    public final MockMethodInterceptor interceptor;
    public final boolean stubInstanceMethods;

    public StaticMockInfo(MockMethodInterceptor interceptor, boolean stubInstanceMethods) {
        this.interceptor = interceptor;
        this.stubInstanceMethods = stubInstanceMethods;
    }
}
