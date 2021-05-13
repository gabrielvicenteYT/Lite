package me.rhys.client.module.movement.fly.modes;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMotionEvent;
import me.rhys.base.event.impl.player.PlayerMoveEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.client.module.movement.fly.Fly;

public class CubeCraft extends ModuleMode<Fly> {

    @Name("Speed")
    @Clamp(min = 0, max = 9)
    public double speed = 1;

    @Name("Ground Spoof")
    public boolean groundSpoof = false;

    public CubeCraft(String name, Fly parent) {
        super(name, parent);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @EventTarget
    void onMotion(PlayerMotionEvent event) {
        event.setOnGround(this.groundSpoof);
    }

    @EventTarget
    void playerMove(PlayerMoveEvent event) {

    }
}
