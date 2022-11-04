package br.com.alura.forum.model

object TopicoTest {
    fun build() = Topico(
        id = 1,
        titulo = "Duvida Kotlin",
        mensagem = "Testes de unidade",
        curso = CursoTest.build(),
        autor = UsuarioTest.build()
    )
}