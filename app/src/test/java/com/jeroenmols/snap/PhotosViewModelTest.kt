package com.jeroenmols.snap

import com.jeroenmols.snap.testutils.ImmediateRxSchedulersExtension
import com.jeroenmols.snap.testutils.InstantExecutorExtension
import com.jeroenmols.snap.unsplash.WebService
import com.jeroenmols.snap.unsplash.data.UnsplashPhoto
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class, InstantExecutorExtension::class, ImmediateRxSchedulersExtension::class)
class PhotosViewModelTest {

    @Mock
    lateinit var webService: WebService

    @Test
    internal fun `can instantiate`() {
        whenever(webService.getPhotos()).thenReturn(Single.just(fakePhotoList()))
        PhotosViewModel(webService)
    }

    @Test
    internal fun `should load photos when creating`() {
        whenever(webService.getPhotos()).thenReturn(Single.just(fakePhotoList()))
        PhotosViewModel(webService)

        verify(webService).getPhotos()
    }

    @Test
    internal fun `should update live data with photos`() {
        val photos = fakePhotoList()
        whenever(webService.getPhotos()).thenReturn(Single.just(photos))

        val viewModel = PhotosViewModel(webService)

        assertThat(viewModel.photos.value).isEqualTo(photos)
    }

    @Test
    internal fun `should search photos with webservice when search`() {
        whenever(webService.getPhotos()).thenReturn(Single.just(fakePhotoList()))
        whenever(webService.searchPhotos("lego")).thenReturn(Single.just(fakePhotoList()))

        PhotosViewModel(webService).search("lego")

        verify(webService).searchPhotos(eq("lego"))
    }

    @Test
    internal fun `should update live data with photos from search`() {
        val photos = fakePhotoList()
        whenever(webService.getPhotos()).thenReturn(Single.just(fakePhotoList()))
        whenever(webService.searchPhotos("lego")).thenReturn(Single.just(photos))
        val viewModel = PhotosViewModel(webService)

        viewModel.search("lego")

        assertThat(viewModel.photos.value).isEqualTo(photos)
    }

    private fun fakePhotoList() = listOf(UnsplashPhoto("id1", "", emptyMap()))
}