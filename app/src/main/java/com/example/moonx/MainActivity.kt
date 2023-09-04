package com.example.moonx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moonx.databinding.ActivityMainBinding
import com.example.moonx.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.apply {
            setupWithNavController(navController)
            setOnItemSelectedListener { item ->
            val destinationId = when(item.itemId) {
                R.id.bottomHomeFragment -> R.id.homeFragment
                R.id.bottomMeditationFragment -> R.id.meditationFragment
                R.id.bottomSettingsFragment -> R.id.settingsFragment
                else -> -1
            }
                if (destinationId != -1) {
                    navController.navigate(destinationId)
                }
                true

            }
            setBottomNavVisibility(navController)

        }

    }

    private fun setBottomNavVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentsWithHiddenBottomNav = setOf(
                R.id.homeFragment,
                R.id.meditationFragment,
                R.id.settingsFragment,

            )

            binding.bottomNav.visibility = if (fragmentsWithHiddenBottomNav.contains(destination.id)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}