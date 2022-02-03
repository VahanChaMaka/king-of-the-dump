package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {

    private final float FRAME_DURATION = 0.05f;

    public final Animation<TextureRegion> runningAnimation;
    public float timer = 0;
    //do nothing until animation is finished
    public boolean isBlocking;

    public AnimationComponent(TextureRegion[] frames) {
        this(frames, Animation.PlayMode.LOOP);
    }

    public AnimationComponent(TextureRegion[] frames, Animation.PlayMode playMode) {
        this.runningAnimation = new Animation<>(FRAME_DURATION, frames);
        runningAnimation.setPlayMode(playMode);
    }
}
