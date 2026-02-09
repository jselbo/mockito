package org.mockitousage.androidtest

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

/**
 * Instrumented tests verifying that final (closed) classes can be mocked
 * using the inline dexmaker mock maker (JVMTI-based).
 */
@RunWith(AndroidJUnit4::class)
class FinalClassInstrumentedTests {

    private var closeable: AutoCloseable? = null

    @Mock private lateinit var mockedViaAnnotationClosedClass: BasicClosedClass

    @Before
    fun setup() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    @Throws(Exception::class)
    fun releaseMocks() {
        closeable?.close()
    }

    @Test
    fun mockFinalClassUsingAnnotation() {
        val receiver = BasicClosedClassReceiver(mockedViaAnnotationClosedClass)
        receiver.callDependencyMethod()
        verify(mockedViaAnnotationClosedClass).emptyMethod()
    }

    @Test
    fun mockFinalClassUsingLocalMock() {
        val closedClass = mock(BasicClosedClass::class.java)
        val receiver = BasicClosedClassReceiver(closedClass)
        receiver.callDependencyMethod()
        verify(closedClass).emptyMethod()
    }

    @Test
    fun stubFinalClassMethod() {
        val closedClass = mock(BasicClosedClass::class.java)
        `when`(closedClass.emptyMethod()).then { /* custom behavior */ }
        closedClass.emptyMethod()
        verify(closedClass).emptyMethod()
    }
}
