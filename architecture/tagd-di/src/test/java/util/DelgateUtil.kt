package util

import com.nhaarman.mockito_kotlin.mock
import io.tagd.core.Service
import io.tagd.di.*
import kotlin.reflect.KProperty

inline fun <reified T : Service> Any.getInject(
    scope: String = Scope.DEFAULT_SCOPE,
    key: Key<T> = Key(typeOf<T>())
): T {

    val delegateProvider: InjectDelegateProvider<T> = inject(scope, key)
    val kProperty: KProperty<*> = mock()
    val delegate = delegateProvider.provideDelegate(this, kProperty)
    return delegate.getValue(this, kProperty)
}

inline fun <reified T : Service> Any.getInjectX(
    scope: String = Scope.DEFAULT_SCOPE,
    key: Key<T> = Key(typeOf<T>())
): T? {

    val delegateProvider: NullableInjectDelegateProvider<T> = injectX(scope, key)
    val kProperty: KProperty<*> = mock()
    val delegate = delegateProvider.provideDelegate(this, kProperty)
    return delegate.getValue(this, kProperty)
}