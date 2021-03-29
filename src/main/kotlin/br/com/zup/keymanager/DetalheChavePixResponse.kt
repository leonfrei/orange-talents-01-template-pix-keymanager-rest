package br.com.zup.keymanager

import br.com.zup.ConsultaChavePixResponse
import br.com.zup.TipoDeConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalheChavePixResponse(chaveResponse: ConsultaChavePixResponse) {
    val pixId = chaveResponse.pixId
    val tipoDeChave = chaveResponse.tipoDeChave
    val chave = chaveResponse.chave

    val registradaEm = chaveResponse.registradaEm.let {
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(it.seconds, it.nanos.toLong()),
            ZoneOffset.UTC
        )
    }

    val tipoDeConta = when (chaveResponse.conta.tipoDeConta) {
        TipoDeConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        TipoDeConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        else -> "NAO_RECONHECIDA"
    }

    val conta = mapOf(
        Pair("tipoDeConta", tipoDeConta),
        Pair("instituicao", chaveResponse.conta.instituicao),
        Pair("nomeDoTitular", chaveResponse.conta.titular.nome),
        Pair("cpfDoTitular", chaveResponse.conta.titular.cpf),
        Pair("agencia", chaveResponse.conta.agencia),
        Pair("numero", chaveResponse.conta.numero),
    )
}