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

    private lateinit var username: String
    private lateinit var password: String

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(mockRepository)
        username = "Sanket"
        password = "Password"
    }

    @Test
    fun `given correct username and password, when login is called, then should return success`() {
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
        loginViewModel.getLoginLD().observeForTesting(mockLoginObserver) {
            loginViewModel.login(username, password)
            Mockito.verify(mockLoginObserver).onChanged(Resource.Loading)
            Mockito.verify(mockRepository).login(eq(username), eq(password), capture(responseCaptor))
            val errorResponse = Resource.unknownError()
            responseCaptor.value.onResponse(errorResponse)
            Mockito.verify(mockLoginObserver).onChanged(errorResponse)
        }
    }

    @Test
    fun `given empty username, when login is called, then should return error`() {
        username = ""
        loginViewModel.getLoginLD().observeForTesting(mockLoginObserver) {
            loginViewModel.login(username, password)
            Mockito.verify(mockLoginObserver).onChanged(Resource.Loading)
            val resource = loginViewModel.getLoginLD().getOrAwaitValue()
            Assert.assertTrue(resource is Resource.Error)
            Assert.assertTrue(resource.uiText is UiText.StringResource)
            Assert.assertTrue((resource.uiText as UiText.StringResource).id == R.string.username_short_error)
        }
    }

    @Test
    fun `given empty password, when login is called, then should return error`() {
        password = ""
        loginViewModel.getLoginLD().observeForTesting(mockLoginObserver) {
            loginViewModel.login(username, password)
            Mockito.verify(mockLoginObserver).onChanged(Resource.Loading)
            val resource = loginViewModel.getLoginLD().getOrAwaitValue()
            Assert.assertTrue(resource is Resource.Error)
            Assert.assertTrue(resource.uiText is UiText.StringResource)
            Assert.assertTrue((resource.uiText as UiText.StringResource).id == R.string.password_short_error)
        }
    }

}