package com.sanket.androidtestingrepo

import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sanket.androidtestingrepo.espresso.EspressoIdlingResource
import com.sanket.androidtestingrepo.robots.LoginRobot
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @BindValue
    @JvmField
    val viewModel = mockk<LoginViewModel>(relaxed = true)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var scenario: ActivityScenario<LoginActivity>
    private val robot: LoginRobot = LoginRobot()
    private val loginLD = MutableLiveData<SimpleResource>()
    private val usernameLD = MutableLiveData<SimpleResource>()
    private val passwordLD = MutableLiveData<SimpleResource>()

    fun launch() {
        scenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Before
    fun setup() {
        Intents.init()
        hiltRule.inject()
        setInternalFieldValue(viewModel, "mBagOfTags", HashMap<String, Any>())
        every { viewModel.getLoginLD() } returns loginLD
        every { viewModel.getUsernameLD() } returns usernameLD
        every { viewModel.getPasswordLD() } returns passwordLD
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
        Intents.release()
    }

    @Test
    fun should_open_login_activity() {
        robot.launch(this)
    }

    @Test
    fun should_open_main_activity_on_submitting_correct_username_and_password() {
        val username = "Sanket"
        val password = "Sanket"

        robot.launch(this)
            .checkIfUsernameEditTextIsVisible()
            .checkIfPasswordEditTextIsVisible()
            .checkIfLoginBtnIsVisible()
            .enterUserName(username)
            .enterPassword(password)
            .clickLoginBtn()
            .sendMockSuccessResponse(loginLD)
            .checkIfMainActivityIsOpen()
    }

    private fun setInternalFieldValue(target: Any, fieldName: String, value: Any, javaClass: Class<in Any> = target.javaClass) {
        try {
            val field = javaClass.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(target, value)
        } catch (exception: NoSuchFieldException) {
            val superClass = javaClass.superclass
            if (superClass != null) {
                setInternalFieldValue(target, fieldName, value, superClass)
            } else {
                throw RuntimeException("Field $fieldName is not declared in a hierarchy of this class")
            }
        }
    }

}