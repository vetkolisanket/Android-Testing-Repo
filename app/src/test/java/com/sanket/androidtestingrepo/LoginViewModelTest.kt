package com.sanket.androidtestingrepo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.kotlin.capture
import org.mockito.kotlin.eq
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel

    @Mock
    private lateinit var mockRepository: AuthRepository

    @Mock
    private lateinit var mockLoginObserver: Observer<SimpleResource>

    @Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Captor
    private lateinit var responseCaptor: ArgumentCaptor<ResponseCallback<SimpleResource>>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(mockRepository)
    }

    @Test
    fun `given correct username and password, when login is called, then should return success`() {
        val username = "Sanket"
        val password = "Sanket"

        loginViewModel.getLoginLD().observeForTesting(mockLoginObserver) {
            loginViewModel.login(username, password)
            Mockito.verify(mockLoginObserver).onChanged(Resource.Loading)
            Mockito.verify(mockRepository).login(eq(username), eq(password), capture(responseCaptor))
            val successResponse = Resource.Success(Unit)
            responseCaptor.value.onResponse(successResponse)
            Mockito.verify(mockLoginObserver).onChanged(successResponse)
        }
    }

    @Test
    fun `given incorrect username and password, when login is called, then should return error`() {

    }

    @Test
    fun `given empty username, when login is called, then should return error`() {

    }

    @Test
    fun `given empty password, when login is called, then should return error`() {

    }

    @Test
    fun `given short username, when login is called, then should return error`() {

    }

    @Test
    fun `given short password, when login is called, then should return error`() {

    }

}