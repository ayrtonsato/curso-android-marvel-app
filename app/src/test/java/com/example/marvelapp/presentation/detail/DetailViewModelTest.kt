package com.example.marvelapp.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.marvelapp.R
import androidx.lifecycle.Observer
import com.example.core.domain.model.Comic
import com.example.core.domain.model.Event
import com.example.core.usecase.GetCharacterCategoriesUseCase
import com.example.core.usecase.base.ResultStatus
import com.example.marvelapp.presentation.detail.DetailViewModel.*
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.model.ComicFactory
import com.example.testing.model.EventFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getCharactersCategoriesUseCase: GetCharacterCategoriesUseCase

    @Mock
    private lateinit var uiStateObserver: Observer<UiState>

    private lateinit var detailViewModel: DetailViewModel

    private val character = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val comics = listOf(ComicFactory().create(ComicFactory.FakeComic.FakeComic1))
    private val events = listOf(EventFactory().create(EventFactory.FakeEvent.FakeEvent1))

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(getCharactersCategoriesUseCase)
        detailViewModel.uiState.observeForever(uiStateObserver)
    }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns success`() =
        runTest {
            // arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            comics to events
                        )
                    )
                )

            // act
            detailViewModel.getCharacterCategories(character.id)
            // assert
            verify(uiStateObserver).onChanged(isA<UiState.Success>())
            val uiStateSuccess = detailViewModel.uiState.value as UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(2, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
            assertEquals(
                R.string.details_events_category,
                categoriesParentList[1].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only comics`() =
        runTest {
            // arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            comics to emptyList()
                        )
                    )
                )

            // act
            detailViewModel.getCharacterCategories(character.id)
            // assert
            verify(uiStateObserver).onChanged(isA<UiState.Success>())
            val uiStateSuccess = detailViewModel.uiState.value as UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_comics_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Success from UiState when get character categories returns only events`() =
        runTest {
            // arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<Comic>() to events
                        )
                    )
                )

            // act
            detailViewModel.getCharacterCategories(character.id)
            // assert
            verify(uiStateObserver).onChanged(isA<UiState.Success>())
            val uiStateSuccess = detailViewModel.uiState.value as UiState.Success
            val categoriesParentList = uiStateSuccess.detailParentList

            assertEquals(1, categoriesParentList.size)
            assertEquals(
                R.string.details_events_category,
                categoriesParentList[0].categoryStringResId
            )
        }

    @Test
    fun `should notify uiState with Empty from UiState when get character categories returns an empty result list`() =
        runTest {
            // arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(
                    flowOf(
                        ResultStatus.Success(
                            emptyList<Comic>() to emptyList<Event>()
                        )
                    )
                )

            // act
            detailViewModel.getCharacterCategories(character.id)

            // assert
            verify(uiStateObserver).onChanged(isA<UiState.Empty>())
        }

    @Test
    fun `should notify uiState with Error from UiState when get character categories returns an exception`() =
        runTest {
            // arrange
            whenever(getCharactersCategoriesUseCase.invoke(any()))
                .thenReturn(flowOf(ResultStatus.Error(Throwable())))

            // act
            detailViewModel.getCharacterCategories(character.id)

            // assert
            verify(uiStateObserver).onChanged(isA<UiState.Error>())
        }
}