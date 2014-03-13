package rem;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MultiplayerServer extends UnicastRemoteObject implements MultiplayerInterface {
	int server_port = 1730;
	String opponent_address;
	Registry registry;
	MultiplayerInterface multiplayer_interface;
	Registry their_registry;

	public MultiplayerServer(String opponent_address, String registry_name) throws RemoteException {
		super();
		registry = LocateRegistry.createRegistry(server_port);
		registry.rebind(registry_name, this);
		this.opponent_address = opponent_address;
	}
	
	public void connect(String registry_name) {
		try {
			their_registry = LocateRegistry.getRegistry(opponent_address, server_port);
			multiplayer_interface = (MultiplayerInterface)(their_registry.lookup(registry_name));
		} catch (NotBoundException | RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sendAircraft() {
		try {
			multiplayer_interface.addAircraft(true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addAircraft(boolean x) {
		
	}
}