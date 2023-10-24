package insaniquarium.game;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import insaniquarium.Main;
import jouvieje.bass.Bass;
import jouvieje.bass.BassInit;
import jouvieje.bass.exceptions.BassException;
import jouvieje.bass.structures.HMUSIC;
import jouvieje.bass.structures.HSTREAM;
import jouvieje.libloader.RTLD;


import javax.sound.sampled.*;

import static jouvieje.bass.Bass.*;
import static jouvieje.bass.defines.BASS_ACTIVE.BASS_ACTIVE_PLAYING;
import static jouvieje.bass.defines.BASS_ATTRIB.*;
import static jouvieje.bass.defines.BASS_POS.BASS_POS_BYTE;
import static jouvieje.bass.defines.BASS_POS.BASS_POS_MUSIC_ORDER;
import static jouvieje.bass.defines.BASS_SAMPLE.BASS_SAMPLE_FLOAT;
import static jouvieje.bass.defines.BASS_SAMPLE.BASS_SAMPLE_LOOP;


public class SoundManager {

     int OFFSET_SONG_TANK_1 = 0;
     int OFFSET_SONG_TANK_2 = 12;
     int OFFSET_SONG_TANK_4 = 37;
     int OFFSET_SONG_TANK_3 = 25;
     int OFFSET_MAIN_MENU = 45;
     int OFFSET_PET_REVEAL = 49;
     int OFFSET_CREDITS = 50;
     int OFFSET_BONUS_ROUND = 58;

    private static SoundManager instance;
    boolean soundOn = true;

    private ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private String SOUND_PATH = "../sounds/";
    private String SONG_PATH = "../music/";
    public SoundManager(){
        try {
            BassInit.loadLibraries();
            BASS_Init((-1), (44100), 0, null, null);
        } catch(BassException e) {

        }


        File file = new File(Main.class.getResource(SONG_PATH + "Insaniq2.mo3").getFile());
        HMUSIC handle = BASS_MusicLoad(false, file.getPath(), 0,0,  0,0);

        BASS_ChannelPlay(handle.asInt(), false);
        BASS_ChannelSetPosition(handle.asInt(), OFFSET_SONG_TANK_1, BASS_POS_MUSIC_ORDER);

    }
    public void shutDown(){
        threadPool.shutdown();

        //BASS_ChannelStop(streamHandle.asInt());
        //BASS_StreamFree(streamHandle);


    }
    public static SoundManager getInstance(){
        if(instance == null){
            instance = new SoundManager();
        }
        return instance;
    }

    public void playSound(String soundName) {
        String path = SOUND_PATH;
        path += soundName;
        File file = new File(Main.class.getResource(path).getFile());

        if(soundOn){
            AudioStream stream = new AudioStream(file.getPath());
            threadPool.submit(stream);
        }
    }



}
