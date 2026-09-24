package app.trio.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Orijinal iOS SettingInputHintView eşdeğeri.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingInputHintView(
    title: String,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    // SwiftUI'daki presentationDetents [.fraction(0.9), .large]
    // Android'de genelde ModalBottomSheet kullanılarak çözülür,
    // ancak bu bileşen doğrudan içerik döndürür, bottom sheet dışarıda tanımlanır.
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    content()
                }
            }
            
            // "Got it!" button
            Button(
                onClick = onClose,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Got it!")
            }
        }
    }
}
