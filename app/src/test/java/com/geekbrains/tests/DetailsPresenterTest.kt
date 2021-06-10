package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter().apply { this.onAttach(viewContract) }
    }

    @Test
    fun viewContract_NotNull_afterOnAttach(){
        val privateViewContract = presenter.javaClass.getDeclaredField("viewContract").apply {
            this.isAccessible = true
        }
        val viewContract = privateViewContract.get(presenter)

        assertNotNull(viewContract)
    }

    @Test
    fun viewContract_Null_afterOnDetach() {
        presenter.onDetach()

        val privateViewContract = presenter.javaClass.getDeclaredField("viewContract").apply {
            this.isAccessible = true
        }
        val viewContract = privateViewContract.get(presenter)

        assertNull(viewContract)
    }

    @Test
    fun setCounter() {
        presenter.setCounter(10)

        val privateCount = presenter.javaClass.getDeclaredField("count").apply {
            this.isAccessible = true
        }
        val count = privateCount.get(presenter)

        assertEquals(10, count)
    }

    @Test
    fun onIncrement_increments(){
        for( i in 1..10) {
            presenter.onIncrement()
        }

        val privateCount = presenter.javaClass.getDeclaredField("count").apply {
            this.isAccessible = true
        }
        val count = privateCount.get(presenter)

        assertEquals(10, count)
    }

    @Test
    fun onIncrement_viewContractSetCount(){
        for( i in 1..10) {
            presenter.onIncrement()
        }

       verify(viewContract, times(10)).setCount(any())
    }

    @Test
    fun onDecrement_increments(){
        for( i in 1..10) {
            presenter.onDecrement()
        }

        val privateCount = presenter.javaClass.getDeclaredField("count").apply {
            this.isAccessible = true
        }
        val count = privateCount.get(presenter)

        assertEquals(-10, count)
    }

    @Test
    fun onDecrement_viewContractSetCount(){
        for( i in 1..10) {
            presenter.onDecrement()
        }

        verify(viewContract, times(10)).setCount(any())

    }


}