plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.ifsp.mercadoroom"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.ifsp.mercadoroom"
        minSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // adiciona as dependências do Room, SQLite e outras dependências do Android

    // dependências principais do Android
    implementation("androidx.core:core-ktx:1.12.0") // Funcionalidades principais do Android com suporte para Kotlin
    implementation("androidx.appcompat:appcompat:1.6.1") // Biblioteca de compatibilidade com versões antigas do Android
    implementation("com.google.android.material:material:1.10.0") // Biblioteca de design de Material Components
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Layouts avançados para Android

    // Dependências de navegação
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5") // Componentes de fragmento para navegação
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5") // Componentes de UI para navegação

    // Dependências do Room (Biblioteca de persistência)
    implementation ("androidx.room:room-runtime:2.6.0") // Biblioteca principal do Room
    implementation ("androidx.room:room-ktx:2.6.0") // Extensões Kotlin para Room
    ksp("androidx.room:room-compiler:2.6.0") // Processador de anotações para geração de código no Room

    // ksp -> Kotlin Symbol Processing

    // Dependências para testes
    testImplementation("junit:junit:4.13.2") // Framework de testes para Java
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Extensões de teste para Android (Junit)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Framework para testes de UI automatizados no Android (Espresso)
}