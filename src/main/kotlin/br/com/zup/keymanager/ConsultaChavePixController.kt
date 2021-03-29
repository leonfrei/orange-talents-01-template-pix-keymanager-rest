package br.com.zup.keymanager

import br.com.zup.ConsultaChavePixRequest
import br.com.zup.KeymanagerConsultaChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerListaChavesPixGrpcServiceGrpc
import br.com.zup.ListaChavesPixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class ConsultaChavePixController(
    private val consultaChavePixClient: KeymanagerConsultaChavePixGrpcServiceGrpc.KeymanagerConsultaChavePixGrpcServiceBlockingStub,
    private val listaChavePixClient: KeymanagerListaChavesPixGrpcServiceGrpc.KeymanagerListaChavesPixGrpcServiceBlockingStub
) {
    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/pix/{pixId}")
    fun consulta(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        LOGGER.info("[$clienteId] consulta chave pix por id $pixId")
        val chaveResponse = consultaChavePixClient.consultar(
            ConsultaChavePixRequest.newBuilder()
                .setPixIdEClienteId(ConsultaChavePixRequest.ConsultaPorClienteEPixId.newBuilder()
                        .setClienteId(clienteId.toString())
                        .setPixId(pixId.toString())
                        .build()
                )
                .build()
        )
        return HttpResponse.ok(DetalheChavePixResponse(chaveResponse))
    }

    @Get("/pix/")
    fun lista(clienteId: UUID): HttpResponse<Any>{
        LOGGER.info("[$clienteId] listando chaves pix")
        val pix = listaChavePixClient.listar(ListaChavesPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build())
        val chaves = pix.chavesList.map { ChavePixResponse(it) }
        return HttpResponse.ok(chaves)
    }
}