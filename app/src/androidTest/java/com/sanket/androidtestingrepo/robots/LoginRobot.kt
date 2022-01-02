package com.sanket.androidtestingrepo.robots

import androidx.lifecycle.MutableLiveData
import com.sanket.androidtestingrepo.*

class LoginRobot {

    fun launch(loginActivityTest: LoginActivityTest): LoginRobot {
        loginActivityTest.launch()
        return this
    }

    fun checkIfUsernameEditTextIsVisible(): LoginRobot {
        EspressoUtils.isViewDisplayed(R.id.etUsername)
        return this
    }

    fun checkIfPasswordEditTextIsVisible(): LoginRobot {
        EspressoUtils.isViewDisplayed(R.id.etPassword)
        return this
    }

    fun checkIfLoginBtnIsVisible(): LoginRobot {
        EspressoUtils.isViewDisplayed(R.id.btnLogin)
        return this
    }

    fun enterUserName(username: String): LoginRobot {
        EspressoUtils.enterTextInEditText(username, R.id.etUsername)
        return this
    }

    fun enterPassword(password: String): LoginRobot {
        EspressoUtils.replaceTextInEditText(password, R.id.etPassword)
        return this
    }

    fun clickLoginBtn(): LoginRobot {
        EspressoUtils.performClick(R.id.btnLogin)
        return this
    }

    fun checkIfMainActivityIsOpen() {
        EspressoUtils.isActivityOpen(MainActivity::class.qualifiedName!!)
    }

    fun sendMockSuccessResponse(loginLD: MutableLiveData<Resource<Unit>>): LoginRobot {
        loginLD.postValue(Resource.Success(Unit))
        return this
    }

}