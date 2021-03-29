package br.com.zup.keymanager

import br.com.zup.ListaChavesPixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ChavePixResponse(chavePix: ListaChavesPixResponse.ChavePix) {
    val id = chavePix.pixId
    val chave = chavePix.chave
    val tipoDeChave = chavePix.tipoDaChave
    val tipoDeConta = chavePix.tipoDaConta
    val criadaEm = chavePix.registradaEm.let {
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(it.seconds, it.nanos.toLong()),
            ZoneOffset.UTC
        )
    }
}
