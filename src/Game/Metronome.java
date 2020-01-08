package Game;

import java.awt.Color;
import java.util.List;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;
import javax.sound.midi.MidiDevice.Info;

public class Metronome {
	private Thread play;
	Metronome(){
		
		
		play = new Thread(){
			
			public void run(){
				try {
					Synthesizer synt = MidiSystem.getSynthesizer();
					synt.open();
					MidiChannel[] channel = synt.getChannels();
					Instrument[] instrument = synt.getDefaultSoundbank().getInstruments();
					synt.loadInstrument(instrument[0]);
					while(true){
						try {
							Thread.sleep(750);
							channel[0].noteOn(110, 100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};	
		play.start();
	}
	
	Metronome(Double tempo){
		int time = (int) ((tempo/60.0)*1000);
		play = new Thread(){
			
			public void run(){
				try {
					Synthesizer synt = MidiSystem.getSynthesizer();
					synt.open();
					MidiChannel[] channel = synt.getChannels();
					Instrument[] instrument = synt.getDefaultSoundbank().getInstruments();
					synt.loadInstrument(instrument[0]);	
					while(true){
						try {
							Thread.sleep(time);
							channel[0].noteOn(110, 100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};	
		play.start();
	}

	public static void main(String[]args){
		new Metronome();
		System.out.println("FFF");
	}
}
