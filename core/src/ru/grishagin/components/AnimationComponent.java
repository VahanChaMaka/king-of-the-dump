package ru.grishagin.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationComponent implements Component {

    private final float FRAME_DURATION = 0.05f;

    public final Animation<TextureRegion> runningAnimation;
    public float timer = 0;

    public AnimationComponent(TextureRegion[] frames) {
        this.runningAnimation = new Animation<>(FRAME_DURATION, frames);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }
}
