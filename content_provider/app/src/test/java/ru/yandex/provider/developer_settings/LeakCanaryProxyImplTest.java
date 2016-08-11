package ru.yandex.provider.developer_settings;

import org.junit.Test;

import ru.yandex.provider.ProviderApp;
import ru.yandex.provider.developer_settings.LeakCanaryProxy;
import ru.yandex.provider.developer_settings.LeakCanaryProxyImpl;

import static org.mockito.Mockito.mock;

public class LeakCanaryProxyImplTest {

    // Unfortunately, we can not really test init method since launching LeakCanary in the tests is not a great idea.

    @Test
    public void watch_shouldNoOpIfInitWasNotCalledCaseWhenLeakCanaryDisabled() {
        LeakCanaryProxy leakCanaryProxy = new LeakCanaryProxyImpl(mock(ProviderApp.class));
        leakCanaryProxy.watch(new Object()); // No exceptions expected.
    }
}