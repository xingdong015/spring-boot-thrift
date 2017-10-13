package start;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import sayhello.util.RegistersayHelloRPC;
import sayhello.util.StartsayHello;

import java.io.IOException;

@Component
public class StartWithAPP implements InitializingBean {

    public void afterPropertiesSet() {

        startsayHelloRPC();
        registerHelloRPC();
    }

    private void registerHelloRPC() {
        RegistersayHelloRPC reg = new RegistersayHelloRPC();
        try {
            reg.register();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void startsayHelloRPC() {

        try {
            new Thread(new StartsayHello()).start();
            ;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
