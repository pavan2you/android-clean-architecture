import com.nhaarman.mockito_kotlin.mock
import fake.FakeService
import fake.FakeTypedService
import io.tagd.di.*
import util.getInject
import util.getInjectX
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.reflect.KProperty

@RunWith(MockitoJUnitRunner::class)
class DelegateTest {

    @Test
    fun `verify inject provides a valid delegate`() {
        val delegateProvider: InjectDelegateProvider<FakeService> = inject()
        TestCase.assertNotNull(delegateProvider)
    }

    @Test
    fun `given an existing service and default scope then verify inject-inline returns service`() {
        stubFakeServiceInDefaultScope()

        val service: FakeService = getInject()
        TestCase.assertNotNull(service)
    }

    @Test
    fun `given an existing service with default scope then verify injectX-inline returns service`() {
        stubFakeServiceInDefaultScope()

        val service: FakeService? = getInjectX()
        TestCase.assertNotNull(service)
    }

    @Test
    fun `given an existing service and scope then verify inject-inline returns service`() {
        stubFakeService()

        val service: FakeService = getInject("test")
        TestCase.assertNotNull(service)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service with unknown scope as default then verify inject-inline throws IllegalAccessException`() {
        Scope("unknown").apply {
            layer<FakeTypedService<*>> {
                bind(key(), FakeTypedService<String>())
            }
        }

        val ignored: FakeTypedService<String> = getInject("unknown")
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service with unknown scope as default then verify injectX-inline throws IllegalAccessException`() {
        Scope("unknown").apply {
            layer<FakeTypedService<*>> {
                bind(key(), FakeTypedService<String>())
            }
        }

        val ignored: FakeTypedService<String>? = getInjectX("unknown")
    }
    @Test
    fun `given an existing service then verify injectX-inline returns service`() {
        stubFakeService()

        val service: FakeService? = getInjectX("test")
        TestCase.assertNotNull(service)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an unknown service then verify inject-inline returns service`() {
        stubTestScope()
        val ignored: FakeService = getInject("test")
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an unknown service then verify injectX-inline returns service`() {
        stubTestScope()
        val ignored: FakeService? = getInjectX("test")
    }

    @Test
    fun `given an existing service then verify injectX-inline can nullify the value`() {
        stubFakeService()

        val delegateProvider: NullableInjectDelegateProvider<FakeService> = injectX("test")
        val kProperty: KProperty<*> = mock()
        val delegate = delegateProvider.provideDelegate(this, kProperty)

        val service: FakeService? = delegate.getValue(this, kProperty)
        TestCase.assertNotNull(service)
        delegate.setValue(this, kProperty, null)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service when injectX-inline nullifies value then verify accessing it throws IllegalAccessException`() {
        stubFakeService()

        val delegateProvider: NullableInjectDelegateProvider<FakeService> = injectX("test")
        val kProperty: KProperty<*> = mock()
        val delegate = delegateProvider.provideDelegate(this, kProperty)

        val service: FakeService? = delegate.getValue(this, kProperty)
        TestCase.assertNotNull(service)
        delegate.setValue(this, kProperty, null)
        val valueAfterReleased = delegate.getValue(this, kProperty)
        assert(valueAfterReleased == null)
    }

    @Test(expected = IllegalAccessException::class)
    fun `given an existing service then verify injectX-inline throws IllegalAccessException if value modifies`() {
        stubFakeService()

        val delegateProvider: NullableInjectDelegateProvider<FakeService> = injectX("test")
        val kProperty: KProperty<*> = mock()
        val delegate = delegateProvider.provideDelegate(this, kProperty)

        val service: FakeService? = delegate.getValue(this, kProperty)
        TestCase.assertNotNull(service)
        delegate.setValue(this, kProperty, FakeService())
    }

    private fun stubFakeService() {
        scope("test") {
            layer<FakeService> {
                bind(key(), FakeService())
            }
        }
    }

    private fun stubFakeServiceInDefaultScope() {
        Global.locator.layer<FakeService> {
            bind(key(), FakeService())
        }
    }

    private fun stubTestScope() {
        scope("test") {
        }
    }
}