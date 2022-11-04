package br.com.alura.forum.service

import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.TopicoTest
import br.com.alura.forum.repository.TopicoRepository
import io.mockk.every
import io.mockk.mockk
import org.springframework.data.domain.PageImpl
import javax.persistence.EntityManager

class TopicoServiceTest {

    private val topicos = PageImpl(listOf(TopicoTest.build()))

    private val topicoRepository: TopicoRepository = mockk{
        every{ findByCursoNome(any(), any()) } returns topicos
    }

    private val topicoViewMapper: TopicoViewMapper = mockk()
    private val topicoFormMapper: TopicoFormMapper = mockk()
    private val entityManager: EntityManager = mockk()


    private val topicoService = TopicoService(
        topicoRepository,
        topicoViewMapper,
        topicoFormMapper,
        notFoundMessage = "Topico nao encontrado!",
        em = entityManager
        )


}