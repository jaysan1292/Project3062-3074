package com.jaysan1292.project.c3074.tween;

import android.widget.ImageView;
import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/11/12
 * Time: 9:44 AM
 *
 * @author Jason Recillo
 */
public class ImageViewAccessor implements TweenAccessor<ImageView> {
    public static final int SCALE = 0;

    /**
     * Gets one or many values from the target object associated to the
     * given tween type. It is used by the Tween Engine to determine starting
     * values.
     *
     * @param target       The target object of the tween.
     * @param tweenType    An integer representing the tween type.
     * @param returnValues An array which should be modified by this method.
     *
     * @return The count of modified slots from the returnValues array.
     */
    public int getValues(ImageView target, int tweenType, float[] returnValues) {
        switch (tweenType) {
            case SCALE:
                returnValues[0] = target.getScaleX();
                returnValues[1] = target.getScaleY();
                return 2;
            default:
                return 0;
        }
    }

    /**
     * This method is called by the Tween Engine each time a running tween
     * associated with the current target object has been updated.
     *
     * @param target    The target object of the tween.
     * @param tweenType An integer representing the tween type.
     * @param newValues The new values determined by the Tween Engine.
     */
    public void setValues(ImageView target, int tweenType, float[] newValues) {
        switch (tweenType) {
            case SCALE:
                target.setScaleX(newValues[0]);
                target.setScaleY(newValues[1]);
        }
    }
}
