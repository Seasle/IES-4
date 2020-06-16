package com.skyface

import com.google.common.base.CaseFormat.LOWER_CAMEL
import com.google.common.base.CaseFormat.LOWER_UNDERSCORE
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.skyface.database.Database
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.utils.io.ByteWriteChannel

private val gson = GsonBuilder().serializeNulls().create()

fun Application.module() {
    install(CORS)
    {
        anyHost()
        allowCredentials = true
        allowNonSimpleContentTypes = true
    }
    install(Routing) {
        route("/api") {
            get("/houses") {
                val houses = Gson().toJson(Database.getHouses())

                call.respondText(houses, ContentType.Application.Json)
            }

            route("/modules") {
                get("/{houseId}") {
                    val houseId = call.parameters["houseId"]!!.toInt()

                    Database.insertControlJournal("Запрос списка модулей и характеристик", houseId)

                    val modules = gson.toJson(Database.getModules(houseId))
                    call.respondText(modules, ContentType.Application.Json)
                }
                get("/{houseId}/{moduleName}") {
                    val moduleName = LOWER_CAMEL.to(LOWER_UNDERSCORE, call.parameters["moduleName"].toString())
                    val houseId = call.parameters["houseId"]!!.toInt()

                    Database.insertControlJournal("Запрос журнала модуля $moduleName", houseId)

                    val journal = gson.toJson(
                        Database.getJournal(
                            moduleName,
                            houseId
                        )
                    )
                    call.respondText(journal, ContentType.Application.Json)
                }
            }
        }

        route("/files") {
            get("/img/{name}") {
                val imgName = call.parameters["name"]
                val inputStream = javaClass.getResourceAsStream("/images/$imgName")
                val bytes = inputStream.readBytes()

                call.respond(object : OutgoingContent.WriteChannelContent() {
                    override val contentType = ContentType.Application.OctetStream
                    override suspend fun writeTo(channel: ByteWriteChannel) {
                        channel.writeFully(bytes, 0, bytes.size)
                    }
                })
            }
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("MainKt"), module = Application::module).start()
}
