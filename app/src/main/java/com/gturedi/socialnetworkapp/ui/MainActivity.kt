package com.gturedi.socialnetworkapp.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.gturedi.socialnetworkapp.ui.home.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

//@AndroidEntryPoint
class MainActivity : BaseActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding
    //private val viewModel: AuthViewModel by viewModels()
    private val viewModel:AuthViewModel by viewModel()
    private val KEY_CODE = "code"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text("Hello world!")
        }
    }

}

@Preview
@Composable
fun Greeting() {
    Text("Hello, World!", style = TextStyle(color = Color.Red))
}