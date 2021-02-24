package com.melkerlarsson.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;


public class Camera {

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;
    private float pitch = 20;
    private float yaw;

    private final float MAX_DISTANCE_FROM_PLAYER = 200;
    private final float MIN_DISTANCE_FROM_PLAYER = 30;

    private final float MAX_PITCH = 90;
    private final float MIN_PITCH = 5;

    private int zoomDirection = -1;
    private int pitchDirection = -1;

    private Vector3f position = new Vector3f(100,60,50);

    private float roll;

    private Player player;

    public Camera(Player player) {
        this.player = player;
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();

        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();

        calculateCameraPosition(horizontalDistance, verticalDistance);
        calculateYaw();



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

    private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + verticalDistance;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer += zoomDirection * zoomLevel;

        if (distanceFromPlayer > MAX_DISTANCE_FROM_PLAYER) {
            distanceFromPlayer = MAX_DISTANCE_FROM_PLAYER;
        } else if (distanceFromPlayer < MIN_DISTANCE_FROM_PLAYER) {
            distanceFromPlayer = MIN_DISTANCE_FROM_PLAYER;
        }
    }

    private void calculateYaw() {
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }

    private void calculatePitch() {
        if (Mouse.isButtonDown(1)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch += pitchDirection * pitchChange;

            if (pitch > MAX_PITCH) {
                pitch = MAX_PITCH;
            } else if (pitch < MIN_PITCH) {
                pitch = MIN_PITCH;
            }
        }
    }

    private void calculateAngleAroundPlayer() {
        if (Mouse.isButtonDown(0)) {
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }
}
