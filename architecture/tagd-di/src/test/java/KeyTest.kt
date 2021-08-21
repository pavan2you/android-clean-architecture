import io.tagd.core.Service
import io.tagd.di.*
import fake.FakeTypedMapService
import fake.FakeTypedService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class KeyTest {

    @Test
    fun `given two keys with same key's value then verify they are equal`() {
        val key1: Key<Service> = Key("ping")
        val key2: Key<Service> = Key("ping")
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two keys with different key's value then verify they are not equal`() {
        val key1: Key<Service> = Key("ping")
        val key2: Key<Service> = Key("pong")
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two keys in which the second key is derived from first key then verify they are referentially same`() {
        val key1: Key<Service> = Key("ping")
        val key2: Key<Service> = key1

        assert(key1 === key2)
    }

    @Test
    fun `given two inline keys with empty key's value then verify they are same`() {
        val key1: Key<Service> = key()
        val key2: Key<Service> = key()
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with same key's value then verify they are same`() {
        val key1: Key<Service> = key("ping")
        val key2: Key<Service> = key("ping")
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with different key's value then verify they are not same`() {
        val key1: Key<Service> = key("ping")
        val key2: Key<Service> = key("pong")
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two inline keys with same typed class values then verify they are same`() {
        val key1: Key<FakeTypedService<String>> = key(typeOf<String>())
        val key2: Key<FakeTypedService<String>> = key(typeOf<String>())
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with different typed class values then verify they are not same`() {
        val key1: Key<FakeTypedService<String>> = key(typeOf<String>())
        val key2: Key<FakeTypedService<Float>> = key(typeOf<Float>())
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and two parameters then verify they are same`() {
        val key1: Key<FakeTypedService<String>> = key2<FakeTypedService<String>, String>()
        val key2: Key<FakeTypedService<String>> = key2<FakeTypedService<String>, String>()
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and two different parameters then verify they are not same`() {
        val key1: Key<FakeTypedService<String>> = key2<FakeTypedService<String>, String>()
        val key2: Key<FakeTypedService<Float>> = key2<FakeTypedService<Float>, Float>()
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and three parameters then verify they are same`() {
        val key1: Key<FakeTypedMapService<String, Int>> = key3<FakeTypedMapService<String, Int>, String, Int>()
        val key2: Key<FakeTypedMapService<String, Int>> = key3<FakeTypedMapService<String, Int>, String, Int>()
        assert(key1 == key2)
        assert(key1.hashCode() == key2.hashCode())
    }

    @Test
    fun `given two inline keys with typed and three different parameters then verify they are not same`() {
        val key1: Key<FakeTypedMapService<String, Int>> = key3<FakeTypedMapService<String, Int>, String, Int>()
        val key2: Key<FakeTypedMapService<Int, String>> = key3<FakeTypedMapService<Int, String>, Int, String>()
        assert(key1 != key2)
        assert(key1.hashCode() != key2.hashCode())
    }
}