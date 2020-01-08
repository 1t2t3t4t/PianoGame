package Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.midi.*;
import javax.swing.JPanel;

import org.w3c.dom.css.Rect;

public class Key extends JPanel {

	private Color c = new Color(255, 255, 255);
	private int height;
	private int width;
	private final String key;
	private final int note;
	private Synthesizer synt;
	private boolean isPressed = false;

	public Key(String key, int note) {
		this.key = key;
		this.note = note;
		try {
			synt = MidiSystem.getSynthesizer();
			synt.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (key.contains("#")) {
			height = 120;
			width = 30;
			c = c.DARK_GRAY.brighter();
		} else {
			height = 240;
			width = 60;
		}
		this.setPreferredSize(new Dimension(width, height));
	}

	public void keyPressed() {
		c = c.darker();
		this.repaint();
		this.isPressed = true;
		MidiChannel[] channel = synt.getChannels();
		Instrument[] instrument = synt.getDefaultSoundbank().getInstruments();
		synt.loadInstrument(instrument[0]);
		channel[0].noteOn(note, 100);
	}

	public void keyReleased() {
		if (key.contains("#")) {
			c = c.DARK_GRAY.brighter();
		} else {
			c = c.WHITE;
		}
		this.repaint();
		this.isPressed = false;
		MidiChannel[] channel = synt.getChannels();
		channel[0].noteOff(note);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isPressed() {
		return this.isPressed;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(c);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		g.drawString(key, width / 2, height / 2);
	}

	public void autoPlay() throws InterruptedException {
		Graphics g = this.getGraphics();
		c = c.darker();
		MidiChannel[] channel = synt.getChannels();
		Instrument[] instrument = synt.getDefaultSoundbank().getInstruments();
		synt.loadInstrument(instrument[0]);
		channel[0].noteOn(note, 100);

		g.setColor(c);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		g.drawString(key, width / 2, height / 2);

		Thread.sleep(500);

		c = c.brighter();
		channel[0].noteOff(note);

		g.setColor(c);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		g.drawString(key, width / 2, height / 2);

		this.repaint();
	}
}
