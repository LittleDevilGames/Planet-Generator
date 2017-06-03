package zk.planet_generator.scene_objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector3;
import zk.planet_generator.Scene;

public class Orbiter extends SpaceObject {
    private float angularVelocity;
    private float zTilt; // Affects how steep of an angle it orbits at
    private float xTilt; // Affects how vertical it appears to the viewer
    private float angle;
    private float radius;
    private float yOffset;

    private int color;

    private Vector3 position;
    private Matrix3 rotZ;
    private Matrix3 rotX;

    public Orbiter(Sprite sprite, OrbiterBlueprint blueprint) {
        super(sprite);

        this.angularVelocity = blueprint.angularVelocity;
        this.zTilt = blueprint.zTilt;
        this.xTilt = blueprint.xTilt;
        this.angle = blueprint.angle;
        this.radius = blueprint.radius;
        this.yOffset = blueprint.yOffset;

        position = new Vector3();
        rotZ = new Matrix3().setToRotation(zTilt);
        rotX = new Matrix3().setToRotation(Vector3.X, xTilt);
    }

    public Orbiter(Sprite sprite, OrbiterBlueprint blueprint, int color) {
        this(sprite, blueprint);
        this.color = color;
    }

    @Override
    public void update(float delta) {
        angle = (angle + (angularVelocity * delta)) % 360;

        // Start object on the XZ plane and rotate to correct 3D orientation
        position.set(radius * MathUtils.cosDeg(angle), 0, radius * MathUtils.sinDeg(angle));
        position.mul(rotZ);
        position.mul(rotX);

        // Set sprite position for 2D rendering and zCoord for ordering
        getSprite().setPosition(Scene.CENTER_X - getSprite().getWidth()/2 + position.x, Scene.CENTER_Y - getSprite().getHeight()/2 + position.y + yOffset);
        setZPos((int)position.z);
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public float getZTilt() {
        return zTilt;
    }

    public void setZTilt(float amount) {
        zTilt = amount;
        rotZ.setToRotation(zTilt);
    }

    public float getXTilt() {
        return xTilt;
    }

    public void setXTilt(float xTilt) {
        this.xTilt = xTilt;
        rotX.setToRotation(Vector3.X, xTilt);
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getSize() {
        return getSprite().getWidth();
    }

    public int getColor() {
        return color;
    }

    public static class OrbiterBlueprint {
        public float angularVelocity;
        public float zTilt;
        public float xTilt;
        public float angle;
        public float radius;
        public float yOffset;
    }
}