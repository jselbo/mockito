/*
 * Copyright (c) 2024 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.android.internal.creation.inline;

import org.mockito.internal.configuration.plugins.Plugins;
import org.mockito.internal.util.Platform;
import org.mockito.invocation.MockHandler;
import org.mockito.mock.MockCreationSettings;
import org.mockito.plugins.InlineMockMaker;

import static org.mockito.internal.util.StringUtil.join;

/**
 * Entry-point mock maker for Android that delegates to {@link InlineDexmakerMockMaker}.
 * Supports mocking final classes and methods via JVMTI-based inline instrumentation.
 * Requires Android API level 28 (Android P) or higher.
 */
public class InlineAndroidMockMaker implements InlineMockMaker {

    private final InlineDexmakerMockMaker delegate;

    public InlineAndroidMockMaker() {
        if (Platform.isAndroid() || Platform.isAndroidMockMakerRequired()) {
            delegate = new InlineDexmakerMockMaker();
        } else {
            Plugins.getMockitoLogger()
                    .log(
                            join(
                                    "IMPORTANT NOTE FROM MOCKITO:",
                                    "",
                                    "You included the 'mockito-android' dependency in a non-Android environment.",
                                    "The Android mock maker was disabled. You should only include the latter in your 'androidTestCompile' configuration",
                                    "If disabling was a mistake, you can set the 'org.mockito.mock.android' property to 'true' to override this detection.",
                                    "",
                                    "Visit https://javadoc.io/page/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.1 for more information"));
            delegate = null;
        }
    }

    @Override
    public <T> T createMock(MockCreationSettings<T> settings, MockHandler handler) {
        if (delegate == null) {
            throw new IllegalStateException("mockito-android is not supported in non-Android environments");
        }
        return delegate.createMock(settings, handler);
    }

    @Override
    public MockHandler getHandler(Object mock) {
        if (delegate == null) {
            return null;
        }
        return delegate.getHandler(mock);
    }

    @Override
    public void resetMock(Object mock, MockHandler newHandler, MockCreationSettings settings) {
        if (delegate != null) {
            delegate.resetMock(mock, newHandler, settings);
        }
    }

    @Override
    public TypeMockability isTypeMockable(Class<?> type) {
        if (delegate == null) {
            return new TypeMockability() {
                @Override
                public boolean mockable() {
                    return false;
                }

                @Override
                public String nonMockableReason() {
                    return "mockito-android is not supported in non-Android environments";
                }
            };
        }
        return delegate.isTypeMockable(type);
    }

    @Override
    public void clearMock(Object mock) {
        if (delegate != null) {
            delegate.clearMock(mock);
        }
    }

    @Override
    public void clearAllMocks() {
        if (delegate != null) {
            delegate.clearAllMocks();
        }
    }

    @Override
    public void clearAllCaches() {
        if (delegate != null) {
            delegate.clearAllCaches();
        }
    }
}
