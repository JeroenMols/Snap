package com.jeroenmols.snap

import com.jeroenmols.snap.testutils.InstantExecutorExtension
import com.jeroenmols.snap.api.PhotosRepository
import com.jeroenmols.snap.source.unsplash.data.UnsplashPhoto
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class, InstantExecutorExtension::class    )
class PhotosViewModelTest {

    @Mock
    lateinit var photosRepository: PhotosRepository

    @BeforeEach
    internal fun setUp() {
        // Called in init to quickly have fresh data so needs to be stubbed for every test
        whenever(photosRepository.getPhotos()).thenReturn(Single.just(emptyList()))
    }

    @Test
    internal fun `can instantiate`() {
        PhotosViewModel(photosRepository)
    }

    @Test
    internal fun `should load photos when creating`() {
        PhotosViewModel(photosRepository)

        verify(photosRepository).getPhotos()
    }

    @Test
    internal fun `should update live data with photos`() {
        val photos = fakePhotoList()
        whenever(photosRepository.getPhotos()).thenReturn(Single.just(photos))

        val viewModel = PhotosViewModel(photosRepository)

        assertThat(viewModel.photos.value).isEqualTo(photos)
    }

    @Test
    internal fun `should search photos with webservice when search`() {
        whenever(photosRepository.searchPhotos("lego")).thenReturn(Single.just(fakePhotoList()))

        PhotosViewModel(photosRepository).search("lego")

        verify(photosRepository).searchPhotos(eq("lego"))
    }

    @Test
    internal fun `should update live data with photos from search`() {
        val photos = fakePhotoList()
        whenever(photosRepository.searchPhotos("lego")).thenReturn(Single.just(photos))
        val viewModel = PhotosViewModel(photosRepository)

        viewModel.search("lego")

        assertThat(viewModel.photos.value).isEqualTo(photos)
    }

    private fun fakePhotoList() = listOf(UnsplashPhoto("id1", "", emptyMap()))
}