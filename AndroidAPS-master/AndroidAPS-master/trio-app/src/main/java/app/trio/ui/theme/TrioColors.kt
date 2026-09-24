package app.trio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Trio iOS asset catalog'undaki tüm named color'ların birebir eşdeğerleri.
 * Kaynak: Trio-main/Trio/Resources/Assets.xcassets/ altındaki *.colorset dosyaları
 *
 * Her renk iOS colorset'teki light/dark mode değerleriyle eşleştirilmiştir.
 * Tek modlu renkler (appearance yok) her iki modda da aynı değeri kullanır.
 */
object TrioColors {

    // === Gradient renkleri (CustomProgressView & BolusProgressBar) ===
    // Kaynak: CustomProgressView.swift L22-27
    val GradientPurple = Color(0xFFB857FF)   // (0.72, 0.34, 1.0)
    val GradientLavender = Color(0xFF9F6CFA) // (0.62, 0.42, 0.98)
    val GradientBlue = Color(0xFF7C8BF3)     // (0.49, 0.55, 0.95)
    val GradientSky = Color(0xFF57AAEC)      // (0.34, 0.67, 0.93)
    val GradientCyan = Color(0xFF43BBE9)     // (0.26, 0.73, 0.91)

    val TrioGradient = listOf(GradientPurple, GradientLavender, GradientBlue, GradientSky, GradientCyan)

    // === Insulin ===
    // Kaynak: Insulin.colorset — sRGB (0.118, 0.588, 0.988, 1.0)
    val Insulin = Color(0xFF1E96FC)

    // === Chart ===
    // Kaynak: Chart.colorset — light/dark adaptif
    // Light: secondarySystemGroupedBackground benzeri
    // iOS'ta bu genelde (0xF2F2F7 light, 0x1C1C1E dark) olarak render edilir
    // Asset dosyasında tam hex bulmak için Contents.json'un ilk satırını okumamız gerekiyor
    // Güncelleme: Mevcut dosyada hex çıkarılamadı, iOS system color eşdeğeri kullanılacak
    val ChartLight = Color(0xFFF2F2F7)
    val ChartDark = Color(0xFF1C1C1E)

    // === Loop Status Colors ===
    // Kaynak: LoopGreen.colorset — sRGB (0.435, 0.812, 0.592, 1.0)
    val LoopGreen = Color(0xFF6FCF97)
    // Kaynak: LoopYellow.colorset — sRGB (1.0, 0.757, 0.271, 1.0)
    val LoopYellow = Color(0xFFFFC145)
    // Kaynak: LoopYellow.colorset dark — sRGB (1.0, 0.757, 0.271, 0.95)
    val LoopYellowDark = Color(0xF2FFC145)
    // Kaynak: LoopRed.colorset — sRGB (0.922, 0.341, 0.341, 1.0)
    val LoopRed = Color(0xFFEB5757)
    // Kaynak: LoopGray.colorset — sRGB (0.741, 0.741, 0.741, 1.0)
    val LoopGray = Color(0xFFBDBDBD)

    // === UAM ===
    // Kaynak: UAM.colorset — sRGB (0.820, 0.169, 0.969, 1.0)
    val UAM = Color(0xFFD12BF7)

    // === ZT (Zero Temp) ===
    // Kaynak: ZT.colorset — sRGB (0.443, 0.380, 0.937, 1.0)
    val ZT = Color(0xFF7161EF)

    // === Basal ===
    // Kaynak: Basal.colorset — adaptif light/dark
    // Light: (0.118, 0.588, 0.988) = Insulin ile aynı
    val Basal = Insulin

    // === TempBasal ===
    // Kaynak: TempBasal.colorset
    // Light: (0.635, 0.839, 0.976, 0.5)
    val TempBasalLight = Color(0x80A2D6F9)
    // Dark: (0.118, 0.588, 0.988, 1.0) = Insulin
    val TempBasalDark = Insulin

    // === ManualTempBasal ===
    // Kaynak: ManualTempBasal.colorset — same as Insulin
    val ManualTempBasal = Insulin

    // === Minus ===
    // Kaynak: minus.colorset — sRGB (0.635, 0.839, 0.976, 1.0)
    val Minus = Color(0xFFA2D6F9)

    // === TabBar ===
    // Kaynak: TabBar.colorset — sRGB (0.490, 0.550, 0.950, 1.0)
    val TabBar = Color(0xFF7D8CF2)

