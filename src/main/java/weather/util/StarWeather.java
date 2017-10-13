package weather.util;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import weather.Weather;
import weather.impl.GetbaiduWeather;


public class StarWeather implements Runnable {

    private static boolean breg = false;

    public void starWeather() throws TTransportException {

        GetbaiduWeather handler = new GetbaiduWeather();
        Weather.Processor processor = new Weather.Processor(handler);

        TServerTransport serverTransport = new TServerSocket(WeatherConstants.WeahterPort);

        TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

        breg = true;
        server.serve();

    }

    @Override
    public void run() {
        try {
            if (!breg)
                starWeather();
        } catch (TTransportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
