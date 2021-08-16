package net.jozoproductions.myapplication.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.upgrades.Upgrade
import net.jozoproductions.myapplication.views.UpgradeView

class UpgradesFragment : Fragment(R.layout.fragment_upgrades) {

    private lateinit var catch_fall_speed: UpgradeView
    private lateinit var catch_fall_cooldown: UpgradeView

    private lateinit var rest_emp_movement_speed: UpgradeView
    private lateinit var rest_emp_faster_cutting: UpgradeView
    private lateinit var rest_emp_faster_cooking: UpgradeView
    private lateinit var rest_more_storage: UpgradeView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize UpgradeViews
        catch_fall_speed = view.findViewById(R.id.catch_fall_speed)
        catch_fall_cooldown = view.findViewById(R.id.catch_fall_cooldown)

        rest_emp_movement_speed = view.findViewById(R.id.rest_emp_movement_speed)
        rest_emp_faster_cutting = view.findViewById(R.id.rest_emp_faster_cutting)
        rest_emp_faster_cooking = view.findViewById(R.id.rest_emp_faster_cooking)
        rest_more_storage = view.findViewById(R.id.rest_more_storage)

        RefreshUpgrades()
    }

    public fun RefreshUpgrades() {
        catch_fall_speed.SetUpgrade(this, Upgrade.CATCHING_FALL_SPEED)
        catch_fall_cooldown.SetUpgrade(this, Upgrade.CATCHING_FALL_COOLDOWN)

        rest_emp_movement_speed.SetUpgrade(this, Upgrade.EMPLOYEE_MOVEMENT_SPEED)
        rest_emp_faster_cutting.SetUpgrade(this, Upgrade.EMPLOYEE_FASTER_CUTTING)
        rest_emp_faster_cooking.SetUpgrade(this, Upgrade.EMPLOYEE_FASTER_COOKING)
        rest_more_storage.SetUpgrade(this, Upgrade.BUILDING_MORE_STORAGE)
    }
}