package com.jeroenmols.snap

import androidx.lifecycle.ViewModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.lang.RuntimeException

class PhotosViewModelFactoryTest {

    @Test
    internal fun `should create ViewModel for PhotosViewModelClass`() {
        val viewModel = PhotosViewModelFactory().create(PhotosViewModel::class.java)

        assertThat(viewModel).isNotNull
    }

    @Test
    internal fun `should throw exception when different viewmodel class`() {
        assertThrows(RuntimeException::class.java) {
            PhotosViewModelFactory().create(TestViewModel::class.java)
        }
    }

    class TestViewModel : ViewModel()
}