package me.rhys.client.module.combat.velocity.modes;

import me.rhys.base.event.Event;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.network.PacketEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.client.module.combat.velocity.Velocity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Reverse extends ModuleMode<Velocity> {

    @Name("Vertical")
    @Clamp(min = 0.0, max = 100)
    public double vertical = 0.0;

    @Name("Horizontal")
    @Clamp(min = 0.0, max = 100)
    public double horizontal = 0.0;

    public Reverse(String name, Velocity parent) {
        super(name, parent);
    }

    @EventTarget
    public void packetReceive(PacketEvent event) {

        if (event.getDirection().equals(Event.Direction.IN)) {

            Packet<?> packet = event.getPacket();

            if (packet instanceof S12PacketEntityVelocity) {
                S12PacketEntityVelocity velocity = (S12PacketEntityVelocity) packet;
                if (velocity.getEntityID() == mc.thePlayer.getEntityId()) {

                    if (vertical == 0.0 && horizontal == 0.0) {
                        event.setCancelled(true);
                    }

                    double motionX = velocity.getMotionX() / 8000. * (horizontal / 100.0D);
                    double motionZ = velocity.getMotionZ() / 8000. * (horizontal / 100.0D);

                    motionX*= -Math.sin(mc.thePlayer.rotationYaw);
                    motionZ*= Math.cos(mc.thePlayer.rotationPitch);
                    velocity.setMotionX((int) (motionX * 8000));
                    velocity.setMotionY((int) (velocity.getMotionY() * (vertical / 100.0D)));
                    velocity.setMotionZ((int) (motionZ * 8000));
                }
            }
        }
    }
}
