package com.example.kailink.model

class Message(var message: String, var sentBy: String) {
    companion object {
        var SENT_BY_ME: String = "me"
        var SENT_BY_BOT: String = "bot"
    }
}