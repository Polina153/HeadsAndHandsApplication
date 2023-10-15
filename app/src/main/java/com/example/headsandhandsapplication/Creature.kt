package com.example.headsandhandsapplication

internal open class Creature(
    private var attack: Int,//0-30
    private var defence: Int,//0-30
    private var health: Int//N>0
) {

    init {
        if (attack < 0 || attack > 30) attack = 3
        if (defence < 0 || defence > 30) defence = 3
        if (health < 0) health = 40
    }

    fun getAttackModifier(attack: Int): Int {
        var attackModifier = (attack - defence) + 1
        if (attackModifier <= 0) {
            attackModifier = 1
        }
        return attackModifier
    }

    open fun getRemainedHealthAfterAttack(damage: Int): Int {
        health -= damage
        return health
    }

    open fun getHealth(): Int {
        return health
    }

    fun getAttack(): Int = attack
}