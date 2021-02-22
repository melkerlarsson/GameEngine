package com.melkerlarsson.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import java.security.Key;

public class Camera {

    private Vector3f position = new Vector3f(0,1,0);
    private float pitch;
    private float yaw;
    private float roll;

    private final float cameraSpeed = 0.5f;

    public Camera(){}

    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x += cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x -= cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
            position.y += cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
            if (position.y > 1) {
                position.y -= cameraSpeed;
            }
        }

    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
