//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.eclipse.paho.client.mqttv3.internal;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

/**
 * 反编译修改jar包使用
 */
public class TCPNetworkModule implements NetworkModule {
    private static final String CLASS_NAME = TCPNetworkModule.class.getName();
    private static final Logger log;
    protected Socket socket;
    private SocketFactory factory;
    private String host;
    private int port;
    private int conTimeout;

    static {
        log = LoggerFactory.getLogger("org.eclipse.paho.client.mqttv3.internal.nls.logcat", CLASS_NAME);
    }

    public TCPNetworkModule(SocketFactory factory, String host, int port, String resourceContext) {
        log.setResourceName(resourceContext);
        this.factory = factory;
        this.host = host;
        this.port = port;
    }

    @Override
    public void start() throws IOException, MqttException {
        try {
            log.fine(CLASS_NAME, "start", "252", new Object[]{this.host, new Integer(this.port), new Long((long)(this.conTimeout * 1000))});
            SocketAddress sockaddr = new InetSocketAddress(this.host, this.port);

            if (this.factory instanceof SSLSocketFactory) {

                getIpv4(new onGetIpv4Listener() {
                    @Override
                    public void getIpv4Success(String ip) {
                        try {
                            System.out.println("SSLSocketFactory ip："+ip);
                            Socket tempsocket = new Socket();
                            tempsocket.bind(new InetSocketAddress(ip, 0));
                            tempsocket.connect(sockaddr, conTimeout * 1000);
                            socket = ((SSLSocketFactory)factory).createSocket(tempsocket, host, port, true);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });



            } else {
                getIpv4(new onGetIpv4Listener() {
                    @Override
                    public void getIpv4Success(String ip) {
                        try {
                            System.out.println("ip："+ip);
                            socket = factory.createSocket();
                            socket.bind(new InetSocketAddress(ip, 0));
                            socket.connect(sockaddr, conTimeout * 1000);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        catch (ConnectException var3) {
//            log.fine(CLASS_NAME, "start", "250", (Object[])null, var3);
//            throw new MqttException(32103, var3);
//        }
    }



//    @Override
//    public void start() throws IOException, MqttException {
//        try {
//            log.fine(CLASS_NAME, "start", "252", new Object[]{this.host, new Integer(this.port), new Long((long)(this.conTimeout * 1000))});
//            SocketAddress sockaddr = new InetSocketAddress(this.host, this.port);
//
//            if (this.factory instanceof SSLSocketFactory) {
//                Socket tempsocket = new Socket();
//                tempsocket.bind(new InetSocketAddress("172.18.11.1", 0));
//                tempsocket.connect(sockaddr, this.conTimeout * 1000);
//                this.socket = ((SSLSocketFactory)this.factory).createSocket(tempsocket, this.host, this.port, true);
//
//
//            } else {
//                this.socket = this.factory.createSocket();
//                this.socket.bind(new InetSocketAddress("172.18.11.1", 0));
//                this.socket.connect(sockaddr, this.conTimeout * 1000);
//
//            }
//
//        } catch (ConnectException var3) {
//            log.fine(CLASS_NAME, "start", "250", (Object[])null, var3);
//            throw new MqttException(32103, var3);
//        }
//    }

    /**
     * get ipv4 address
     *
     * @param onGetIpv4Listener
     */
    private void getIpv4(onGetIpv4Listener onGetIpv4Listener) {
        try {
            Process localProcess = Runtime.getRuntime().exec("ifconfig eth0");
            InputStream inputStream = localProcess.getInputStream();
            byte[] bytes = new byte[1024];
            if (inputStream.read(bytes) > 0) {
                String eth0 = new String(bytes);
                if (eth0.contains("inet addr:") && eth0.contains("Bcast")) {
                    int start = eth0.indexOf("inet addr:");
                    int end = eth0.indexOf("Bcast");
                    onGetIpv4Listener.getIpv4Success(eth0.substring(start + "inet addr:".length(), end).trim());
                } else {
                    ;
                }
            } else {
                ;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Process localProcess = Runtime.getRuntime().exec("ifconfig eth0");
//                    InputStream inputStream = localProcess.getInputStream();
//                    byte[] bytes = new byte[1024];
//                    if (inputStream.read(bytes) > 0) {
//                        String eth0 = new String(bytes);
//                        if (eth0.contains("inet addr:") && eth0.contains("Bcast")) {
//                            int start = eth0.indexOf("inet addr:");
//                            int end = eth0.indexOf("Bcast");
//                            onGetIpv4Listener.getIpv4Success(eth0.substring(start + "inet addr:".length(), end).trim());
//                        } else {
//                            ;
//                        }
//                    } else {
//                        ;
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    interface onGetIpv4Listener {
        void getIpv4Success(String ip);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    @Override
    public void stop() throws IOException {
        if (this.socket != null) {
            this.socket.shutdownInput();
            this.socket.close();
        }

    }

    public void setConnectTimeout(int timeout) {
        this.conTimeout = timeout;
    }

    @Override
    public String getServerURI() {
        return "tcp://" + this.host + ":" + this.port;
    }
}
