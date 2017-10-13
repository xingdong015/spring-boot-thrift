package weather;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import weather.util.RegisterWeatherRPC;
import weather.util.StarWeather;

import java.io.IOException;

public class StartWeatherWithAPP implements ApplicationListener {

	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {

		startWeatherRPC();
		registerWeatherRPC();
	}

	private void registerWeatherRPC() {
		RegisterWeatherRPC reg = new RegisterWeatherRPC();
		try {
			reg.register();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void startWeatherRPC() {

		try {
			new Thread(new StarWeather()).start();
			;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
