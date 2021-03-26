package br.com.zup.keymanager

import br.com.zup.RegistraChavePixRequest
import br.com.zup.TipoDeChave
import br.com.zup.TipoDeConta
import br.com.zup.keymanager.shared.validation.ValidPixKey
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NovaChavePixRequest(
    @field:NotNull val tipoDeConta: TipoDeContaRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoDeChave: TipoDeChaveRequest?
) {
    fun paraModeloGrpc(clienteId: UUID): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoDeConta(tipoDeConta?.atributoGrpc ?: TipoDeConta.UNKNOWN_TIPO_DE_CONTA)
            .setChave(chave ?: "")
            .setTipoDeChave(tipoDeChave?.atributoGrpc ?: TipoDeChave.UNKNOWN_TIPO_DE_CHAVE)
            .build()
    }
}

enum class TipoDeChaveRequest(val atributoGrpc: TipoDeChave) {
    CPF(TipoDeChave.CPF) {
        override fun validar(chave: String?): Boolean {
            if (chave.isNullOrBlank() || !chave.matches("^[0-9]{11}\$".toRegex()))
                return false

            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    TELEFONE_CELULAR(TipoDeChave.TELEFONE_CELULAR) {
        override fun validar(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false

            return chave?.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex()) ?: false
        }
    },
    EMAIL(TipoDeChave.EMAIL) {
        override fun validar(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false

            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    CHAVE_ALEATORIA(TipoDeChave.CHAVE_ALEATORIA) {
        override fun validar(chave: String?) = chave.isNullOrBlank()
    };

    abstract fun validar(chave: String?): Boolean
}

enum class TipoDeContaRequest(val atributoGrpc: TipoDeConta) {
    CONTA_CORRENTE(TipoDeConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoDeConta.CONTA_POUPANCA)
}
