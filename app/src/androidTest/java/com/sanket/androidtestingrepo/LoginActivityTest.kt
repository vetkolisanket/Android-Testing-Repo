package com.sanket.androidtestingrepo

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    private lateinit var scenario: ActivityScenario<LoginActivity>

    @Test
    fun should_open_login_activity() {
        scenario = ActivityScenario.launch(LoginActivity::class.java)
    }

}