package com.cupshe.ak;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;

/**
 * AddressUtils
 *
 * @author zxy
 */
public class AddressUtils {

    /**
     * 根据机器 IP 计算应在集群中的坐标
     *
     * @return int 0~31
     */
    public static int getLocalIp4AddressMod32() {
        return getLocalIp4Address()
                .map(t -> Math.abs(t.getHostAddress().hashCode()) % 32)
                .orElse(-1);
    }

    /**
     * 获取机器 IP 地址
     *
     * @return Optional Inet4Address
     */
    public static Optional<Inet4Address> getLocalIp4Address() {
        try {
            List<Inet4Address> ni = getLocalIp4AddressFromNetworkInterface();
            if (ni.size() != 1) {
                Optional<Inet4Address> socket = getIpBySocket();
                if (socket.isPresent()) {
                    return socket;
                }

                return ni.isEmpty() ? Optional.empty() : Optional.of(ni.get(0));
            }

            return Optional.of(ni.get(0));
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
        List<Inet4Address> addresses = new ArrayList<>(1);
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        if (nis == null) {
            return addresses;
        }

        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            if (!isValidInterface(ni)) {
                continue;
            }

            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (isValidAddress(ia)) {
                    addresses.add((Inet4Address) ia);
                }
            }
        }

        return addresses;
    }

    private static Optional<Inet4Address> getIpBySocket() throws SocketException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            if (socket.getLocalAddress() instanceof Inet4Address) {
                return Optional.of((Inet4Address) socket.getLocalAddress());
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    /**
     * 过滤回环网卡、点对点网卡、非活动网卡、虚拟网卡并要求网卡名字是 eth 或 ens 开头
     *
     * @param ni 网卡
     * @return boolean
     */
    private static boolean isValidInterface(NetworkInterface ni) throws SocketException {
        return !ni.isLoopback() && !ni.isPointToPoint() && ni.isUp() && !ni.isVirtual()
                && (ni.getName().startsWith("eth") || ni.getName().startsWith("ens"));
    }

    /**
     * 判断是否是 IPv4 并且内网地址并过滤回环地址
     *
     * @param address InetAddress
     * @return boolean
     */
    private static boolean isValidAddress(InetAddress address) {
        return address instanceof Inet4Address && address.isSiteLocalAddress() && !address.isLoopbackAddress();
    }
}
