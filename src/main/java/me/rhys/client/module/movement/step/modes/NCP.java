package me.rhys.client.module.movement.step.modes;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.StepEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.client.module.movement.step.Step;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NCP extends ModuleMode<Step> {

    private final static Map<Float, double[]> offsets = new HashMap<>();

    public NCP(String name, Step parent) {
        super(name, parent);
    }

    @EventTarget
    public void onEvent(StepEvent event) {
        if (mc == null || mc.thePlayer == null)
            return;

        if (!mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isInLiquid())
            return;

        float stepHeight;
        if ((stepHeight = parent.getNeededStepHeight()) > parent.height)
            return;

        if ((stepHeight == 1 && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
                mc.thePlayer.boundingBox.offset(mc.thePlayer.motionX, 0.6, mc.thePlayer.motionZ)).isEmpty())
                || !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(
                mc.thePlayer.motionX, stepHeight + 0.01, mc.thePlayer.motionZ)).isEmpty())
            return;

        event.setStepHeight(stepHeight);

        Arrays.stream(offsets.get(stepHeight)).forEachOrdered(offsets -> mc.thePlayer.sendQueue
                .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                        mc.thePlayer.posY + offsets, mc.thePlayer.posZ, false)));

    }

    static {
        offsets.put(1.0F, new double[]{0.42, 0.753});
        offsets.put(1.5F, new double[]{0.42, 0.75, 1, 1.16, 1.23, 1.2});
        offsets.put(2.0F, new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43});
    }
}
