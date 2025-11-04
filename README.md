# 🌱 Huerto Hogar — App Android (Kotlin + Jetpack Compose)

Aplicación móvil para gestionar **Huerto Hogar**: inicio de sesión, menú principal, catálogo con imágenes, detalle de producto y (opcional) área de administración ligera. Construida con **Android Studio** y **Jetpack Compose** siguiendo un enfoque **MVVM** y estado inmutable por pantalla.

---

## 📱 Requisitos

- **Android Studio** (recomendado: Narwhal Feature Drop 2025.1.2 Patch 1 o posterior)
- **JDK 17**
- **Gradle Wrapper** incluido en el proyecto
- **SDK**: `minSdk` 24+ (ajustable), `targetSdk` el más reciente que soporte tu IDE

> Abre el proyecto con *File ▸ Open…* → selecciona la carpeta raíz → espera el **Gradle Sync**.

---

## 🚀 Ejecución rápida

1) Conecta un dispositivo o crea un **AVD** (Virtual Device).  
2) En la barra superior, elige el módulo `app` y el dispositivo.  
3) Pulsa **Run ▶**.
4) Usuario predeterminado es cliente@huerto.cl y la contraseña es Huerto2025!

> Si es la primera vez, Android Studio descargará dependencias y toolchains automáticamente.

---

## 🧱 Estructura (sugerida)

```
app/
└─ src/
   └─ main/
      ├─ java/com/example/proyectologin005d/
      │  ├─ HuertoApp.kt                # @Composable raíz y NavHost
      │  ├─ navigation/                 # rutas/graph
      │  ├─ ui/login/                   # LoginScreen, LoginViewModel
      │  ├─ ui/home/                    # HomeMenuScreen
      │  ├─ ui/catalogo/                # CatalogScreen, ProductCard, DetailScreen
      │  ├─ data/                       # repos, modelos, fuentes de datos
      │  ├─ core/                       # SessionManager, Result wrappers
      │  └─ theme/                      # Tema Material 3 (colors, typography, shapes)
      ├─ res/
      │  ├─ drawable/                   # imágenes estáticas (png/webp/svg)
      │  ├─ drawable-nodpi/             # imágenes sin densidad (opcional)
      │  ├─ mipmap-anydpi-v26/          # íconos del app (ic_launcher*)
      │  ├─ values/strings.xml          # app_name, textos
      │  └─ values/themes.xml           # si aplicas estilos xml de apoyo
      └─ AndroidManifest.xml
```

---

## 🔐 Sesión y autenticación

- **SessionManager** (SharedPreferences / DataStore Preferences) para guardar:
  - `auth_token` (simulado/local)
  - `username` / `rememberMe`
- **LoginScreen**: campos usuario/clave, visualización de errores, botón “Mostrar/Ocultar” contraseña.
- **Navegación protegida**: si no hay sesión válida, redirige a `Login`.

---

## 🛒 Catálogo y recursos de imágenes

Tienes dos formas de manejar **fotos del catálogo**:

### Opción A — Imágenes estáticas en `res/drawable`
- Coloca tus fotos en: `app/src/main/res/drawable/`
- Nómbralas en minúsculas y con guiones bajos: `manzana_roja.webp`, `lechuga_baby.webp`
- En tu `ProductCard`/`CatalogScreen` usa:
  ```kotlin
  Image(
      painter = painterResource(R.drawable.manzana_roja),
      contentDescription = "Manzana Roja",
      modifier = Modifier.size(120.dp)
  )
  ```

### Opción B — Archivos en `assets/` (cuando son muchas o vienen de un json)
- Crea la carpeta: `app/src/main/assets/`
- Copia ahí tus imágenes: `assets/img/catalogo/manzana_roja.webp`
- Carga con **Coil** (recomendado en Compose):
  ```kotlin
  AsyncImage(
      model = "file:///android_asset/img/catalogo/manzana_roja.webp",
      contentDescription = "Manzana Roja"
  )
  ```

> Si usas **galería del sistema** para elegir imágenes (perfil/actualizaciones), es una **funcionalidad nativa** mediante *Photo Picker/Intent*. Para catálogo fijo en la app, prefiere `drawable` o `assets`.

---

## 🎨 Tema y colores

- **Material 3 (M3)** con tu paleta: Verde principal, acentos mostaza y marrón claro.
- Centraliza colores/typography en `theme/` para que *Home*, *Catálogo* y *Login* compartan estilo.
- Si separaste colores por pantalla (p. ej. `HomeMenuScreen`), expórtalos desde `theme/Colors.kt`.

