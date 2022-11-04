package br.com.alura.forum.service

import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.model.TopicoViewTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager

class TopicoServiceTest {

    private val topicos = PageImpl(listOf(TopicoTest.build()))

    private val topicoRepository: TopicoRepository = mockk{
        every{ findByCursoNome(any(), any()) } returns topicos
    }
    private val paginaçao: Pageable = mockk()

    private val topicoViewMapper: TopicoViewMapper = mockk()
    private val topicoFormMapper: TopicoFormMapper = mockk()
    private val entityManager: EntityManager = mockk()


    private val topicoService = TopicoService(
        topicoRepository,
        topicoViewMapper,
        topicoFormMapper
        )

    @Test
    fun `deve retornar topicos a partir do nome`(){
        every{ topicoViewMapper.map(any())} returns TopicoViewTest.build()

        topicoService.listar("Kotlin Avançado", paginaçao)

        verify(exactly = 1) {topicoRepository.findByCursoNome(any(), any())}
        verify(exactly = 1) {topicoViewMapper.map(any())}
        verify(exactly = 0) {topicoRepository.findAll(paginaçao)}


    }


}