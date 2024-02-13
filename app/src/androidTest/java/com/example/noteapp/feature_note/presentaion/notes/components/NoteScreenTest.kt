package com.example.noteapp.feature_note.presentaion.notes.components

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import com.example.noteapp.TestTags
import com.example.noteapp.di.AppModule
import com.example.noteapp.feature_note.presentaion.MainActivity
import com.example.noteapp.feature_note.presentaion.util.Screen
import com.example.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)


    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()


    @OptIn(ExperimentalAnimationApi::class)
    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.activity.setContent {
            val navController = rememberNavController()
            NoteAppTheme{
                NavHost(navController = navController,
                    startDestination = Screen.NoteScreen.route){
                    composable(route =  Screen.NoteScreen.route){
                        NotesScreen(navController = navController)
                    }
                }
            }
        }
    }


//    _ separated by what we do and what we expected
    @Test
    fun clickToggleOrderSection_isVisible(){

//        val context = ApplicationProvider.getApplicationContext<Context>()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick()
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()

    }




}