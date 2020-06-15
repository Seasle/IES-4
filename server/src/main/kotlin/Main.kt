import com.google.common.base.CaseFormat.LOWER_CAMEL
import com.google.common.base.CaseFormat.LOWER_UNDERSCORE
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.http.ContentType
import io.ktor.response.respondFile
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import java.io.File

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
                    val modules = gson.toJson(Database.getModules(call.parameters["houseId"]!!.toInt()))
                    call.respondText(modules, ContentType.Application.Json)
                }
                get("/{houseId}/{moduleName}") {
                    val journal = gson.toJson(
                        Database.getJournal(
                            LOWER_CAMEL.to(LOWER_UNDERSCORE, call.parameters["moduleName"].toString()),
                            call.parameters["houseId"]!!.toInt()
                        )
                    )
                    call.respondText(journal, ContentType.Application.Json)
                }
            }
        }

        route("/files") {
            get("/img/{name}") {
                val url = javaClass.getResource("/images").file
                call.respondFile(File(url), call.parameters["name"].toString())
            }
        }
    }
}

fun main() {
    embeddedServer(Netty, 8080, watchPaths = listOf("MainKt"), module = Application::module).start()
}