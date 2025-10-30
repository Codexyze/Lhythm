package com.nutrino.lhythm.UnitTest

import com.nutrino.lhythm.Fake.FakeGetAllSongsRepository
import com.nutrino.lhythm.core.StateHandeling.ResultState
import com.nutrino.lhythm.domain.Usecases.GetAllSongUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class LhythmUnitTest {
    private lateinit var fakeGetAllSongsRepository: FakeGetAllSongsRepository
    private lateinit var getAllSongUseCase: GetAllSongUseCase

    @Before
    fun setUp(){
        fakeGetAllSongsRepository = FakeGetAllSongsRepository()
        getAllSongUseCase = GetAllSongUseCase(getAllSongRepository = fakeGetAllSongsRepository)

    }

    @After
    fun tearUp(){

    }

    @Test
    fun `get all songs from internal storage test case`() = runBlocking{
        val resultDataElement = getAllSongUseCase.invoke().first()
        val result = resultDataElement is ResultState.Success
        assertTrue(result)
        val mainData = (resultDataElement as ResultState.Success).data
        assertEquals(mainData.size,4)
        assertEquals(mainData[0].id,"1")

    }

}