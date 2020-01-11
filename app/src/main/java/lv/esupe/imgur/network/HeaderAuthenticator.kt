package lv.esupe.imgur.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class HeaderAuthenticator @Inject constructor(private val clientId: String) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request().header("Authorization") != null) {
            return null // Give up, we've already attempted to authenticate.
        }
        return response.request().newBuilder()
            .header("Authorization", "Client-ID $clientId")
            .build()
    }
}
