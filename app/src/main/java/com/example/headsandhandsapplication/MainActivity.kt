package com.example.headsandhandsapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.example.headsandhandsapplication.databinding.ActivityMainBinding

//ТЗ https://docs.google.com/document/d/1lfpe1JDCuGMQ3cFyn5oNk2PqRO94z6IqCq6yoTaUsYo/edit
//TODO Обработка пересоздания
//TODO Добавление ландшафтной ориентации
//TODO Добавление экрана с настройками характеристик для Player и Monster
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        start()
    }

    private fun start() {
        val dice = Dice()
        val diceImage = binding.diceImage
        val player = Player()
        val monster = Monster()
        createMonster(player.getAttack(), monster, dice, diceImage)
        createPlayer(monster.getAttack(), player, dice, diceImage)
    }

    private fun createPlayer(
        monsterAttack: Int,
        player: Player,
        dice: Dice,
        diceImage: AppCompatImageView
    ) {
        val playerHealth = player.getHealth()
        val playerHealthBar = binding.playerHealthProgressbar
        showPlayerHealth(playerHealth)
        playerHealthBar.max = playerHealth
        playerHealthBar.progress = playerHealth
        val playerAttackModifier = player.getAttackModifier(monsterAttack)
        binding.attackPlayerButton.setOnClickListener {
            val damage = dice.rollDice(this@MainActivity, diceImage, playerAttackModifier)
            val hitPointsLeft = player.getRemainedHealthAfterAttack(damage)
            playerHealthBar.progress = hitPointsLeft
            showPlayerHealth(hitPointsLeft)
        }
        binding.healButton.setOnClickListener {
            if (player.heal()) {
                val health = player.getHealth()
                showPlayerHealth(health)
                playerHealthBar.progress = health
            } else {
                Toast.makeText(this@MainActivity, getString(R.string.player_cant_heal_toast), Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Разделение на Player и Monster защищает от лишних ошибок
    private fun showPlayerHealth(hitpoints: Int) {
        val playerHealthCounterText = binding.playerHealthCounterText
        if (hitpoints <= 0) {
            playerHealthCounterText.text = getString(R.string.player_is_dead)
            showEndOfBattleDialog()
        } else {
            playerHealthCounterText.text = getString(R.string.health_left, hitpoints)
        }
    }

    private fun createMonster(
        playerAttack: Int,
        monster: Monster,
        dice: Dice,
        diceImage: AppCompatImageView
    ) {
        val monsterHealth = monster.getHealth()
        val monsterHealthCounter = binding.monsterHealthCounterText
        val monsterHealthBar = binding.monsterHealthProgressbar
        monsterHealthCounter.text = getString(R.string.health_left, monsterHealth)
        monsterHealthBar.max = monsterHealth
        monsterHealthBar.progress = monsterHealth
        val monsterAttackModifier = monster.getAttackModifier(playerAttack)
        binding.attackMonsterButton.setOnClickListener {
            val damage = dice.rollDice(this@MainActivity, diceImage, monsterAttackModifier)
            val hitPointsLeft = monster.getRemainedHealthAfterAttack(damage)
            monsterHealthBar.progress = hitPointsLeft
            showMonsterHealth(hitPointsLeft)
        }
    }

    private fun showMonsterHealth(hitpoints: Int) {
        val monsterHealthCounterText = binding.monsterHealthCounterText
        if (hitpoints <= 0) {
            monsterHealthCounterText.text = getString(R.string.monster_is_dead)
            showEndOfBattleDialog()
        } else {
            monsterHealthCounterText.text = getString(R.string.health_left, hitpoints)
        }
    }

    private fun showEndOfBattleDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(getString(R.string.yes_botton)) { dialog, button ->
                binding.playerHealthProgressbar.requestLayout()
                binding.monsterHealthProgressbar.requestLayout()
                //binding.playerHealthProgressbar.forceLayout()
                recreate()//FIXME не обновляет UI
            }
            .setNegativeButton(getString(R.string.no_button)) { dialog, button ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
}