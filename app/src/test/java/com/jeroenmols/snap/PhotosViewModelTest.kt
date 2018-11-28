package com.jeroenmols.snap

import com.jeroenmols.snap.testutils.InstantExecutorExtension
import com.jeroenmols.snap.unsplash.WebService
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import retrofit2.Callback
import retrofit2.Response

@ExtendWith(MockitoExtension::class, InstantExecutorExtension::class)
class PhotosViewModelTest {

    @Mock
    lateinit var webService: WebService

    val captor = argumentCaptor<Callback<List<UnsplashPhoto>>>()

    @Test
    internal fun `can instantiate`() {
        PhotosViewModel(webService)
    }

    @Test
    internal fun `should load photos when creating`() {
        PhotosViewModel(webService)

        verify(webService).getPhotos(any())
    }

    @Test
    internal fun `should update live data with photos`() {
        val photos = fakePhotoList()
        val viewModel = PhotosViewModel(webService)
        verify(webService).getPhotos(captor.capture())

        captor.firstValue.onResponse(mock(), photos.asResponse())

        assertThat(viewModel.photos.value).isEqualTo(photos)
    }

    @Test
    internal fun `should search photos with webservice when search`() {
        PhotosViewModel(webService).search("lego")

        verify(webService).searchPhotos(eq("lego"), any())
    }

    private fun List<UnsplashPhoto>.asResponse(): Response<List<UnsplashPhoto>> {
        val response: Response<List<UnsplashPhoto>> = mock()
        whenever(response.body()).thenReturn(this)
        return response
    }

    private fun fakePhotoList() = listOf(UnsplashPhoto("id1", "", emptyMap()))
}

