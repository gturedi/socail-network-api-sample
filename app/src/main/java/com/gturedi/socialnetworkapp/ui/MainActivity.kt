package com.gturedi.socialnetworkapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.gturedi.socialnetworkapp.R
import com.gturedi.socialnetworkapp.databinding.ActivityMainBinding
import com.gturedi.socialnetworkapp.network.AuthRepository
import com.gturedi.socialnetworkapp.ui.home.AuthViewModel
import com.gturedi.socialnetworkapp.ui.home.AuthViewModelFactory
import com.gturedi.socialnetworkapp.util.PrefService
import com.gturedi.socialnetworkapp.util.log

class MainActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels() {
        AuthViewModelFactory(AuthRepository())
    }
    private val KEY_CODE = "code"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        log("token ${PrefService.accessToken()}")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val code = intent?.data?.getQueryParameter(KEY_CODE)
        log("code $code")
        viewModel.updateAuthCode(code.orEmpty())
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}