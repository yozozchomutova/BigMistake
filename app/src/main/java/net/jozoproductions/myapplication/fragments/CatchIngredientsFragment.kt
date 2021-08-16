package net.jozoproductions.myapplication.fragments

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import net.jozoproductions.myapplication.MainActivity
import net.jozoproductions.myapplication.R
import net.jozoproductions.myapplication.player.Player
import net.jozoproductions.myapplication.somegoodenums.Ingredient
import net.jozoproductions.myapplication.upgrades.Upgrade
import kotlin.random.Random

class CatchIngredientsFragment : Fragment(R.layout.fragment_catchingredients), View.OnTouchListener {

    private lateinit var kotlik: ImageView

    private var kotlikWidth: Int = 0

    private var maxStorageSize = 0

    //Views
    private lateinit var storageInfo: TextView

    //Thread
    private lateinit var thread: Thread
    private var ingredientSpawnCooldown: Float = 300f

    private var fallingIngredients = mutableListOf<Ingredient>()
    private var fallingIngredientImageViews = mutableListOf<ImageView>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        maxStorageSize = Player.restaurant?.getMaxStorage() ?: -1

        val root: ConstraintLayout = view.findViewById(R.id.root)
        root.setOnTouchListener(this)

        storageInfo = view.findViewById(R.id.storage_info)
        UpdateStorageInfoTextView()

        kotlik = view.findViewById(R.id.kotlik)
        kotlik.post { kotlin.run {
            kotlikWidth = kotlik.width
        } }

        //Thread
        thread = Thread {
            try {
                while (true) {
                    ingredientSpawnCooldown--

                    //Spawner
                    if (ingredientSpawnCooldown <= 0) {
                        ingredientSpawnCooldown = Upgrade.CATCHING_FALL_COOLDOWN.CalculateEffect()

                        val fetchedIngredients = Ingredient.values()
                        val randomIngredient = fetchedIngredients[Random.nextInt(0, fetchedIngredients.size)]

                        val newFallingIngredient = ImageView(context);
                        newFallingIngredient.setImageDrawable(context?.let {
                            ContextCompat.getDrawable(
                                it, randomIngredient.drawableId)
                        })
                        newFallingIngredient.layoutParams = ConstraintLayout.LayoutParams(64, 64)
                        newFallingIngredient.x = Random.nextFloat() * (MainActivity.SCREEN_WIDTH-64)
                        newFallingIngredient.y = 0f

                        AddViewToRoot(root, newFallingIngredient)

                        fallingIngredients.add(randomIngredient)
                        fallingIngredientImageViews.add(newFallingIngredient)
                    }

                    //Update velocity of falling ingredients
                    for (i in 0 until fallingIngredients.size) {
                        if (i != fallingIngredientImageViews.size) {
                            val fallingIIV = fallingIngredientImageViews.get(i)
                            fallingIIV.y += Upgrade.CATCHING_FALL_SPEED.CalculateEffect()

                            //Check for collision with kotlik
                            val kotlikHitboxPos = IntArray(2)
                            kotlik.getLocationOnScreen(kotlikHitboxPos)
                            var kotlikSize = kotlik.measuredWidth
                            kotlikSize /= 2

                            if (maxStorageSize <= Player.getIngredientCount()) {
                                //Message
                            } else if (fallingIIV.x + 32 > kotlikHitboxPos[0] + kotlikSize - 64 && fallingIIV.x + 32 < kotlikHitboxPos[0] + kotlikSize + 64 &&
                                    fallingIIV.y + 32 > kotlikHitboxPos[1] + kotlikSize - 64 && fallingIIV.y + 32 < kotlikHitboxPos[1] + kotlikSize + 64) {
                                Player.AddIngredient(fallingIngredients[i])
                                UpdateStorageInfoTextView()

                                RemoveViewToRoot(root, fallingIIV)

                                fallingIngredients.removeAt(i)
                                fallingIngredientImageViews.removeAt(i)
                            } else if (fallingIIV.y > MainActivity.SCREEN_HEIGHT) { //Out of borders?
                                RemoveViewToRoot(root, fallingIIV)

                                fallingIngredients.removeAt(i)
                                fallingIngredientImageViews.removeAt(i)
                            }
                        }
                    }

                    //Adios for 0.01 secs.
                    Thread.sleep(10)
                }
            } catch (i: InterruptedException) {}
        }

        thread.start()
    }

    override fun onDestroyView() {
        thread.interrupt()
        super.onDestroyView()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        var actionId = event?.action

        if (actionId == MotionEvent.ACTION_MOVE) {
            val x = event?.rawX

            kotlik.x = x!! - (kotlikWidth/2f)
        }

        return true
    }

    private fun AddViewToRoot(root: ConstraintLayout, view: View) {
        val activity = root.context as AppCompatActivity
        activity.runOnUiThread {
            root.addView(view)
        }
    }

    private fun RemoveViewToRoot(root: ConstraintLayout, view: View) {
        val activity = root.context as AppCompatActivity
        activity.runOnUiThread {
            root.removeView(view)
        }
    }

    private fun UpdateStorageInfoTextView() {
        (context as AppCompatActivity).runOnUiThread {
            storageInfo.text = "Storage: " + Player.getIngredientCount() + "/" + maxStorageSize
        }
    }
}
