package de.royzer.fabrichg.game.phase.phases

import de.royzer.fabrichg.game.GamePhaseManager
import de.royzer.fabrichg.game.broadcast
import de.royzer.fabrichg.game.phase.GamePhase
import de.royzer.fabrichg.game.phase.PhaseType
import net.axay.fabrik.core.text.literalText
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameMode
import net.minecraft.world.Heightmap

object InvincibilityPhase : GamePhase() {
    override fun init() {
        GamePhaseManager.resetTimer()
        broadcast("hg geht los ok :)")
        GamePhaseManager.server.playerManager.playerList.forEach {
            it.teleport(0.0, 100.0, 0.0)
            val highestPos = it.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlockPos(0,100,0))
            it.teleport(highestPos.x.toDouble(), highestPos.y.toDouble(), highestPos.z.toDouble())
            it.changeGameMode(GameMode.SURVIVAL)
        }
    }

    override fun tick(timer: Int) {
        when (val timeLeft = maxPhaseTime - timer) {
            180, 120, 60, 30, 15, 10, 5, 4, 3, 2, 1 -> broadcast(literalText("invincibility endet in $timeLeft"))
            0 -> startNextPhase()
        }
    }

    override val phaseType = PhaseType.INVINCIBILITY
    override val maxPhaseTime = 5 * 1
    override val nextPhase = IngamePhase
}