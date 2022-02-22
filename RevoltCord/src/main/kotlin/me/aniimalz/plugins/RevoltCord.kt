package me.aniimalz.plugins

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.aliucord.Utils
import com.aliucord.Utils.openPageWithProxy
import com.aliucord.annotations.AliucordPlugin
import com.aliucord.api.CommandsAPI
import com.aliucord.entities.Plugin
import com.aliucord.fragments.SettingsPage

@AliucordPlugin
class TwitterCord : Plugin() {
    @SuppressLint("SetJavaScriptEnabled")
    inner class Trolley(context: Context) : WebView(context) {
        @SuppressLint("ClickableViewAccessibility")
        override fun onTouchEvent(event: MotionEvent): Boolean {
            requestDisallowInterceptTouchEvent(true)
            return super.onTouchEvent(event)
        }

        init {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                }
            }
        }
    }

    lateinit var twitter: Trolley

    inner class TwitterPage : SettingsPage() {
        override fun onViewBound(view: View) {
            super.onViewBound(view)
            headerBar.visibility = View.GONE
            setPadding(0)
            twitter = Trolley(view.context)
            twitter.loadUrl("https://twittrr.com")
            twitter.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            linearLayout.addView(revolt)
        }
    }

    override fun start(context: Context) {
        commands.registerCommand("twitter", "Twitter in Discord lol") {
            openPageWithProxy(Utils.appActivity, TwitterPage())
            CommandsAPI.CommandResult()
        }
    }

    override fun stop(context: Context) {
        patcher.unpatchAll()
        commands.unregisterAll()
    }
}
