package com.example.watermarkoverlay

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.yukonga.miuix.kmp.basic.Text
import java.util.Locale

private data class SupportedLanguage(
    val tag: String,
    val nameRes: Int
)

private val supportedLanguages = listOf(
    SupportedLanguage("zh-CN", R.string.language_simplified_chinese),
    SupportedLanguage("zh-TW", R.string.language_traditional_chinese),
    SupportedLanguage("en", R.string.language_english),
    SupportedLanguage("ru", R.string.language_russian),
    SupportedLanguage("kk", R.string.language_kazakh),
    SupportedLanguage("ug", R.string.language_uyghur),
    SupportedLanguage("de", R.string.language_german),
    SupportedLanguage("ky", R.string.language_kyrgyz),
    SupportedLanguage("fr", R.string.language_french),
    SupportedLanguage("hi", R.string.language_hindi)
)

@Composable
fun AboutScreen(
    onLanguageClick: () -> Unit = {}
) {
    val context = LocalContext.current

    var languageMenuExpanded by remember { mutableStateOf(false) }

    val currentLanguageTag = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                ?.applicationLocales
                ?.takeIf { !it.isEmpty }
                ?.get(0)
                ?.toLanguageTag()
        } else {
            Locale.getDefault().toLanguageTag()
        }
    }

    val selectedLanguage = supportedLanguages.firstOrNull { language ->
        currentLanguageTag?.equals(language.tag, ignoreCase = true) == true ||
            currentLanguageTag?.startsWith(language.tag, ignoreCase = true) == true
    } ?: supportedLanguages.first()

    fun selectLanguage(language: SupportedLanguage) {
        languageMenuExpanded = false
        onLanguageClick()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.getSystemService(LocaleManager::class.java)
                ?.applicationLocales = LocaleList.forLanguageTags(language.tag)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.size(88.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.version, "2.1.0-release"),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(36.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.language),
                fontSize = 14.sp,
                color = Color.Gray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clickable {
                        languageMenuExpanded = !languageMenuExpanded
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(selectedLanguage.nameRes),
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = if (languageMenuExpanded) "⌃" else "⌄",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }

            if (languageMenuExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    supportedLanguages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .clickable {
                                    selectLanguage(language)
                                }
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(language.nameRes),
                                modifier = Modifier.weight(1f),
                                fontSize = 15.sp
                            )

                            if (language.tag.equals(selectedLanguage.tag, ignoreCase = true)) {
                                Text(
                                    text = "?",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }

            AboutItem(
                title = stringResource(R.string.github),
                value = "Eason206 / XUELiWatermarkTool"
            )

            AboutItem(
                title = stringResource(R.string.open_source),
                value = stringResource(R.string.open_source_project)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.credits),
            modifier = Modifier.fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.credits_text),
            modifier = Modifier.fillMaxWidth(),
            fontSize = 14.sp,
            lineHeight = 21.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = stringResource(R.string.copyright),
            fontSize = 13.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun AboutItem(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}





