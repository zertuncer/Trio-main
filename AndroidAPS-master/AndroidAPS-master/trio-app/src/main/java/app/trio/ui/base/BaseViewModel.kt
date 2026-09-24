package app.trio.ui.base

import androidx.lifecycle.ViewModel

/**
 * Trio iOS 'BaseStateModel' (Swinject) karşılığı Android Hilt tabanlı BaseViewModel.
 *
 * NOT: Orijinal `state.isInitial = false` ve `configureView()` pattern'i,
 * ViewModel'in init{} bloğuna veya Composable'ın LaunchedEffect(Unit) bloğuna
 * taşınması gerektiğinden ViewModel'de isInitial bayrağı varsayılan olarak eklendi.
 */
abstract class BaseViewModel : ViewModel() {
    // İlk kurulumun yapılıp yapılmadığını izler (iOS: `isInitial`)
    var isInitial: Boolean = true
        protected set

    /**
     * Composable'dan ilk göründüğünde çağrılacak. 
     * iOS'teki `configureView()` muadili.
     */
    open fun configureView(configure: (() -> Unit)? = null) {
        if (isInitial) {
            configure?.invoke()
            subscribe()
            isInitial = false
        }
    }

    /**
     * Orijinal TrioBaseStateModel'de resolver atandığında çağrılan abone olma metodu.
     * Flow collect işlemleri alt sınıflarda burada veya init{} bloğunda yapılabilir.
     */
    protected open fun subscribe() {
        // Alt sınıflar tarafından ezilecek
    }

    // Modal işlemleri Router / NavController üzerinden yapılacağı için
    // showModal/hideModal fonksiyonları doğrudan ViewModel yerine UI layer'da 
    // ele alınabilir veya UI Event (SharedFlow) olarak dışa sunulabilir.
}
