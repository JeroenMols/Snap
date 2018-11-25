package com.jeroenmols.snap

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PhotosViewModelFactoryTest {

    @Test
    internal fun `can instantiate`() {
        PhotosViewModelFactory()
    }

    @Test
    internal fun `should create ViewModel for PhotosViewModelClass`() {
        val viewModel = PhotosViewModelFactory().create(PhotosViewModel::class.java)

        assertThat(viewModel).isNotNull
    }
}