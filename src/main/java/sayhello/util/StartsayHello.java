package sayhello.util;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import sayhello.Hello;
import sayhello.impl.HelloServiceImpl;


public class StartsayHello implements Runnable {

    private static boolean breg = false;

    public void sayHello() throws TTransportException {

        HelloServiceImpl handler = new HelloServiceImpl();
        Hello.Processor processor = new Hello.Processor(handler);

        TServerTransport serverTransport = new TServerSocket(HelloConstants.sayHelloPort);
        // TServer server = new TSimpleServer(new
        // Args(serverTransport).processor(processor));

        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

        System.out.println("Starting the simple server1...");
        breg = true;
        server.serve();
        System.out.println("End the simple server1...");
    }

    @Override
    public void run() {
        try {
            if (!breg)
                sayHello();
        } catch (TTransportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
