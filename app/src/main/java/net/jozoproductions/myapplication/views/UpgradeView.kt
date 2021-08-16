package net.jozoproductions.myapplication.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.fragments.UpgradesFragment
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.upgrades.Upgrade

class UpgradeView : ConstraintLayout, View.OnClickListener {

    private var upgrade: Upgrade? = null

    private lateinit var upgradesFragment: UpgradesFragment

    //Views
    private var nameText: TextView? = null
    private var effectText: TextView? = null
    private var priceText: TextView? = null
    private var icon: ImageView? = null

    constructor(context: Context) : super(context) {
        Init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Init(context)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Init(context)
    }

    private fun Init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_upgrade, this, true)

        //Initialize views
        nameText = findViewById(R.id.name)
        effectText = findViewById(R.id.effect)
        priceText = findViewById(R.id.price)
        icon = findViewById(R.id.icon)
        findViewById<View>(R.id.buy).setOnClickListener(this)
    }

    fun SetUpgrade(upgradesFragment: UpgradesFragment, upgrade: Upgrade) {
        this.upgradesFragment = upgradesFragment;
        this.upgrade = upgrade

        val price = upgrade.basePrice + upgrade.priceIncrease * upgrade.upgradeLevel

        nameText!!.text = upgrade.upgradeName + " " + upgrade.upgradeLevel
        effectText!!.text = "Current: " + upgrade.CalculateEffect() + " (+" + upgrade.effectIncrease + ")"
        priceText!!.text = price.toString() + ""
        icon!!.setImageDrawable(ContextCompat.getDrawable(context, upgrade.drawableId))
    }

    override fun onClick(v: View) {
        val price = upgrade!!.basePrice + (upgrade!!.priceIncrease * upgrade!!.upgradeLevel)

        if (Player.money!! >= price) {
            upgrade!!.upgradeLevel++
            Player.money = Player.money!! - price //Cannot ease it to: (Player.money -= price), because kotlin is retarded, ig
            upgradesFragment.RefreshUpgrades()
        } else {
            Toast.makeText(context, "Not enough money!", Toast.LENGTH_SHORT).show()
        }
    }
}