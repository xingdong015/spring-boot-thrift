package sayhello.util;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class RegistersayHelloRPC implements Watcher {

    private static ZooKeeper zookeeper = null;
    // 扇子锁
    private static CountDownLatch conDown = new CountDownLatch(1);

    public void register() throws IOException {

        zookeeper = new ZooKeeper(ZkConstants.connectString, // l链接地址字符串，可以多个用逗号分割
                3000, // 超时设置
                new RegistersayHelloRPC()// 监听函数
        );

        try {
            conDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            checkServerName();
            createServerHost();
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void createServerHost() throws KeeperException, InterruptedException {
        Stat stat = zookeeper.exists(HelloConstants.RPCNAME + "/" + GetIP.IP() + ":" + HelloConstants.sayHelloPort, false);
        if (stat == null) {
            String path = null;
            try {
                // 这里是临时的节点，会因服务器的宕机而消失
                path = zookeeper.create(HelloConstants.RPCNAME + "/" + GetIP.IP() + ":" + HelloConstants.sayHelloPort,
                        "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                System.out.println(path);
            } catch (KeeperException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    private void checkServerName() throws KeeperException, InterruptedException {
        Stat stat = zookeeper.exists(HelloConstants.RPCNAME, false);


        System.out.println(stat);

        // 没有则创建
        if (stat == null) {
            // 这里是固定的节点
            String path = zookeeper.create(HelloConstants.RPCNAME, "".getBytes(), Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            System.out.println(path);
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState())
            conDown.countDown();

    }

    public static void main(String[] args) throws IOException {

        RegistersayHelloRPC rpc = new RegistersayHelloRPC();
        rpc.register();
    }

}
