package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JPanel;

public class Key extends JPanel {

	final String key;
	final int note;

	private Color color = new Color(255, 255, 255);
	private int height;
	private int width;
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
			color = color.DARK_GRAY.brighter();
		} else {
			height = 240;
			width = 60;
		}
		this.setPreferredSize(new Dimension(width, height));
	}

	public void keyPressed() {
		color = color.darker();
		this.repaint();
		this.isPressed = true;
		MidiChannel[] channel = synt.getChannels();
		Instrument[] instrument = synt.getDefaultSoundbank().getInstruments();
		synt.loadInstrument(instrument[0]);
		channel[0].noteOn(note, 100);
	}

	public void keyReleased() {
		if (key.contains("#")) {
			color = color.DARK_GRAY.brighter();
		} else {
			color = color.WHITE;
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
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		g.drawString(key, width / 2, height / 2);
	}

	public void autoPlay() throws InterruptedException {
		Graphics g = this.getGraphics();
		color = color.darker();
		MidiChannel[] channel = synt.getChannels();
		Instrument[] instrument = synt.getDefaultSoundbank().getInstruments();
		synt.loadInstrument(instrument[0]);
		channel[0].noteOn(note, 100);

		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		g.drawString(key, width / 2, height / 2);

		Thread.sleep(500);

		color = color.brighter();
		channel[0].noteOff(note);

		g.setColor(color);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		g.drawString(key, width / 2, height / 2);

		this.repaint();
	}
}
