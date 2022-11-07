package br.com.alura.forum.service

import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.model.TopicoViewTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.apache.el.stream.Optional
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.Optional.empty
import javax.persistence.EntityManager

class TopicoServiceTest {

    private val topicos = PageImpl(listOf(TopicoTest.build()))

    private val paginaçao: Pageable = mockk()

    private val topicoRepository: TopicoRepository = mockk{
        every{ findByCursoNome(any(), any()) } returns topicos
        every{ findAll(paginaçao)} returns topicos

    }

    private val topicoViewMapper: TopicoViewMapper = mockk(){
        every{ map(any())} returns TopicoViewTest.build()

    }
    private val topicoFormMapper: TopicoFormMapper = mockk()
    private val entityManager: EntityManager = mockk()


    private val topicoService = TopicoService(
        topicoRepository,
        topicoViewMapper,
        topicoFormMapper
        )

    @Test
    fun `deve retornar topicos a partir do nome`(){
        topicoService.listar("Kotlin Avançado", paginaçao)

        verify(exactly = 1) {topicoRepository.findByCursoNome(any(), any())}
        verify(exactly = 1) {topicoViewMapper.map(any())}
        verify(exactly = 0) {topicoRepository.findAll(paginaçao)}
    }

    @Test
    fun `deve retornar todos os topicos quando o retorno for nulo`(){
        topicoService.listar(null,paginaçao)

        verify(exactly = 0) {topicoRepository.findByCursoNome(any(), any())}
        verify(exactly = 1) {topicoViewMapper.map(any())}
        verify(exactly = 1) {topicoRepository.findAll(paginaçao)}
    }

    @Test
    fun `deve listar notFoundException quando o topico nao encontrado`(){
        every{ topicoRepository.findById(any())} returns empty()

        val atual = assertThrows<NotFoundException> {
            topicoService.buscarPorId(1)
        }
        assertThat(atual.message).isEqualTo("Topico nao encontrado!")
    }




}