package app.trio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import app.trio.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val NunitoFont = GoogleFont("Nunito")

val NunitoFontFamily = FontFamily(
    Font(googleFont = NunitoFont, fontProvider = provider)
)

val TrioTypography = Typography().copy(
    displayLarge = Typography().displayLarge.copy(fontFamily = NunitoFontFamily),
    displayMedium = Typography().displayMedium.copy(fontFamily = NunitoFontFamily),
    displaySmall = Typography().displaySmall.copy(fontFamily = NunitoFontFamily),
    headlineLarge = Typography().headlineLarge.copy(fontFamily = NunitoFontFamily),
    headlineMedium = Typography().headlineMedium.copy(fontFamily = NunitoFontFamily),
    headlineSmall = Typography().headlineSmall.copy(fontFamily = NunitoFontFamily),
    titleLarge = Typography().titleLarge.copy(fontFamily = NunitoFontFamily),
    titleMedium = Typography().titleMedium.copy(fontFamily = NunitoFontFamily),
    titleSmall = Typography().titleSmall.copy(fontFamily = NunitoFontFamily),
    bodyLarge = Typography().bodyLarge.copy(fontFamily = NunitoFontFamily),
    bodyMedium = Typography().bodyMedium.copy(fontFamily = NunitoFontFamily),
    bodySmall = Typography().bodySmall.copy(fontFamily = NunitoFontFamily),
    labelLarge = Typography().labelLarge.copy(fontFamily = NunitoFontFamily),
    labelMedium = Typography().labelMedium.copy(fontFamily = NunitoFontFamily),
    labelSmall = Typography().labelSmall.copy(fontFamily = NunitoFontFamily)
)
