package net.sendback.util;

import net.sendback.util.logging.LogType;
import net.sendback.util.logging.Logger;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.util.*;

public class SoundManager {
    private final HashMap<String, Clip> clips;
    private final Queue<Clip> queue;

    private boolean randomized;
    private boolean isPaused;
    private FloatControl volumeControl;

    public SoundManager(HashMap<String, Clip> clips) {
        this.clips = clips;
        this.queue = new LinkedList<>();
        this.randomized = false;
        this.isPaused = false;

        for(Clip clip : clips.values()) {
            if(clip != null) {
                queue.add(clip);
            }
        }
    }

    public void setRandomized(boolean randomized) {
        this.randomized = randomized;
    }

    public void shuffle() {
        List<Clip> clipList = new ArrayList<>(clips.values());
        Collections.shuffle(clipList);
        queue.clear();
        queue.addAll(clipList);
    }

    public void play() {
        if(isPaused) {
            isPaused = false;
            playNextClip();
            return;
        }

        if(queue.isEmpty()) {
            return;
        }
        playNextClip();
    }

    private void playNextClip() {
        if(isPaused) {
            return;
        }

        if(queue.isEmpty()) {
            if(randomized) {
                shuffle();
            } else {
                return;
            }
        }

        Clip clip = queue.poll();
        if(clip != null) {
            try {
                clip.setFramePosition(0);
                clip.start();
                clip.removeLineListener(lineListener);
                clip.addLineListener(lineListener);
            } catch(Exception e) {
                Logger.log("Error playing clip: " + e.getMessage(), LogType.ERROR);
            }
        }
    }

    private final LineListener lineListener = event -> {
        if(event.getType() == LineEvent.Type.STOP) {
            playNextClip();
        }
    };

    public void pause() {
        if(!isPaused) {
            isPaused = true;
            for(Clip clip : clips.values()) {
                if(clip.isRunning()) {
                    clip.stop();
                }
            }
        }
    }

    public void setVolume(float volume) {
        if(volume < 0.0f || volume > 1.0f) {
            Logger.log("Volume must be between 0.0 (silent) and 1.0 (max volume).", LogType.WARN);
            return;
        }

        float minDb = -80.0f;
        float maxDb = 0.0f;
        float dbVolume;

        if(volume == 0.0f) {
            dbVolume = minDb;
        } else {
            dbVolume = 20.0f * (float) Math.log10(volume);

            dbVolume = Math.max(dbVolume, minDb);
            dbVolume = Math.min(dbVolume, maxDb);
        }

        for(Clip clip : clips.values()) {
            if(clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue(dbVolume);
            } else {
                Logger.log("Volume control not supported for this clip.", LogType.WARN);
            }
        }
    }

    public void stopAll() {
        for(Clip clip : clips.values()) {
            clip.stop();
            clip.close();
        }
        queue.clear();
    }
}
