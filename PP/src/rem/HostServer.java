package rem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


import scn.Host;

public class HostServer extends UnicastRemoteObject implements HostInterface {
	Registry registry; 
	int port = 1729;
	Host host;
	String my_name, address, their_name;
	
	public HostServer(Host host) throws RemoteException {
		super();
		registry = LocateRegistry.createRegistry(port);
		registry.rebind("host_server", this);
		this.host = host;
		this.my_name = host.getPlayerName();
	}
	
	public String getMyName() {
		return this.my_name;
	}
	
	public String getTheirName() {
		return this.their_name;
	}

	@Override
	public String connect(String address, String their_name) throws RemoteException {
		this.address = address;
		this.their_name = their_name;
		try {
			registry.unbind("host_server");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		host.connected(their_name, address);
		return my_name;
	}
}