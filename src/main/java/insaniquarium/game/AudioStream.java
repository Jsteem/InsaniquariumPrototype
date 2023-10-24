package insaniquarium.game;

import insaniquarium.Main;
import jouvieje.bass.structures.HSTREAM;

import java.io.File;
import java.util.Objects;

import static jouvieje.bass.Bass.*;
import static jouvieje.bass.defines.BASS_ACTIVE.BASS_ACTIVE_PLAYING;
import static jouvieje.bass.defines.BASS_ATTRIB.*;
import static jouvieje.bass.defines.BASS_POS.BASS_POS_BYTE;
import static jouvieje.bass.defines.BASS_SAMPLE.BASS_SAMPLE_FLOAT;
import static jouvieje.bass.defines.BASS_SAMPLE.BASS_SAMPLE_LOOP;

public class AudioStream implements Runnable {

    private boolean isLooping;

    private HSTREAM stream;

    String fileName;
    public AudioStream(String fileName) {
        this.fileName = fileName;
    }

    public boolean isPlaying() {
        return (stream != null) && (BASS_ChannelIsActive(stream.asInt()) == BASS_ACTIVE_PLAYING);
    }

    public void play() {
        if (isPlaying()) {
            stop();
        }
        else{
            // Create the stream

            stream = BASS_StreamCreateFile(false, fileName, 0, 0, 0);

            if (isLooping) {
                BASS_ChannelFlags(stream.asInt(), BASS_SAMPLE_LOOP, BASS_SAMPLE_LOOP);
            }

            // Play the stream
            BASS_ChannelPlay(stream.asInt(), false);
        }


    }

    public void stop() {
        BASS_ChannelStop(stream.asInt());
    }

    public void pause() {
        BASS_ChannelPause(stream.asInt());
    }

    public void resume() {
        BASS_ChannelPlay(stream.asInt(), false);
    }

    public void setVolume(float volume) {
        BASS_ChannelSetAttribute(stream.asInt(), BASS_ATTRIB_VOL, volume);
    }

    public void setPitch(float pitch) {
        BASS_ChannelSetAttribute(stream.asInt(), BASS_ATTRIB_FREQ, pitch);
    }

    public void setPan(float pan) {
        BASS_ChannelSetAttribute(stream.asInt(), BASS_ATTRIB_PAN, pan);
    }

    public void setPosition(int position) {
        BASS_ChannelSetPosition(stream.asInt(), (long) position, BASS_POS_BYTE);
    }

    @Override
    public void run() {
        play();

        // Wait for the stream to finish playing
        while (isPlaying()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Free the stream
        BASS_StreamFree(stream);
    }

}
