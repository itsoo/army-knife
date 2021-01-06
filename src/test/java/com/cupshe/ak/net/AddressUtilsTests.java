package com.cupshe.ak.net;

import org.junit.Test;
import org.springframework.util.Assert;

import java.net.Inet4Address;
import java.util.Optional;

/**
 * AddressUtilsTests
 *
 * @author zxy
 */
public class AddressUtilsTests {

    @Test
    public void testInet4Address() {
        Optional<Inet4Address> op = AddressUtils.getLocalIp4Address();
        Assert.isTrue(op.isPresent(), "Get address error.");
        System.out.println(op.get().getHostAddress());
    }

    @Test
    public void testMachineIndex() {
        int index = AddressUtils.getLocalIp4AddressMod32();
        Assert.isTrue(index >= 0, "Get index error");
        System.out.println(index);
    }
}
