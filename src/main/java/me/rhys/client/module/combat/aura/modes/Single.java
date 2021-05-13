package me.rhys.client.module.combat.aura.modes;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMotionEvent;
import me.rhys.base.event.impl.player.PlayerUpdateEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.base.util.RotationUtil;
import me.rhys.base.util.vec.Vec2f;
import me.rhys.client.module.combat.aura.Aura;

public class Single extends ModuleMode<Aura> {
    public Single(String name, Aura parent) {
        super(name, parent);
    }

    @EventTarget
    void onUpdate(PlayerUpdateEvent event) {
        if(!parent.doAim) return;

        if (parent.lockView && parent.target != null) {
            Vec2f rotation = RotationUtil.getRotations(parent.target);
            if(parent.currentRotation == null) parent
                    .currentRotation = new Vec2f(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);

            if(parent.smoothness > 0f) {
                float yaw = RotationUtil.updateYawRotation(parent.currentRotation.x, rotation.x,
                        Math.max(1, 180 * (1 - parent.smoothness / 100)));
                float pitch = RotationUtil.updatePitchRotation(parent.currentRotation.y, rotation.y,
                        Math.max(1, 90f * (1 - parent.smoothness / 100)));

                rotation.x = yaw;
                rotation.y = pitch;

                parent.currentRotation = rotation;
            }

            if(parent.minecraftRotation) rotation = RotationUtil.clampRotation(rotation);

            mc.thePlayer.rotationYaw = rotation.getVecX();
            mc.thePlayer.rotationPitch = rotation.getVecY();
        }
    }

    @EventTarget
    void onMotion(PlayerMotionEvent event) {
        if (parent.target != null && event.getType() == parent.eventType) {
            parent.swing(parent.target);
            parent.sendUseItem();

            if (!parent.lockView && parent.doAim) {
                parent.aimAtTarget(event, parent.target);
            }
        }
    }
}
