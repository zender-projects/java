package redis;

import sun.management.VMManagement;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
public class KeyGeneratory {


    public static String getMac() throws Exception{
        InetAddress ia = InetAddress.getLocalHost();
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        return sb.toString().toUpperCase();//.replaceAll("-", "");

    }

    public static Integer getJvmPid() throws Exception {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        Field jvm = runtime.getClass().getDeclaredField("jvm");
        jvm.setAccessible(true);
        VMManagement mgmt = (VMManagement) jvm.get(runtime);
        Method pidMethod = mgmt.getClass().getDeclaredMethod("getProcessId");
        pidMethod.setAccessible(true);
        int pid = (Integer) pidMethod.invoke(mgmt);
        return pid;
    }

    public static Long getThreadId() {
        return Thread.currentThread().getId();
    }

    public static String getRequireId() {
        try {
            return getMac() + ":" + getJvmPid() + ":" + getThreadId();
        }catch (Exception ex) {
            throw new RuntimeException("获取required id失败");
        }
    }

    public static void main(String[] args) throws Exception{
        System.out.println(getMac());
        System.out.println(getJvmPid());
        System.out.println(getThreadId());
    }
}
