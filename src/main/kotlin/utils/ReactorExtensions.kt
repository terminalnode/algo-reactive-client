package utils

import com.google.gson.Gson
import io.netty.buffer.ByteBuf
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import reactor.netty.ByteBufFlux

val gson = Gson()

inline fun <reified T> ByteBufFlux.toFlux(): Flux<T> =
	toFlux()
		.map {
			val jsonString = it.readString()
			return@map gson.fromJson(jsonString, T::class.java)
		}

fun ByteBuf.readString(): String =
	readCharSequence().toString()

fun ByteBuf.readCharSequence(): CharSequence =
	getCharSequence(0, capacity(), Charsets.UTF_8).toString()