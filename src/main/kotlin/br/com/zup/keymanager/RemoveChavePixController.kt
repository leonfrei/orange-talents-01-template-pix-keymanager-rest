package br.com.zup.keymanager

import br.com.zup.KeymanagerRemoveChavePixGrpcServiceGrpc
import br.com.zup.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteID}")
class RemoveChavePixController(private val removeChavePixClient: KeymanagerRemoveChavePixGrpcServiceGrpc.KeymanagerRemoveChavePixGrpcServiceBlockingStub) {
    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("/pix/{pixId}")
    fun delete(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        LOGGER.info("[$clienteId] removendo uma chave pix com $pixId")
        removeChavePixClient.remover(RemoveChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .build())
        return HttpResponse.ok()
    }
}