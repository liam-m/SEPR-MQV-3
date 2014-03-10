package scn;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.lwjgl.input.Keyboard;

import cls.JoinClient;

import lib.ButtonText;
import lib.jog.graphics;
import lib.jog.input;
import lib.jog.window;
import lib.jog.audio.Sound;
import pp.Main;

public class Join extends Scene {
	
	String this_address = null;
	private String their_address = "";
	
	String player_name;
	String their_name = "";
	
	ButtonText join_button;
	
	JoinClient join_client;
	
	private final int JOIN_X_POSITION = window.getWidth() /2;
	private final int JOIN_Y_POSITION = 800;
	private final int JOIN_WIDTH = 100;
	private final int JOIN_HEIGHT = 25;
	
	public Join(Main main, String player_name) {
		super(main);
		this.player_name = player_name;
		try {
			this_address = (InetAddress.getLocalHost().getHostAddress()).toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		join_client = new JoinClient();
	}
	
	@Override
	public void start() {
		ButtonText.Action join = new ButtonText.Action() {
			@Override
			public void action() {
				their_name = join_client.connect(their_address, this_address, player_name);
				main.closeScene();
				main.setScene(new Multiplayer(main, their_name, player_name));
			}
		};
		join_button = new ButtonText("Join", join, JOIN_X_POSITION, JOIN_Y_POSITION, JOIN_WIDTH, JOIN_HEIGHT);
	}
	@Override
	public void mousePressed(int key, int x, int y) {
		if (join_button.isMouseOver(x, y)) {
			join_button.act();
		}		
	}

	@Override
	public void mouseReleased(int key, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int key) {
		if (their_address.length() > 13) {
			if (key == input.KEY_BACKSPACE)
				their_address = their_address.substring(0, their_address.length()-1);
		} else if ((key >= input.KEY_1 && key <= input.KEY_0) || (key >= input.KEY_7_NP && key <= input.KEY_0_NP && key != 74 && key != 78)) { // Number key
			their_address += Keyboard.getEventCharacter();	
		} else if (key == input.KEY_DECIMAL || key == input.KEY_PERIOD) {
			their_address +='.';
		} else if (key == input.KEY_BACKSPACE) {
			if (their_address.length() > 0)
				their_address = their_address.substring(0, their_address.length()-1);
		} else if (key == input.KEY_RETURN || key == input.KEY_NUMPADENTER) {
			join_button.act();
		} else if (key == input.KEY_ESCAPE) {
			main.closeScene();
		}
	}

	@Override
	public void keyReleased(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(double time_difference) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		graphics.setColour(Main.GREEN);
		graphics.printTextCentred("Welcome!:", window.getWidth() / 2, 100, 5, 100);
		graphics.printTextCentred(player_name, window.getWidth() / 2, 300, 10, 100);
		
		graphics.printTextCentred("Enter IP: ", window.getWidth() / 2, 600, 5, 100);
		graphics.printTextCentred(their_address, window.getWidth() / 2, 700, 5, 100);
		
		graphics.rectangle(false, JOIN_X_POSITION, JOIN_Y_POSITION, JOIN_WIDTH, JOIN_HEIGHT);
		join_button.draw();
	}

	@Override
	public void close() {
	}

	@Override
	public void playSound(Sound sound) {
		// TODO Auto-generated method stub
		
	}

}