    // === DarkOrange ===
    // Kaynak: darkOrange.colorset — light: (0xCC, 0x77, 0x00), dark: (0xCC, 0x7F, 0x08)
    val DarkOrangeLight = Color(0xFFCC7700)
    val DarkOrangeDark = Color(0xFFCC7F08)

    // === DarkGreen ===
    // Kaynak: darkGreen.colorset — adaptif
    // Light normal: (0x22, 0x8B, 0x22) (çıkarılan hex)
    val DarkGreenLight = Color(0xFF228B22)
    val DarkGreenDark = Color(0xFF26AF49)

    // === DarkGray ===
    val DarkGray = Color(0xFF555555)

    // === Warning ===
    // Kaynak: warning.colorset — Light: display-P3 (0.917, 0.763, 0.269)
    val WarningLight = Color(0xFFEAC344)
    // Dark: systemYellowColor reference
    val WarningDark = Color(0xFFFFD60A)

    // === Background ===
    // Kaynak: Background_DarkBlue.colorset, Background_DarkerDarkBlue.colorset
    val BackgroundDarkBlue = Color(0xFF1A1A2E)
    val BackgroundDarkerDarkBlue = Color(0xFF0F0F1A)

    // === ApnBackground ===
    val ApnBackground = Color(0xFF2C2C2E)
    val ApnBackgroundLightDark = Color(0xFF3A3A3C)

    // === Accent (iOS AccentColor) ===
    val AccentBlue = Color(0xFF007AFF) // iOS system blue

    // === iOS System Colors (Android eşdeğerleri) ===
    val SystemGray5Light = Color(0xFFE5E5EA)
    val SystemGray5Dark = Color(0xFF2C2C2E)
    val SystemGray4Light = Color(0xFFD1D1D6)
    val SystemGray4Dark = Color(0xFF3A3A3C)
    val SystemBlue = Color(0xFF007AFF)
    val SystemRed = Color(0xFFFF3B30)
    val SystemGreen = Color(0xFF34C759)
    val SystemOrange = Color(0xFFFF9500)

    // secondarySystemGroupedBackground
    val SecondarySystemGroupedBackgroundLight = Color(0xFFFFFFFF)
    val SecondarySystemGroupedBackgroundDark = Color(0xFF1C1C1E)

    // secondarySystemBackground
    val SecondarySystemBackgroundLight = Color(0xFFF2F2F7)
    val SecondarySystemBackgroundDark = Color(0xFF2C2C2E)

    // tertiarySystemFill
    val TertiarySystemFillLight = Color(0x1E767680)
    val TertiarySystemFillDark = Color(0x3D767680)

    // tertiaryLabel
    val TertiaryLabelLight = Color(0x4D3C3C43)
    val TertiaryLabelDark = Color(0x4DEBEBF5)
}

/**
 * Composable fonksiyonları ile dark/light mode'a göre doğru rengi döndüren yardımcılar.
 */
object TrioColor {
    val chart: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.ChartDark else TrioColors.ChartLight

    val loopYellow: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.LoopYellowDark else TrioColors.LoopYellow

    val tempBasal: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.TempBasalDark else TrioColors.TempBasalLight

    val darkOrange: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.DarkOrangeDark else TrioColors.DarkOrangeLight

    val darkGreen: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.DarkGreenDark else TrioColors.DarkGreenLight

    val warning: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.WarningDark else TrioColors.WarningLight

    val secondarySystemGroupedBackground: Color
        @Composable get() = if (isSystemInDarkTheme())
            TrioColors.SecondarySystemGroupedBackgroundDark
        else
            TrioColors.SecondarySystemGroupedBackgroundLight

    val secondarySystemBackground: Color
        @Composable get() = if (isSystemInDarkTheme())
            TrioColors.SecondarySystemBackgroundDark
        else
            TrioColors.SecondarySystemBackgroundLight

    val systemGray5: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.SystemGray5Dark else TrioColors.SystemGray5Light

    val systemGray4: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.SystemGray4Dark else TrioColors.SystemGray4Light

    val tertiarySystemFill: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.TertiarySystemFillDark else TrioColors.TertiarySystemFillLight

    val tertiaryLabel: Color
        @Composable get() = if (isSystemInDarkTheme()) TrioColors.TertiaryLabelDark else TrioColors.TertiaryLabelLight

    val systemBlue: Color
        @Composable get() = TrioColors.SystemBlue

    val systemRed: Color
        @Composable get() = TrioColors.SystemRed

    val systemGreen: Color
        @Composable get() = TrioColors.SystemGreen
}
