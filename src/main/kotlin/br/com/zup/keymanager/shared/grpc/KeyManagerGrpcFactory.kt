package br.com.zup.keymanager.shared.grpc

import br.com.zup.KeymanagerConsultaChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerListaChavesPixGrpcServiceGrpc
import br.com.zup.KeymanagerRegistraChavePixGrpcServiceGrpc
import br.com.zup.KeymanagerRemoveChavePixGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = KeymanagerRegistraChavePixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeChave() = KeymanagerRemoveChavePixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave() = KeymanagerListaChavesPixGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaChave() = KeymanagerConsultaChavePixGrpcServiceGrpc.newBlockingStub(channel)

}