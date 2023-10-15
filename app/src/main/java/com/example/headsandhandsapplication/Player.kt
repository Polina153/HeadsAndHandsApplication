package com.example.headsandhandsapplication

import kotlin.math.roundToInt

internal class Player(attack: Int = 4, defence: Int = 3, private var health: Int = 40) : Creature(attack, defence, health) {

    private var healCount: Int = 4
    private val healthThreshold: Int = health

    init {
        if (health < 0) health = 40
    }

    //TODO Имеет смысл возвращать healCount и отображать это значение на самой кнопке
    fun heal(): Boolean {
        val healthResult = health + (healthThreshold * 0.3).roundToInt()
        if (healthResult > healthThreshold) return false

        healCount--
        if (healCount > 0) {
            health = healthResult
            return true
        }
        return false
    }

    override fun getRemainedHealthAfterAttack(damage: Int): Int {
        health -= damage
        return health
    }

    override fun getHealth(): Int {
        return health
    }
}