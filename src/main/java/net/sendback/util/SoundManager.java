package net.sendback.util;

import net.sendback.util.logging.LogType;
import net.sendback.util.logging.Logger;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SoundManager {
    private final HashMap<String, Clip> clips;
    private final List<String> names;

    private int currentClipIndex;
    private int lastPlayedIndex;

    private boolean randomOrder;
    private final Random random;

    private boolean isPaused;
    private final Map<String, Integer> pausedPositions;

    private final Map<String, LineListener> lineListeners;

    public SoundManager(HashMap<String, Clip> clips) {
        this.clips = new HashMap<>(clips);
        names = new ArrayList<>(clips.keySet());

        currentClipIndex = 0;
        lastPlayedIndex = -1;

        randomOrder = false;
        random = new Random();

        isPaused = false;
        pausedPositions = new HashMap<>();

        lineListeners = new HashMap<>();
    }

    public void start(boolean randomOrder) {
        this.randomOrder = randomOrder;
        if(!clips.isEmpty()) {
            playClip(currentClipIndex);
        }
    }

    private void playClip(int index) {
        if(index < names.size()) {
            String name = names.get(index);
            Clip clip = clips.get(name);
            if(clip != null) {
                clip.setFramePosition(0);

                if(lineListeners.containsKey(name)) {
                    clip.removeLineListener(lineListeners.get(name));
                }

                LineListener listener = event -> {
                    if(event.getType() == LineEvent.Type.STOP && !isPaused) {
                        clip.stop();
                        clip.setFramePosition(0);

                        if(randomOrder) {
                            currentClipIndex = getRandomIndexExcludingLast();
                        } else {
                            currentClipIndex = (currentClipIndex + 1) % names.size();
                        }

                        lastPlayedIndex = currentClipIndex;
                        playClip(currentClipIndex);
                    }
                };
                lineListeners.put(name, listener);
                clip.addLineListener(listener);

                clip.start();
            }
        }
    }

    private int getRandomIndexExcludingLast() {
        int newIndex;
        do {
            newIndex = random.nextInt(names.size());
        } while(newIndex == lastPlayedIndex);
        return newIndex;
    }

    public void stop() {
        for(Clip clip : clips.values()) {
            if(clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0);
        }
        isPaused = false;
        pausedPositions.clear();
        lineListeners.clear();
    }

    public void pause() {
        if(!isPaused) {
            isPaused = true;
            for(Map.Entry<String, Clip> entry : clips.entrySet()) {
                Clip clip = entry.getValue();
                if(clip.isRunning()) {
                    pausedPositions.put(entry.getKey(), clip.getFramePosition());
                    clip.stop();
                    if(lineListeners.containsKey(entry.getKey())) {
                        clip.removeLineListener(lineListeners.get(entry.getKey()));
                    }
                }
            }
        }
    }

    public void resume() {
        if(isPaused) {
            for(Map.Entry<String, Clip> entry : clips.entrySet()) {
                String name = entry.getKey();
                Clip clip = entry.getValue();
                if(pausedPositions.containsKey(name)) {
                    int position = pausedPositions.get(name);
                    clip.setFramePosition(position);
                    clip.start();

                    LineListener listener = lineListeners.get(name);
                    if(listener != null) {
                        clip.addLineListener(listener);
                    }
                }
            }
            isPaused = false;
            pausedPositions.clear();
        } else {
            playClip(currentClipIndex);
        }
    }

    public void setVolume(float volume) {
        if(volume < 0.0f || volume > 1.0f) {
            Logger.log("Volume must be between 0.0 and 1.0", LogType.WARN);
            return;
        }

        for(Clip clip : clips.values()) {
            if(clip.isOpen()) {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                float min = volumeControl.getMinimum();
                float max = volumeControl.getMaximum();
                float dB = min + (max - min) * volume;

                volumeControl.setValue(dB);
            }
        }
    }
}