---

## 🧭 Navegación (Compose Navigation)

- Rutas sugeridas:
  - `/login`
  - `/home`
  - `/catalog`
  - `/detail/{codigo}`

Ejemplo de NavHost:

```kotlin
NavHost(navController, startDestination = if (isLogged) "home" else "login") {
    composable("login")   { LoginScreen(onLoginSuccess = { navController.navigate("home") }) }
    composable("home")    { HomeMenuScreen(
        onGoToCatalog = { navController.navigate("catalog") },
        onLogout = { /* limpiar sesión y volver a login */ }
    ) }
    composable("catalog") { CatalogScreen(onOpenDetail = { code ->
        navController.navigate("detail/$code")
    }) }
    composable("detail/{codigo}") { backStackEntry ->
        val codigo = backStackEntry.arguments?.getString("codigo")
        DetailScreen(code = codigo)
    }
}
```

---

## 📦 Dependencias (gradle)

En `app/build.gradle.kts` (ejemplo):

```kotlin
dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.10.00") // ajusta versión
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.8.3")

    // Imágenes remotas/asset: Coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Estado & prefs (elige uno)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    // o SharedPreferences (incluido en AndroidX)

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // (Opcional) Room, Retrofit, etc.
}
```

> Sincroniza con **Sync Now** si cambias versiones.

---

## 🧪 Pruebas

- **Unit tests**: lógica de `SessionManager`, validaciones de login, formateo de precios.  
- **UI tests** (Compose UI Test): estados de carga, errores, navegación.

Scripts (desde Android Studio):
- **Run tests**: *Gradle ▸ app ▸ verification ▸ test*  
- **Connected tests**: con AVD/dispositivo real

---

## 🔧 Cambiar nombre del app y paquete

### 1) Nombre visible (label)
- Edita `app/src/main/res/values/strings.xml` → `app_name`.

### 2) Ícono del app
- *File ▸ New ▸ Image Asset* → genera `mipmap/ic_launcher*`.

### 3) **applicationId** (ID de publicación)
- Abre `app/build.gradle.kts` → cambia `namespace` y **`applicationId`**:
  ```kotlin
  android {
      namespace = "com.tuempresa.huertohogar"
      defaultConfig {
          applicationId = "com.tuempresa.huertohogar"
      }
  }
  ```

### 4) **Paquete de código** (carpetas `java/`)
- En el panel **Project** (vista *Android* o *Project*), clic derecho sobre el paquete  
  `com.example.proyectologin005d` → **Refactor ▸ Rename…**  
  → elige **Rename package** (no “directory”) → aplica cambios en cascada.

> Android Studio actualizará imports y rutas. Verifica `AndroidManifest.xml`.

---

## 🏗️ Build de Release (APK / App Bundle)

1) *Build ▸ Generate Signed App Bundle / APK…*  
2) Crea/selecciona **keystore** → define contraseña segura.  
3) Elige **Android App Bundle (AAB)** o **APK**.  
4) Selecciona `release` → **Finish**.  
5) El artefacto queda en: `app/release/`.

---

## 🧭 Funciones clave de esta app

- **Login** con validación básica, feedback de error y persistencia de sesión.  
- **Home / Menú** con navegación a **Catálogo** y **Cerrar sesión**.  
- **Catálogo** con lista de productos (imagen, nombre, precio) y acceso a **Detalle**.  
- **Detalle** con imagen, descripción y acciones (añadir al “carrito” local si corresponde).  
- (Opcional) **Perfil de usuario** con cambio de **foto desde galería** (*Photo Picker/Intent*).  
- (Opcional) Bandeja de **ofertas** (precio con descuento) si implementas bandera y %.

---

## 🧩 Convenciones y buenas prácticas

- **MVVM + State Hoisting**: la UI observa `uiState` del `ViewModel`.  
- **Single Source of Truth**: repositorio/SessionManager centralizan datos.  
- **Theming unificado** en `theme/` para colores/tipografías globales.  
- **Recursos**: imágenes de catálogo en `drawable` o `assets` (ver sección).  
- **Navegación** declarativa y rutas tipadas cuando sea posible.



- **Autor/Equipo**: *(agrega tus nombres)*  
- **Docencia/Curso**: *(opcional)*
