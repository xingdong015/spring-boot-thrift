package sayhello.impl;

import org.apache.thrift.TException;
import sayhello.Hello;
import sayhello.util.GetIP;


public class HelloServiceImpl implements Hello.Iface {

	@Override
	public String helloString(String para) throws TException {
		System.out.println("helloString be calling");
		return "你好:" + para + ",欢迎来到"+ GetIP.IP()+"服务器!";
	}

}
