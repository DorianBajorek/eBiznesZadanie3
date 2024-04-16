package com.example

import discord4j.core.DiscordClient
import discord4j.core.GatewayDiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Mono

class DiscordBot(private val token: String) {
    private lateinit var client: GatewayDiscordClient

    fun start() {
        client = DiscordClient.create(token).login().block()!!
        recv()
        client.on(MessageCreateEvent::class.java)
            .flatMap { event ->
                handleMessage(event.message.content)
            }
            .subscribe()
    }

    private fun handleMessage(message: String): Mono<Void> {
        // Tutaj dodaj logikę obsługi wiadomości i wysyłania odpowiedzi
        println("Received message: $message")
        return Mono.empty()
    }

    fun getListOfCategories(): String {
        val categories = listOf("sport shoes", "sport clothes", "fitness equipment", "watches")
        return categories.joinToString(", ")
    }

    fun getListOfSportShoes(): String {
        val shoes = listOf("Adidas Adizero Boston", "Nike Vaporfly 3", "Nike Structure 25")
        return shoes.joinToString(", ")
    }

    fun getListOfSportClothes(): String {
        val clothes = listOf("T-shirt", "Hoodie", "Gloves")
        return clothes.joinToString(", ")
    }

    fun getListOfFitnessEquipment(): String {
        val fitnessEquipment = listOf("Treadmill", "Dumbbells", "Resistance bands");
        return fitnessEquipment.joinToString(", ")
    }

    fun getListOfWatches(): String {
        val watches = listOf("Garmin Fenix 7X", "Garmin Forerunner 265", " Garmin Forerunner 965")
        return watches.joinToString(", ")
    }

    fun recv() {
        client.on(MessageCreateEvent::class.java)
            .flatMap { event ->
                if (event.message.content.equals("get categories", ignoreCase = true)) {
                    event.message.channel.flatMap { channel ->
                        channel.createMessage(getListOfCategories())
                    }
                } else if (event.message.content.equals("get sport shoes", ignoreCase = true)) {
                        event.message.channel.flatMap { channel ->
                            channel.createMessage(getListOfSportShoes())
                        }
                } else if (event.message.content.equals("get sport clothes", ignoreCase = true)) {
                    event.message.channel.flatMap { channel ->
                        channel.createMessage(getListOfSportClothes())
                    }
                } else if (event.message.content.equals("get fitness equipment", ignoreCase = true)) {
                    event.message.channel.flatMap { channel ->
                        channel.createMessage(getListOfFitnessEquipment())
                    }
                }
                else if (event.message.content.equals("get watches", ignoreCase = true)) {
                    event.message.channel.flatMap { channel ->
                        channel.createMessage(getListOfWatches())
                    }
                }else if (event.message.content.equals("thanks", ignoreCase = true)) {
                    event.message.channel.flatMap { channel ->
                        channel.createMessage("Thanks bye!!!")
                    }
                } else {
                    Mono.empty()
                }
            }
            .subscribe()
    }
}
